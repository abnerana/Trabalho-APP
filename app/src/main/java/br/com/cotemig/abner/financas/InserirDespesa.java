package br.com.cotemig.abner.financas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.cotemig.abner.DAO.DespesaDAO;
import br.com.cotemig.abner.entity.Despesa;


/**
 * Created by Abner on 31/05/2016.
 */
public class InserirDespesa extends AppCompatActivity {

    private EditText descricaoDesp;
    private EditText valorDesp;
    private EditText diaDesp;
    private EditText mesDesp;
    private int idDespesa;
    private DespesaDAO despesaDAO;
    private boolean modoEditar = false;
    Button btn_editar;
    public static final String PREFS_NAME = "Preferences";
    private double controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir_despesa_activity);
        descricaoDesp = (EditText) findViewById(R.id.descricaoDespesa);
        valorDesp = (EditText) findViewById(R.id.valorDespesa);
        diaDesp = (EditText) findViewById(R.id.diaDespesa);
        mesDesp = (EditText) findViewById(R.id.mesDespesa);

        // Icone na tela
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icone);


        if(despesaDAO == null)
        {
            despesaDAO = new DespesaDAO(this);
        }

        btn_editar = (Button) findViewById(R.id.botao_insere);

        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();

        if (intent.hasExtra("Descricao")) {
            descricaoDesp.setText(bundle.getString("Descricao"));
            valorDesp.setText(bundle.getString("Valor"));
            diaDesp.setText(bundle.getString("Dia"));
            mesDesp.setText(bundle.getString("Mes"));
            idDespesa = Integer.parseInt(bundle.getString("ID"));
            this.controle = Double.parseDouble(bundle.getString("Valor"));

            btn_editar.setText(R.string.str_btn_editar);
            modoEditar = true;
        }


    }

    @SuppressLint("ShowToast")
    public void inserir_registro (View view) {

        String testeDia = diaDesp.getText().toString();
        String testeMes = diaDesp.getText().toString();
        String testeValor = valorDesp.getText().toString();

        if(!testeDia.equals("") && !testeMes.equals("") && !testeValor.equals("")) {

            //pegando valores

            final Despesa despesaIsere = new Despesa();
            despesaIsere.setDescricao(descricaoDesp.getText().toString());
            despesaIsere.setValor(Double.parseDouble(valorDesp.getText().toString()));
            despesaIsere.setDia(Integer.parseInt(diaDesp.getText().toString()));
            despesaIsere.setMes(Integer.parseInt(mesDesp.getText().toString()));

            SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
            double subTotal ;
            if (settings.getString("PrefValor", "").equals("")) {
                subTotal = 0;
            }else {
                subTotal = Double.parseDouble(settings.getString("PrefValor", ""));
            }

            if(despesaIsere.getDia()>0 && despesaIsere.getDia()<32 && despesaIsere.getMes()>0 && despesaIsere.getMes()<13) {

                //salvar dados no banco

                if (modoEditar) {

                    double totalMes = despesaDAO.totalMes(Integer.parseInt(mesDesp.getText().toString()))
                                                            + Double.parseDouble(valorDesp.getText().toString()) - this.controle;

                    if(subTotal < totalMes){//inicio estouro atualizar
                    AlertDialog.Builder alerta = new AlertDialog.Builder(InserirDespesa.this);
                    alerta.setTitle(R.string.str_alerta);
                        alerta.setMessage(R.string.str_msg_dlg_est);
                        alerta.setPositiveButton(R.string.str_dlg_s, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Despesa despesa = new Despesa(idDespesa, descricaoDesp.getText().toString(), Double.parseDouble(valorDesp.getText().toString()),
                                        Integer.parseInt(diaDesp.getText().toString()), Integer.parseInt(mesDesp.getText().toString()));
                                despesaDAO.Atualizar(despesa);
                                Toast.makeText(getApplicationContext(), R.string.str_msg_rg_editado, Toast.LENGTH_LONG).show();

                            }
                        });
                        alerta.setNegativeButton(R.string.str_dlg_n, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(getApplicationContext(), R.string.str_dlg_cancel, Toast.LENGTH_LONG).show();
                            }
                        });

                        alerta.create().show();

                    }//fim if mensagem de estouro
                    else {
                        Despesa despesa = new Despesa(idDespesa, descricaoDesp.getText().toString(), Double.parseDouble(valorDesp.getText().toString()),
                                Integer.parseInt(diaDesp.getText().toString()), Integer.parseInt(mesDesp.getText().toString()));
                        despesaDAO.Atualizar(despesa);
                        Toast.makeText(this, R.string.str_msg_rg_editado, Toast.LENGTH_LONG).show();
                    }

                } else {

                    double totalMes = despesaDAO.totalMes(Integer.parseInt(mesDesp.getText().toString())) + Double.parseDouble(valorDesp.getText().toString());

                    if(subTotal < totalMes){//inicio estouro salvar

                        AlertDialog.Builder alerta = new AlertDialog.Builder(InserirDespesa.this);
                        alerta.setTitle(R.string.str_alerta);
                        alerta.setMessage(R.string.str_msg_dlg_est);
                        alerta.setPositiveButton(R.string.str_dlg_s, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Long id = despesaDAO.Insere(despesaIsere);
                                Toast.makeText(getApplicationContext(), getString(R.string.str_msg_rg_salvo), Toast.LENGTH_LONG).show();
                            }
                        });
                        alerta.setNegativeButton(R.string.str_dlg_n, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(getApplicationContext(), R.string.str_dlg_cancel, Toast.LENGTH_LONG).show();
                            }
                        });

                        alerta.create().show();

                    }//fim if mensagem de estouro
                    else {
                        Long id = despesaDAO.Insere(despesaIsere);
                        Toast.makeText(this, getString(R.string.str_msg_rg_salvo), Toast.LENGTH_LONG).show();

                    }
                }
                //apos salvar/editar o registro volta para a tela principal
                finish();
            }//final if testa valores válidos de mes e dia
            else{
                Toast.makeText(this, getString(R.string.str_mesdia_max_min), Toast.LENGTH_LONG).show();
            }


        }//final if testa se tem valores
        else{
            Toast.makeText(this, getString(R.string.str_not_val), Toast.LENGTH_LONG).show();
        }


    }


    public void voltar(View view) {
        finish();
    }

}
