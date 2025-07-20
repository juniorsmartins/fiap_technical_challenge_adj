package br.com.fiap.tech.challenge_user.infrastructure.drivers.daos;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "enderecos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public final class EnderecoDao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "endereco_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID enderecoId;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;
}

