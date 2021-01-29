package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atividade {

    // Identificador da atividade
    private String _id;

    // Identificador do usuário
    private String usuarioUid;

    // Início da atividade, em milissegundos
    private Long inicio;

    // Término da atividade, em milissegundos
    private Long termino;

    // Tipo da atividade
    @Builder.Default
    private AtividadeTipo tipo = AtividadeTipo.SOZINHO;

    // Distância total, em metros
    private Double distancia;

    // Velocidade média, em m/s
    private Double velocidade;

    // Duração total, em segundos
    private Long duracao;

    // Ritmo médio, em min.sec/km
    private Double ritmo;

    // Calorias gastas, em cal
    private Double calorias;

    // Pontuação final obtida
    private Long pontos;

    // Coordenadas do trajeto
    @Builder.Default
    private List<AtividadePosicao> posicoes = new ArrayList<>();

    // Estado
    private AtividadeEstado estado;

    // Uid do parceiro
    private String parceiroUid;

    // Nome do parceiro
    private String parceiroNome;

    // Sincronizado online
    private boolean sincronizado;


    public AtividadeDTO toDto() {
        return new AtividadeDTO(this.get_id(),
                this.getUsuarioUid(),
                this.getInicio(),
                this.getTermino(),
                this.getTipo().toDto(),
                this.getDistancia(),
                this.getVelocidade(),
                this.getDuracao(),
                this.getRitmo(),
                this.getCalorias(),
                this.getPontos(),
                this.getEstado().toDto(),
                this.getParceiroUid(),
                this.getParceiroNome(),
                this.isSincronizado() ? "S" : "N"
        );
    }

    public Atividade parse(AtividadeDTO dto) {
        this.set_id(dto.get_id());
        this.setUsuarioUid(dto.getUid());
        this.setInicio(dto.getI());
        this.setTermino(dto.getTe());
        this.setTipo(AtividadeTipo.fromDto(dto.getTi()));
        this.setDistancia(dto.getDi());
        this.setDuracao(dto.getDu());
        this.setRitmo(dto.getR());
        this.setCalorias(dto.getC());
        this.setPontos(dto.getP());
        this.setEstado(AtividadeEstado.fromDto(dto.getE()));
        this.setParceiroUid(dto.getPId());
        this.setParceiroNome(dto.getPNo());
        this.setSincronizado("S".equals(dto.getS()));
        return this;
    }

    public Atividade(String id, AtividadeTipo tipo, AtividadeEstado estado) {
        set_id(id);
        setTipo(tipo);
        setEstado(estado);
    }
}
