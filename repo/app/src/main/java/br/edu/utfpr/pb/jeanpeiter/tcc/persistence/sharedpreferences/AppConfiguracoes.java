package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppConfiguracoes {

    @Getter
    @Builder.Default
    private boolean utilizaDadosMoveis = false;


}
