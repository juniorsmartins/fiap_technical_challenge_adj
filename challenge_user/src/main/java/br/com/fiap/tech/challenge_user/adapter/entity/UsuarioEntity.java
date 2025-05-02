package br.com.fiap.tech.challenge_user.adapter.entity;

import br.com.fiap.tech.challenge_user.application.core.domain.TipoUsuarioEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.*;

@Entity
@Table(name = "usuarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"usuarioId"}, callSuper = false)
public final class UsuarioEntity extends AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID usuarioId;

    @Column(name = "nome", length = MAX_CARACTER_NOME, nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", length = MAX_CARACTER_LOGIN, nullable = false)
    private String login;

    @Column(name = "senha", length = MAX_CARACTER_SENHA, nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoUsuarioEnum tipo;

    @OneToOne(targetEntity = EnderecoEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", unique = true)
    private EnderecoEntity endereco;
}

