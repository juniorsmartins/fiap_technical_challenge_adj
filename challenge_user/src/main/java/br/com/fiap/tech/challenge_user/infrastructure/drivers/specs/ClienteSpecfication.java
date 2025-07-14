package br.com.fiap.tech.challenge_user.infrastructure.drivers.specs;

import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ClienteEntity;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public final class ClienteSpecfication {

    public static Specification<ClienteEntity> consultaDinamica(UsuarioFiltroDto filtro) {

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

            if (ObjectUtils.isNotEmpty(filtro.email())) {

                var valores = Arrays.asList(filtro.email().split(","));

                List<Predicate> predicates = valores.stream()
                        .map(valor -> criteriaBuilder.like(criteriaBuilder
                                .lower(root.get("email")), "%" + valor.toLowerCase() + "%"))
                        .toList();

                predicados.add(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
            }

            if (ObjectUtils.isNotEmpty(filtro.numeroCartaoFidelidade())) {

                var valores = Arrays.asList(filtro.numeroCartaoFidelidade().split(","));

                List<Predicate> predicates = valores.stream()
                        .map(valor -> criteriaBuilder.like(criteriaBuilder
                                        .lower(root.get("numeroCartaoFidelidade")),
                                        "%" + valor.toLowerCase() + "%"))
                        .toList();

                predicados.add(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicados.toArray(new Predicate[0]));
        });
    }
}

