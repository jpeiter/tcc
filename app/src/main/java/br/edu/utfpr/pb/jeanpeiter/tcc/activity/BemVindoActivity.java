package br.edu.utfpr.pb.jeanpeiter.tcc.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.ResourcesUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BemVindoActivity extends AppCompatActivity implements GenericActivity, ResourceActivity {

    public TextView tvBemVindo;
    public TextView tvInformeSobre;
    public EditText nascimento;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bem_vindo);
        initViews();
        initListeners();
        replaceResources();
    }

    private void initListeners() {

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        nascimento.setOnClickListener((v) -> {
            new DatePickerDialog(BemVindoActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        nascimento.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void initViews() {
        setTvBemVindo(findViewById(R.id.tv_seja_bem_vindo));
        setTvInformeSobre(findViewById(R.id.tv_informe_sobre));
        setNascimento(findViewById(R.id.et_nascimento));
        nascimento.setShowSoftInputOnFocus(false);
    }

    @Override
    public void replaceResources() {
        ResourcesUtils resourcesUtils = new ResourcesUtils(getBaseContext());

        String bemVindo = resourcesUtils.replace(R.string.bem_vindo_x, "Jean");
        getTvBemVindo().setText(resourcesUtils.negrito(bemVindo, bemVindo.indexOf(',') + 1, bemVindo.length()));
        getTvInformeSobre().setText(resourcesUtils.pontoFinal(getString(R.string.informe_sobre_voce)));
    }
}
