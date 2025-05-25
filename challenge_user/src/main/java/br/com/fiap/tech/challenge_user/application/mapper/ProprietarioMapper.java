package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class ProprietarioMapper implements InputMapper<ProprietarioDtoRequest, ProprietarioUpdateDtoRequest, Proprietario>,
        EntityMapper<Proprietario, ProprietarioEntity>, OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> {

    private final EnderecoMapper mapper;

    @Override
    public ProprietarioEntity toEntity(Proprietario proprietario) {
        if (proprietario == null) {
            return null;
        }

        var entity = new ProprietarioEntity();
        entity.setUsuarioId(proprietario.getUsuarioId());
        entity.setNome(proprietario.getNome());
        entity.setEmail(proprietario.getEmail());
        entity.setLogin(proprietario.getLogin());
        entity.setSenha(proprietario.getSenha());
        entity.setEndereco(mapper.toEnderecoEntity(proprietario.getEndereco()));
        entity.setDescricao(proprietario.getDescricao());

        return entity;
    }

    @Override
    public Proprietario toDomain(ProprietarioEntity entity) {
        if (entity == null) {
            return null;
        }

        var proprietario = new Proprietario();
        proprietario.setUsuarioId(entity.getUsuarioId());
        proprietario.setNome(entity.getNome());
        proprietario.setEmail(entity.getEmail());
        proprietario.setLogin(entity.getLogin());
        proprietario.setSenha(entity.getSenha());
        proprietario.setEndereco(mapper.toEndereco(entity.getEndereco()));
        proprietario.setDescricao(entity.getDescricao());

        return proprietario;
    }

    @Override
    public Proprietario toDomainIn(ProprietarioDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var proprietario = new Proprietario();
        proprietario.setNome(dto.nome());
        proprietario.setEmail(dto.email());
        proprietario.setLogin(dto.login());
        proprietario.setSenha(dto.senha());
        proprietario.setEndereco(mapper.toEndereco(dto.endereco()));
        proprietario.setDescricao(dto.descricao());

        return proprietario;
    }

    @Override
    public Proprietario toDomainUpdate(ProprietarioUpdateDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var proprietario = new Proprietario();
        proprietario.setUsuarioId(dto.usuarioId());
        proprietario.setNome(dto.nome());
        proprietario.setEmail(dto.email());
        proprietario.setLogin(dto.login());
        proprietario.setSenha(dto.senha());
        proprietario.setEndereco(mapper.toEndereco(dto.endereco()));
        proprietario.setDescricao(dto.descricao());

        return proprietario;
    }

    @Override
    public ProprietarioDtoResponse toDtoResponse(Proprietario proprietario) {
        if (proprietario == null) {
            return null;
        }

        return new ProprietarioDtoResponse(
                proprietario.getUsuarioId(), proprietario.getNome(), proprietario.getEmail(), proprietario.getLogin(),
                proprietario.getSenha(), null, null,
                mapper.toEnderecoDtoResponse(proprietario.getEndereco()), proprietario.getDescricao()
        );
    }

    @Override
    public ProprietarioDtoResponse toResponse(ProprietarioEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ProprietarioDtoResponse(
                entity.getUsuarioId(), entity.getNome(), entity.getEmail(), entity.getLogin(),
                entity.getSenha(), entity.getDataHoraCriacao(), entity.getDataHoraEdicao(),
                mapper.toEnderecoDtoResponse(entity.getEndereco()), entity.getDescricao()
        );
    }
}

