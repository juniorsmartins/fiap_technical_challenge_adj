package br.com.fiap.tech.challenge_user.adapter.entity;

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
@EqualsAndHashCode(of = {"email"})
public final class UsuarioEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID usuarioId;

    @Column(name = "nome", length = MAX_CARACTER_NOME, nullable = false)
    private String nome;

    @Column(name = "email", length = MAX_CARACTER_EMAIL, nullable = false)
    private String email;

    @Column(name = "login", length = MAX_CARACTER_LOGIN, nullable = false)
    private String login;

    @Column(name = "senha", length = MAX_CARACTER_SENHA, nullable = false)
    private String senha;
}

