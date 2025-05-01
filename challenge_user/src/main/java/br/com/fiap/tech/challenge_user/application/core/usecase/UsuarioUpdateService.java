package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.adapter.repository.UsuarioCreateAdapter;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioUpdateService implements UsuarioUpdateInputPort {

    private final UsuarioFindByIdOutputPort usuarioFindByIdOutputPort;

    private final UsuarioCreateOutputPort usuarioCreateOutputPort;

    private final ApplicationMapper applicationMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public Usuario update(@NonNull final Usuario usuario) {

        log.info("UsuarioUpdateService - entrada no service de update: {}", usuario);

        var id = usuario.getUsuarioId();

        var usuarioUpdated = usuarioFindByIdOutputPort.findById(id)
                .map(entity -> atualizarUsuario(usuario, entity))
                .map(entity -> atualizarEndereco(usuario, entity))
                .map(usuarioCreateOutputPort::save)
                .map(applicationMapper::toUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        log.info("UsuarioUpdateService - concluído serviço de update: {}", usuarioUpdated);

        return usuarioUpdated;
    }

    private UsuarioEntity atualizarUsuario(Usuario usuario, UsuarioEntity usuarioEntity) {
        BeanUtils.copyProperties(usuario, usuarioEntity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return usuarioEntity;
    }

    private UsuarioEntity atualizarEndereco(Usuario usuario, UsuarioEntity usuarioEntity) {

        if (usuario.getEndereco() == null && usuarioEntity.getEndereco() == null) {
            // Cenário 1: Requisição sem endereço, usuário sem endereço → Não fazer nada
            return usuarioEntity;

        } else if (usuario.getEndereco() == null && usuarioEntity.getEndereco() != null) {
            // Cenário 2: Requisição sem endereço, usuário com endereço → Remover endereço
            usuarioEntity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (usuario.getEndereco() != null && usuarioEntity.getEndereco() == null) {
            // Cenário 3: Requisição com endereço, usuário sem endereço → Criar novo endereço
            usuarioEntity.setEndereco(EnderecoEntity.builder()
                    .cep(usuario.getEndereco().getCep())
                    .logradouro(usuario.getEndereco().getLogradouro())
                    .numero(usuario.getEndereco().getNumero())
                    .build()
            );

        } else if (usuario.getEndereco() != null && usuarioEntity.getEndereco() != null) {
            // Cenário 4: Requisição com endereço, usuário com endereço → Atualizar endereço
            BeanUtils.copyProperties(usuario.getEndereco(), usuarioEntity.getEndereco(), "enderecoId");
        }

        return usuarioEntity;
    }
}

