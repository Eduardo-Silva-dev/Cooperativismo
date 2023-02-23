package br.com.sicredi.cooperativismo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "associado")
public class Associado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable=false, updatable=false)
    private long id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy="associado")
    private List<Voto> votos;

}
