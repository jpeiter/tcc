package br.edu.utfpr.pb.jeanpeiter.tcc.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.DialogUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.ResourcesUtils;
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
    public NumberPicker npKg;
    public NumberPicker npG;
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bem_vindo);
        initViews();
        replaceResources();
        initListeners();
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

        String bemVindo = resourcesUtils.replace(R.string.bem_vindo_x, "Jean");
        getTvBemVindo().setText(resourcesUtils.negrito(bemVindo, bemVindo.indexOf(',') + 1, bemVindo.length()));
        getTvInformeSobre().setText(resourcesUtils.pontoFinal(getString(R.string.informe_sobre_voce)));
    }

    private void initListeners() {
        initListenerEtNascimento();
        initListenerEtPeso();
    }

    private void initListenerEtPeso() {
        etPeso.setOnClickListener((v) -> {
            etPeso.setShowSoftInputOnFocus(false);

            AlertDialog dialog = new DialogUtils()
                    .build(BemVindoActivity.this, R.layout.dialog_peso, getString(R.string.peso))
                    .setPositiveButton(R.string.ok, (d, which) -> {
                        String pesoString = getNpKg().getValue() + "." + getNpG().getValue() + "kg";
                        getEtPeso().setText(pesoString);
                    })
                    .setNegativeButton(R.string.cancelar, ((d, which) -> d.cancel()))
                    .setCancelable(true)
                    .create();
            dialog.show();

            setCustomResourceDialogPeso(dialog);

        });

        etPeso.setOnFocusChangeListener((v, e) -> {
            if (e) {
                etPeso.performClick();
            }
        });
    }

    private void setCustomResourceDialogPeso(AlertDialog dialog) {
        Button btnCancelar = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button btnConfirmar = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        setDialogButtonColor(btnCancelar);
        setDialogButtonColor(btnConfirmar);

        setNpKg(dialog.findViewById(R.id.np_peso_kg));
        getNpKg().setWrapSelectorWheel(false);
        setPickerValues(getNpKg(), 15, 300, 75);

        setNpG(dialog.findViewById(R.id.np_peso_g));
        getNpG().setWrapSelectorWheel(false);
        setPickerValues(getNpG(), 0, 9, 0);

        TextView tvUnidadeMedidaPeso = dialog.findViewById(R.id.tv_unidade_medida_peso_dialog);
        tvUnidadeMedidaPeso.setText(R.string.kg);
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
        etNascimento.setText(sdf.format(myCalendar.getTime()));
    }

    public void btnConfirmarBemVindoOnClick(View view) {


    }
}
