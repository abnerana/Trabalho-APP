package br.com.cotemig.abner.financas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        edtmes = (EditText) findViewById(R.id.mesRel);

        // Icone na tela
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icone);

    }


    public void inserir_despesa (View v) {

        Intent irParaIserir = new Intent(this,InserirDespesa.class);
        startActivity(irParaIserir);
    }

    public void relatorio_mes (View v) {

        String teste = "teste";
        String mes = edtmes.getText().toString();
        Intent irParaListaDespesas = new Intent(this,VerDespesas.class);
        irParaListaDespesas.putExtra("Mes_sel",mes );
        irParaListaDespesas.putExtra("MesSel", teste) ;
        startActivity(irParaListaDespesas);
    }

    public void preferencias (View v) {

        Intent irParaPreferencias = new Intent(this,PreferenceActivity.class);
        startActivity(irParaPreferencias);
    }


}
