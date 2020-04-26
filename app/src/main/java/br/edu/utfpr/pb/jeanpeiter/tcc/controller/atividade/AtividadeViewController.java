package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.widget.TextView;

public class AtividadeViewController {

    public void atualizarValor(TextView textView, Long valor) {
        atualizarValor(textView, String.valueOf(valor));
    }

    public void atualizarValor(TextView textView, Double valor) {
        atualizarValor(textView, String.valueOf(valor));
    }

    public void atualizarValor(TextView textView, String valor) {
        textView.setText(valor);
    }



}
