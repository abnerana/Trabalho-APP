package br.com.cotemig.abner.financas;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Abner on 19/06/2016.
 */
public class PreferenceActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "Preferences";
    private EditText valOrcamento;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opcoes_activity);

        // Icone na tela
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icone);


        valOrcamento = (EditText)findViewById(R.id.edt_orc);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        valOrcamento.setText(settings.getString("PrefValor", ""));
    }


    public void salvar (View view) {


            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("PrefValor", valOrcamento.getText().toString());

            Toast.makeText(getApplicationContext(), R.string.str_renda_salva, Toast.LENGTH_LONG).show();

            editor.commit();
            finish();
    }


    public void voltar(View view) {
        finish();
    }


}
