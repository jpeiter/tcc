package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.atividade;

public class AtividadeQueries {

    public static final String HISTORICO =
            "SELECT " +
                    "SUM(distancia) as distanciaTotal, " +
                    "SUM(duracao) as tempoMovimento, " +
                    "COUNT(*) as totalPercursos, " +
                    "MIN(inicio) as dataDe, " +
                    "MAX(inicio) as dataAte " +
            "FROM atividade " +
            "WHERE userid = :uid";

    public static final String QTDE_EM_DUPLA =      "SELECT COUNT(*) FROM atividade WHERE userid = :uid AND tipo = 'D'";
    public static final String ATIVIDADES_RESUMO =  "SELECT _id, inicio, distancia, duracao, tipo, pontos FROM atividade WHERE inicio BETWEEN :inicio AND :termino ORDER BY inicio DESC";

}
