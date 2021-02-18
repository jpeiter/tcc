package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico;

import com.digidemic.unitof.UnitOf;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistoricoAtividades {

    private double distanciaTotal;
    private Duration tempoMovimento;
    private long totalPercursos;
    private LocalDate dataDe;
    private LocalDate dataAte;

    public HistoricoAtividades(float distanciaTotal, long tempoMovimento, long totalPercursos, long dataDe, long dataAte) {
        this.distanciaTotal = distanciaTotal;
        this.tempoMovimento = Duration.ofSeconds(tempoMovimento);
        this.totalPercursos = totalPercursos;
        DateUtils dateUtils = new DateUtils();
        this.dataDe = dateUtils.millisToLocalDate(dataDe);
        this.dataAte = dateUtils.millisToLocalDate(dataAte);
    }

}
