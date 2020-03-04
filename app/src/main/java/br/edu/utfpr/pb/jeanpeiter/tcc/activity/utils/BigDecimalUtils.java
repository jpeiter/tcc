package br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public BigDecimal getDecimalDeIteiroEDecimal(int inteiro, int decimal) {
        return new BigDecimal(inteiro + "." + decimal);
    }


}
