package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso;


import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NivelProgresso {

    BRIDGETOWN(1, 1, 300, R.string.nivel_progresso_1),
    ISTAMBUL(2, 301, 650, R.string.nivel_progresso_2),
    BRUXELAS(3, 651, 1050, R.string.nivel_progresso_3),
    AMSTERDAM(4, 1051, 1500, R.string.nivel_progresso_4),
    MACAU(5, 1501, 2000, R.string.nivel_progresso_5),
    AUCKLAND(6, 2001, 2550, R.string.nivel_progresso_6),
    LONDRES(7, 2551, 3150, R.string.nivel_progresso_7),
    JAKARTA(8, 3151, 3800, R.string.nivel_progresso_8),
    JERUSALEM(9, 3801, 4500, R.string.nivel_progresso_9),
    HANOI(10, 4501, 5250, R.string.nivel_progresso_10),
    MONTEVIDEU(11, 5251, 6050, R.string.nivel_progresso_11),
    HONOLULU(12, 6051, 6900, R.string.nivel_progresso_12),
    ATENAS(13, 6901, 7800, R.string.nivel_progresso_13),
    ARGEL(14, 7801, 8750, R.string.nivel_progresso_14),
    TEGUCIGALPA(15, 8751, 9750, R.string.nivel_progresso_15),
    BAGDA(16, 9751, 10800, R.string.nivel_progresso_16),
    MOSCOU(17, 10801, 11900, R.string.nivel_progresso_17),
    LISBOA(18, 11901, 13050, R.string.nivel_progresso_18),
    FRANKFURT(19, 13051, 14250, R.string.nivel_progresso_19),
    BOGOTA(20, 14251, 15500, R.string.nivel_progresso_20),
    PRAGA(21, 15501, 16800, R.string.nivel_progresso_21),
    PARIS(22, 16801, 18150, R.string.nivel_progresso_22),
    DUBAI(23, 18151, 19550, R.string.nivel_progresso_23),
    MARRAQUEXE(24, 19551, 21000, R.string.nivel_progresso_24),
    LIMA(25, 21001, 22500, R.string.nivel_progresso_25),
    BUDAPESTE(26, 22501, 24050, R.string.nivel_progresso_26),
    OSLO(27, 24051, 25650, R.string.nivel_progresso_27),
    MUMBAI(28, 25651, 27300, R.string.nivel_progresso_28),
    BUCARESTE(29, 27301, 29000, R.string.nivel_progresso_29),
    BANGKOK(30, 29001, 30750, R.string.nivel_progresso_30),
    HELSINQUE(31, 30751, 32560, R.string.nivel_progresso_31),
    SANTIAGO(32, 32561, 34430, R.string.nivel_progresso_32),
    TAIPEI(33, 34431, 36360, R.string.nivel_progresso_33),
    DUBLIN(34, 36361, 38350, R.string.nivel_progresso_34),
    COPENHAGUE(35, 38351, 40400, R.string.nivel_progresso_35),
    CANCUN(36, 40401, 42510, R.string.nivel_progresso_36),
    VILNIUS(37, 42511, 44680, R.string.nivel_progresso_37),
    MECA(38, 44681, 46910, R.string.nivel_progresso_38),
    VENEZA(39, 46911, 49200, R.string.nivel_progresso_39),
    SEUL(40, 49201, 51550, R.string.nivel_progresso_40),
    ESTOCOLMO(41, 51551, 53970, R.string.nivel_progresso_41),
    CAIRO(42, 53971, 56460, R.string.nivel_progresso_42),
    VIENA(43, 56461, 59020, R.string.nivel_progresso_43),
    VANCOUVER(44, 59021, 61650, R.string.nivel_progresso_44),
    SYDNEY(45, 61651, 64350, R.string.nivel_progresso_45),
    KYOTO(46, 64351, 67130, R.string.nivel_progresso_46),
    MANILA(47, 67131, 69990, R.string.nivel_progresso_47),
    TEERA(48, 69991, 72930, R.string.nivel_progresso_48),
    KINGSTON(49, 72931, 75950, R.string.nivel_progresso_49),
    SINGAPURA(50, 75951, 79050, R.string.nivel_progresso_50),
    FLORIANOPOLIS(51, 79051, 82240, R.string.nivel_progresso_51),
    GEORGETOWN(52, 82241, 85530, R.string.nivel_progresso_52),
    HAVANA(53, 85531, 88920, R.string.nivel_progresso_53),
    COLOMBO(54, 88921, 92410, R.string.nivel_progresso_54),
    VARSOVIA(55, 92411, 96000, R.string.nivel_progresso_55),
    BARCELONA(56, 96001, 100000, R.string.nivel_progresso_56);

    private final int ordem;
    private final int pontuacaoMinima;
    private final int pontuacaoMaxima;
    private final int descricaoId;

}
