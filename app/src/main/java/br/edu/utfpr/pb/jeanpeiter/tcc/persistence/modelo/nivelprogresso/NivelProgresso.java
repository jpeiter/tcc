package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso;


import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NivelProgresso {

    BRIDGETOWN(1, 1, 3000, R.string.nivel_progresso_1),
    ISTAMBUL(2, 3001, 6500, R.string.nivel_progresso_2),
    BRUXELAS(3, 6501, 10500, R.string.nivel_progresso_3),
    AMSTERDAM(4, 10501, 15000, R.string.nivel_progresso_4),
    MACAU(5, 15001, 20000, R.string.nivel_progresso_5),
    AUCKLAND(6, 20001, 25500, R.string.nivel_progresso_6),
    LONDRES(7, 25501, 31500, R.string.nivel_progresso_7),
    JAKARTA(8, 31501, 38000, R.string.nivel_progresso_8),
    JERUSALEM(9, 38001, 45000, R.string.nivel_progresso_9),
    HANOI(10, 45001, 52500, R.string.nivel_progresso_10),
    MONTEVIDEU(11, 52501, 60500, R.string.nivel_progresso_11),
    HONOLULU(12, 60501, 69000, R.string.nivel_progresso_12),
    ATENAS(13, 69001, 78000, R.string.nivel_progresso_13),
    ARGEL(14, 78001, 87500, R.string.nivel_progresso_14),
    TEGUCIGALPA(15, 87501, 97500, R.string.nivel_progresso_15),
    BAGDA(16, 97501, 108000, R.string.nivel_progresso_16),
    MOSCOU(17, 108001, 119000, R.string.nivel_progresso_17),
    LISBOA(18, 119001, 130500, R.string.nivel_progresso_18),
    FRANKFURT(19, 130501, 142500, R.string.nivel_progresso_19),
    BOGOTA(20, 142501, 155000, R.string.nivel_progresso_20),
    PRAGA(21, 155001, 168000, R.string.nivel_progresso_21),
    PARIS(22, 168001, 181500, R.string.nivel_progresso_22),
    DUBAI(23, 181501, 195500, R.string.nivel_progresso_23),
    MARRAQUEXE(24, 195501, 210000, R.string.nivel_progresso_24),
    LIMA(25, 210001, 225000, R.string.nivel_progresso_25),
    BUDAPESTE(26, 225001, 240500, R.string.nivel_progresso_26),
    OSLO(27, 240501, 256500, R.string.nivel_progresso_27),
    MUMBAI(28, 256501, 273000, R.string.nivel_progresso_28),
    BUCARESTE(29, 273001, 290000, R.string.nivel_progresso_29),
    BANGKOK(30, 290001, 307500, R.string.nivel_progresso_30),
    HELSINQUE(31, 307501, 325600, R.string.nivel_progresso_31),
    SANTIAGO(32, 325601, 344300, R.string.nivel_progresso_32),
    TAIPEI(33, 344301, 363600, R.string.nivel_progresso_33),
    DUBLIN(34, 363601, 383500, R.string.nivel_progresso_34),
    COPENHAGUE(35, 383501, 404000, R.string.nivel_progresso_35),
    CANCUN(36, 404001, 425100, R.string.nivel_progresso_36),
    VILNIUS(37, 425101, 446800, R.string.nivel_progresso_37),
    MECA(38, 446801, 469100, R.string.nivel_progresso_38),
    VENEZA(39, 469101, 492000, R.string.nivel_progresso_39),
    SEUL(40, 492001, 515500, R.string.nivel_progresso_40),
    ESTOCOLMO(41, 515501, 539700, R.string.nivel_progresso_41),
    CAIRO(42, 539701, 564600, R.string.nivel_progresso_42),
    VIENA(43, 564601, 590200, R.string.nivel_progresso_43),
    VANCOUVER(44, 590201, 616500, R.string.nivel_progresso_44),
    SYDNEY(45, 616501, 643500, R.string.nivel_progresso_45),
    KYOTO(46, 643501, 671300, R.string.nivel_progresso_46),
    MANILA(47, 671301, 699900, R.string.nivel_progresso_47),
    TEERA(48, 699901, 729300, R.string.nivel_progresso_48),
    KINGSTON(49, 729301, 759500, R.string.nivel_progresso_49),
    SINGAPURA(50, 759501, 790500, R.string.nivel_progresso_50),
    FLORIANOPOLIS(51, 790501, 822400, R.string.nivel_progresso_51),
    GEORGETOWN(52, 822401, 855300, R.string.nivel_progresso_52),
    HAVANA(53, 855301, 889200, R.string.nivel_progresso_53),
    COLOMBO(54, 889201, 924100, R.string.nivel_progresso_54),
    VARSOVIA(55, 924101, 960000, R.string.nivel_progresso_55),
    BARCELONA(56, 960001, 1000000, R.string.nivel_progresso_56);

    private final int ordem;
    private final int pontuacaoMinima;
    private final int pontuacaoMaxima;
    private final int descricaoId;

}
