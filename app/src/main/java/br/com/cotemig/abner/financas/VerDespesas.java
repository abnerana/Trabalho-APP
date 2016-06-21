package br.com.cotemig.abner.financas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;

import br.com.cotemig.abner.DAO.DespesaDAO;
import br.com.cotemig.abner.entity.Despesa;

public class VerDespesas extends AppCompatActivity {

    public static ListView lista;

    private static final String[] CAMPOS = {Despesa.DespesaEntity.COLUMN_NAME_DESCRICAO,
            Despesa.DespesaEntity.COLUMN_NAME_VALOR , Despesa.DespesaEntity.COLUMN_NAME_DIA , Despesa.DespesaEntity.COLUMN_NAME_MES};

    private Context context;
    public static SimpleAdapter simpleAdapter;
    public static DespesaDAO despesas;
    private int mes;
    private int verificador;
    public static final String PREFS_NAME = "Preferences";
    private TextView valorRenda;
    private TextView valorDespMes;
    private TextView valorSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_despesas);

        context = this;

        lista = (ListView) findViewById(R.id.lista_despesas);
        despesas = new DespesaDAO (getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();

        valorRenda = (TextView) findViewById(R.id.valRenda);
        valorDespMes = (TextView) findViewById(R.id.valDespMes);
        valorSaldo = (TextView) findViewById(R.id.valSaldo);

        // Icone na tela
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icone);



        if (intent.hasExtra("MesSel") && !bundle.getString("Mes_sel").equals("")) {

            this.verificador = Integer.parseInt(bundle.getString("Mes_sel"));
            if(this.verificador>0 && this.verificador<13) {
                this.mes = Integer.parseInt(bundle.getString("Mes_sel"));
                exibeAtualizaLista();
            }else{
                Toast.makeText(this, getString(R.string.str_mes_max_min), Toast.LENGTH_LONG).show();
                finish();
            }

        }else{
            Toast.makeText(this, getString(R.string.str_not_mes), Toast.LENGTH_LONG).show();
            finish();
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) lista.getItemAtPosition(position);

                Intent i = new Intent(context, InserirDespesa.class);
                i.putExtra("ID", map.get("ID"));
                i.putExtra("Descricao", map.get("Descricao"));
                i.putExtra("Valor", map.get("Valor"));
                i.putExtra("Dia", map.get("Dia"));
                i.putExtra("Mes", map.get("Mes"));
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> map = (HashMap<String, String>) lista.getItemAtPosition(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(VerDespesas.this);
                alerta.setTitle(R.string.str_dlg_del_title);
                alerta.setMessage(R.string.str_msg_dlg_del);
                alerta.setPositiveButton(R.string.str_dlg_s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        despesas.Deletar(Integer.parseInt(map.get("ID")));
                        exibeAtualizaLista();
                    }
                });
                alerta.setNegativeButton(R.string.str_dlg_n, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Toast.makeText(getApplicationContext(), R.string.str_dlg_cancel, Toast.LENGTH_LONG).show();
                    }
                });

                alerta.create().show();
                return false;
            }
        });

        lista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                HashMap<String, String> map = (HashMap<String, String>) lista.getSelectedItem();
                Toast.makeText(getApplicationContext(), "" + map, Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }

    public void exibeAtualizaLista() {

        simpleAdapter = new SimpleAdapter(this, despesas.getDespesasMes(this.mes), R.layout.view_despesa, new String[]{ "Descricao", "Valor2", "Dia", "Mes"}, new int[]{ R.id.textoDescricao, R.id.textoValor,R.id.textoDia, R.id.textoMes});
        lista.setAdapter(simpleAdapter);

        //formatando valores dos txt views
        DecimalFormat df = new DecimalFormat("#,##0.00");

        //seta valor renda
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        double subTotal ;
        if (settings.getString("PrefValor", "").equals("")) {
            subTotal = 0;
        }else {
            subTotal = Double.parseDouble(settings.getString("PrefValor", ""));
        }
        String renda = df.format(subTotal);
        valorRenda.setText(renda);


        //seta valor total despesas
        String total = df.format(despesas.totalMes(this.mes));
        valorDespMes.setText(total);

        //seta valor saldo
        double sub = subTotal - despesas.totalMes(this.mes);
        String saldoAtual = df.format(sub);
        valorSaldo.setText(saldoAtual);

    }


    public void onResume() {
        super.onResume();
        exibeAtualizaLista();
    }


    public void voltar(View view) {
        finish();
    }


}
