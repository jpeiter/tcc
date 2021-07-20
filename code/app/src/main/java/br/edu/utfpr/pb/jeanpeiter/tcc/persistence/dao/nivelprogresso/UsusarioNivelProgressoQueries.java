package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.nivelprogresso;

public class UsusarioNivelProgressoQueries {

    public static final String NIVEL_ATUAL =    "SELECT _id, userid, nivel, datahora, pontuacao FROM usuario_nivel WHERE userid = :uid ORDER BY nivel DESC LIMIT 1";
    public static final String HISTORICO =      "SELECT _id, userid, nivel, datahora, pontuacao FROM usuario_nivel WHERE userid = :uid ORDER BY nivel DESC";

}
