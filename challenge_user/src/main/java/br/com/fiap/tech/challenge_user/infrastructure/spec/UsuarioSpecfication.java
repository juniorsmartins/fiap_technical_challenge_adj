package br.com.fiap.tech.challenge_user.infrastructure.spec;

import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public final class UsuarioSpecfication {

    public static Specification<UsuarioEntity> consultaDinamica(UsuarioFiltroDto filtro) {

        return ((root, query, criteriaBuilder) -> {

            var predicados = new ArrayList<Predicate>();

            if (ObjectUtils.isNotEmpty(filtro.usuarioId())) {

                var ids = Arrays.asList(filtro.usuarioId().split(","));

                List<Predicate> idPredicates = ids.stream()
                        .filter(id -> !id.trim().isEmpty())
                        .map(id -> {
                            try {
                                UUID uuid = UUID.fromString(id.trim());
                                return criteriaBuilder.equal(root.get("usuarioId"), uuid);
                            } catch (IllegalArgumentException e) {
                                return null; // Ignora Ids inválidos
                            }
                        })
                        .filter(Objects::nonNull)
                        .toList();

                if (!idPredicates.isEmpty()) {
                    predicados.add(criteriaBuilder.or(idPredicates.toArray(new Predicate[0])));
                }
            }

            if (ObjectUtils.isNotEmpty(filtro.nome())) {

                var nomes = Arrays.asList(filtro.nome().split(","));

                List<Predicate> nomePredicates = nomes.stream()
                        .map(nome -> criteriaBuilder.like(criteriaBuilder
                                .lower(root.get("nome")), "%" + nome.toLowerCase() + "%"))
                        .toList();

                predicados.add(criteriaBuilder.or(nomePredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicados.toArray(new Predicate[0]));
        });
    }
}

