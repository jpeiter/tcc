package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.data.LocationObservedData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeFragment extends Fragment implements ListenerActivity, Observer {

    private View parent;
    private AtividadeActivity atividadeActivity;

    private TextView tvAtividadePausada;

    private MaterialButton btnPausarParar;
    private MaterialButton btnRetomarAtividade;
    private FrameLayout frameLayoutRetomarAtividade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_atividade, container, false);
        atividadeActivity = (AtividadeActivity) getActivity();

        setBtnPausarParar(parent.findViewById(R.id.btn_pausar_parar_atividade));
        setBtnRetomarAtividade(parent.findViewById(R.id.btn_retomar_atividade));
        setFrameLayoutRetomarAtividade(parent.findViewById(R.id.fl_btn_retomar_atividade));
        setTvAtividadePausada(parent.findViewById(R.id.tv_atividade_pausada));
        getTvAtividadePausada().setVisibility(View.GONE);

        atividadeActivity.getLocationListener().addObserver(this);

        initListeners();
        return parent;
    }

    @Override
    public void initListeners() {
        getBtnPausarParar().setOnClickListener(v -> {
            if (atividadeActivity.getAtividadeEstado().equals(AtividadeEstado.EM_ANDAMENTO)) {
                getFrameLayoutRetomarAtividade().setVisibility(View.VISIBLE);
                pausarAtividade();
            } else {
                Toast.makeText(getContext(), getString(R.string.mantenha_pressionado_para_finalizar), Toast.LENGTH_SHORT).show();
            }
        });

        getBtnPausarParar().setOnLongClickListener(v -> {
            if (atividadeActivity.getAtividadeEstado().equals(AtividadeEstado.PAUSADA)) {
                finalizarAtividade();
            } else {
                Toast.makeText(getContext(), getString(R.string.pressione_para_finalizar), Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        getBtnRetomarAtividade().setOnClickListener(v -> {
            retomarAtividade();
        });
    }



    private void retomarAtividade() {
        retomaAtividadeUi();
        atividadeActivity.retomarAtividade();
    }

    private void retomaAtividadeUi() {
        getTvAtividadePausada().setVisibility(View.GONE);
        getFrameLayoutRetomarAtividade().setVisibility(View.GONE);
        getTvAtividadePausada().clearAnimation();
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_pause_white_36dp));
    }

    private void pausarAtividade() {
        pausaAtividadeUi();
        atividadeActivity.pausarAtividade();
    }

    protected void pausaAtividadeUi() {
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_stop_white_36dp));
        getFrameLayoutRetomarAtividade().setVisibility(View.VISIBLE);
        getTvAtividadePausada().setVisibility(View.VISIBLE);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(700);
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        getTvAtividadePausada().startAnimation(anim);
    }

    private void finalizarAtividade() {
        atividadeActivity.finalizarAtividade();
    }


    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof LocationObservedData){
            Toast.makeText(getContext(), "NO FRAGMENT!!!!", Toast.LENGTH_SHORT).show();
        }
    }
}

