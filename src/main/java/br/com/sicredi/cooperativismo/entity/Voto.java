package br.com.sicredi.cooperativismo.entity;

import br.com.sicredi.cooperativismo.controller.dto.response.CalcularVotacaoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Builder
@Table(name = "voto")
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(
        name = "Voto.calculateVotes",
        query = "select " +
                "COUNT(NULLIF(0, v.status)) AS votosSim, " +
                "COUNT(NULLIF(1, v.status)) AS votosNao " +
                "from voto v " +
                "where v.sessao_id = :idSessao",
        resultSetMapping = "calculateVotesMapping"
)
@SqlResultSetMapping(
        name = "calculateVotesMapping",
        classes = @ConstructorResult(
                targetClass = CalcularVotacaoResponse.class,
                columns = {
                        @ColumnResult(name = "votosSim", type = Integer.class),
                        @ColumnResult(name = "votosNao", type = Integer.class)
                }
        )
)
public class Voto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable=false, updatable=false)
    private long id;

    @Column
    private int status;

    @ManyToOne
    @JoinColumn(name="sessao_id", nullable=false)
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name="associado_id", nullable=false)
    private Associado associado;

}