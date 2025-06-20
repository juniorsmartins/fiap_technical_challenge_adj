package br.com.fiap.tech.challenge_user.infrastructure.entity;

import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cozinha", nullable = false)
    private TipoCozinhaEnum tipoCozinhaEnum;

    @Column(name = "hora_abertura", nullable = false)
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento", nullable = false)
    private LocalTime horaFechamento;

    @OneToOne(targetEntity = EnderecoEntity.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = false, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", nullable = false, unique = true)
    private EnderecoEntity endereco;

    @ManyToOne(targetEntity = ProprietarioEntity.class, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private ProprietarioEntity proprietario;
}

