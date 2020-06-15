package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public BigDecimal getDecimalDeIteiroEDecimal(int inteiro, int decimal) {
        return new BigDecimal(inteiro + "." + decimal);
    }

    public BigDecimal arredondado(Double valor, int precisao) {
        return BigDecimal.valueOf(valor)
                .setScale(precisao, RoundingMode.HALF_EVEN);
    }

}
