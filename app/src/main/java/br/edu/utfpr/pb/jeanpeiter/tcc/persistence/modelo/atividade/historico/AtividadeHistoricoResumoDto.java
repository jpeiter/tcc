package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AtividadeHistoricoResumoDto {

    private float distanciaTotal;
    private long tempoMovimento;
    private long totalPercursos;
    private long dataDe;
    private long dataAte;

    public AtividadeHistoricoResumo parse() {
        return new AtividadeHistoricoResumo(
                this.distanciaTotal,
                this.tempoMovimento,
                this.totalPercursos,
                this.dataDe,
                this.dataAte
        );
    }
}
