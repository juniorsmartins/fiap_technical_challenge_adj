package br.com.fiap.tech.challenge_user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"restauranteId"})
public final class RestauranteEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "restaurante_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID restauranteId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToOne(targetEntity = EnderecoEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", unique = true)
    private EnderecoEntity endereco;
}

