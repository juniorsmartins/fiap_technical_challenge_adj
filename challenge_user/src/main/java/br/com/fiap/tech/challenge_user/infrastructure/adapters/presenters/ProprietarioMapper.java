package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class ProprietarioMapper implements InputMapper<ProprietarioDtoRequest, Proprietario>,
        OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity>,
        EntityMapper<Proprietario, ProprietarioEntity>,
        PageMapper<ProprietarioDtoResponse, ProprietarioEntity> {

    private final EnderecoMapper mapper;

    @Override
    public ProprietarioEntity toEntity(Proprietario proprietario) {
        if (proprietario == null) {
            return null;
        }

        var endereco = mapper.toEnderecoEntity(proprietario.getEndereco());

        return new ProprietarioEntity(
                proprietario.getUsuarioId(),
                proprietario.getNome(), proprietario.getEmail(), proprietario.getLogin(), proprietario.getSenha(),
                endereco, proprietario.getDescricao(), null, null
        );
    }

    @Override
    public Proprietario toDomain(ProprietarioEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = mapper.toEndereco(entity.getEndereco());

        return new Proprietario(
                entity.getUsuarioId(), entity.getNome(), entity.getEmail(), entity.getLogin(), entity.getSenha(),
                endereco, entity.getDescricao()
        );
    }

    @Override
    public Proprietario toDomainIn(ProprietarioDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var endereco = mapper.toEndereco(dto.endereco());

        return new Proprietario(
                dto.nome(), dto.email(), dto.login(), dto.senha(), endereco, dto.descricao()
        );
    }

    @Override
    public ProprietarioDtoResponse toDtoResponse(Proprietario proprietario) {
        if (proprietario == null) {
            return null;
        }

        var endereco = mapper.toEnderecoDtoResponse(proprietario.getEndereco());

        return new ProprietarioDtoResponse(
                proprietario.getUsuarioId(), proprietario.getNome(), proprietario.getEmail(), proprietario.getLogin(),
                proprietario.getSenha(), null, null,
                endereco, proprietario.getDescricao()
        );
    }

    @Override
    public ProprietarioDtoResponse toResponse(ProprietarioEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = mapper.toEnderecoDtoResponse(entity.getEndereco());

        return new ProprietarioDtoResponse(
                entity.getUsuarioId(), entity.getNome(), entity.getEmail(), entity.getLogin(),
                entity.getSenha(), entity.getDataHoraCriacao(), entity.getDataHoraEdicao(),
                endereco, entity.getDescricao()
        );
    }

    @Override
    public Page<ProprietarioDtoResponse> toPageResponse(Page<ProprietarioEntity> entityPage) {

        List<ProprietarioDtoResponse> dtos = entityPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(dtos, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

