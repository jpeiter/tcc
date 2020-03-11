package br.edu.utfpr.pb.jeanpeiter.tcc.activity.bemvindo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.ResourceActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.login.LoginActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.BigDecimalUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.DialogUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.IntentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.ResourcesUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.FirebaseUserController;
import br.edu.utfpr.pb.jeanpeiter.tcc.usuario.Perfil;
import br.edu.utfpr.pb.jeanpeiter.tcc.usuario.Sexo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BemVindoActivity extends AppCompatActivity implements GenericActivity, ResourceActivity {

    public TextView tvBemVindo;
    public TextView tvInformeSobre;
    public EditText etNascimento;
    public EditText etPeso;
    public EditText etAltura;
    public RadioGroup rgSexo;
    public NumberPicker npInteiro;
    public NumberPicker npDecimal;
    private Calendar myCalendar = Calendar.getInstance();
    private Perfil perfil = new Perfil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bem_vindo);

        FirebaseUser usuarioLogado = FirebaseUserController.getUser();

        if (usuarioLogado == null) {
            new IntentUtils().startActivity(this, LoginActivity.class);
        } else {
            initViews();
            replaceResources();
            initListeners();
        }
    }

    @Override
    public void initViews() {
        setTvBemVindo(findViewById(R.id.tv_seja_bem_vindo));
        setTvInformeSobre(findViewById(R.id.tv_informe_sobre));
        setEtNascimento(findViewById(R.id.et_nascimento));
        setEtPeso(findViewById(R.id.et_peso));
        setEtAltura(findViewById(R.id.et_altura));
        setRgSexo(findViewById(R.id.rg_sexo));
    }

    @Override
    public void replaceResources() {
        ResourcesUtils resourcesUtils = new ResourcesUtils(getBaseContext());

        String bemVindo = resourcesUtils.replace(R.string.bem_vindo_x, FirebaseUserController.getFirstName());
        getTvBemVindo().setText(resourcesUtils.negrito(bemVindo, bemVindo.indexOf(',') + 1, bemVindo.length()));
        getTvInformeSobre().setText(resourcesUtils.pontoFinal(getString(R.string.informe_sobre_voce)));
    }

    private void initListeners() {
        initListenerEtNascimento();
        initListenerEtAltura();
        initListenerEtPeso();
    }

    private void initListenerEtNascimento() {
        etNascimento.setOnClickListener((v) -> {
            etNascimento.setShowSoftInputOnFocus(false);
            new DatePickerDialog(BemVindoActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelNasicmento();
            }, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        etNascimento.setOnFocusChangeListener((v, e) -> {
            if (e) {
                etNascimento.performClick();
            }
        });
    }

    private void updateLabelNasicmento() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        perfil.setNascimento(myCalendar.getTime());
        setError(getEtNascimento(), null);
        getEtNascimento().setText(sdf.format(myCalendar.getTime()));
    }

    private void initListenerEtAltura() {
        getEtAltura().setOnClickListener((v) -> {
            getEtAltura().setShowSoftInputOnFocus(false);
            AlertDialog dialog = getAlertComPickerDecimal(getString(R.string.altura))
                    .setPositiveButton(R.string.ok, (d, which) -> {
                        perfil.setAltura(new BigDecimalUtils().getDecimalDeIteiroEDecimal(getNpInteiro().getValue(), getNpDecimal().getValue()));
                        setError(getEtAltura(), null);
                        getEtAltura().setText(getTextFromNumberPicker(getNpInteiro(), getNpDecimal(), R.string.m));
                    })
                    .create();
            dialog.show();
            setCustomPicker(dialog, 1, 2, 1, 00, 99, 65, R.string.m);
        });

        getEtAltura().setOnFocusChangeListener((v, e) -> {
            if (e) {
                getEtAltura().performClick();
            }
        });
    }

    private void initListenerEtPeso() {
        getEtPeso().setOnClickListener((v) -> {
            getEtPeso().setShowSoftInputOnFocus(false);
            AlertDialog dialog = getAlertComPickerDecimal(getString(R.string.peso))
                    .setPositiveButton(R.string.ok, (d, which) -> {
                        perfil.setPeso(new BigDecimalUtils().getDecimalDeIteiroEDecimal(getNpInteiro().getValue(), getNpDecimal().getValue()));
                        setError(getEtPeso(), null);
                        getEtPeso().setText(getTextFromNumberPicker(getNpInteiro(), getNpDecimal(), R.string.kg));
                    })
                    .create();
            dialog.show();
            setCustomPicker(dialog, 15, 300, 75, 0, 9, 0, R.string.kg);
        });

        getEtPeso().setOnFocusChangeListener((v, e) -> {
            if (e) {
                getEtPeso().performClick();
            }
        });
    }

    private AlertDialog.Builder getAlertComPickerDecimal(String title) {
        return new DialogUtils()
                .build(BemVindoActivity.this, R.layout.number_picker_decimal, title)
                .setNegativeButton(R.string.cancelar, ((d, which) -> d.cancel()))
                .setCancelable(true);
    }

    private String getTextFromNumberPicker(NumberPicker npInteiro, NumberPicker npDecimal, int id_unidade_medida) {
        return npInteiro.getValue() + "." + npDecimal.getValue() + getString(id_unidade_medida);
    }

    private void setCustomPicker(AlertDialog dialog, int minInteiro, int maxInteiro, int defaultInteiro, int minDecimal, int maxDecimal, int defaultDecimal, int id_unidade_medida) {
        Button btnCancelar = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button btnConfirmar = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        setDialogButtonColor(btnCancelar);
        setDialogButtonColor(btnConfirmar);

        setNpInteiro(dialog.findViewById(R.id.np_inteiro));
        getNpInteiro().setWrapSelectorWheel(false);
        setPickerValues(getNpInteiro(), minInteiro, maxInteiro, defaultInteiro);

        setNpDecimal(dialog.findViewById(R.id.np_decimal));
        getNpDecimal().setWrapSelectorWheel(false);
        setPickerValues(getNpDecimal(), minDecimal, maxDecimal, defaultDecimal);

        TextView tvUnidadeMedidaPeso = dialog.findViewById(R.id.tv_unidade_medida_picker);
        tvUnidadeMedidaPeso.setText(getString(id_unidade_medida));
    }

    private void setPickerValues(NumberPicker np, int min, int max, int defaultValue) {
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(defaultValue);
    }

    private void setDialogButtonColor(Button button) {
        button.setBackgroundColor(getColor(R.color.branco));
        button.setTextColor(getColor(R.color.primaria));
    }

    public void btnConfirmarBemVindoOnClick(View view) {
        if (getRgSexo().getCheckedRadioButtonId() != -1) {
            perfil.setSexo(Sexo.getByResourceId(getRgSexo().getCheckedRadioButtonId()));
            setError((RadioButton) getRgSexo().getChildAt(rgSexo.getChildCount() - 1), null);
        } else {
            RadioButton radioButton = (RadioButton) getRgSexo().getChildAt(rgSexo.getChildCount() - 1);
            setError(radioButton, "Infome sexo");
            return;
        }

        if (perfil.getNascimento() == null) {
            setError(getEtNascimento(), "Informe nascimento");
            return;
        }

        if (perfil.getAltura() == null) {
            setError(getEtAltura(), "Informe altura");
            return;
        }

        if (perfil.getPeso() == null) {
            setError(getEtPeso(), "Informe peso");
            return;
        }


    }

    private <T extends TextView> void setError(T view, String erro) {
        view.setError(erro);
    }
}
