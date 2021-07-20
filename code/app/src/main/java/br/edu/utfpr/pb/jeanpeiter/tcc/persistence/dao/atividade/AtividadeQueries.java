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

    public static final String QTDE_EM_DUPLA =      "SELECT COUNT(*) FROM atividade WHERE userid = :uid AND tipo = 0";
    public static final String ATIVIDADES_RESUMO =  "SELECT * FROM atividade WHERE userid = :uid ORDER BY inicio DESC";
    public static final String ATIVIDADE_DETALHES =  "SELECT * FROM atividade WHERE _id = :id";
    public static final String POSICOES_ATIVIDADE =  "SELECT * FROM atividade_posicao WHERE atividadeId = :atividadeId ORDER BY ordem ASC";

}
