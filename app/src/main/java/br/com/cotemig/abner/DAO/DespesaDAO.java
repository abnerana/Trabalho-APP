package br.com.cotemig.abner.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.cotemig.abner.database.DataBaseSQLite;
import br.com.cotemig.abner.entity.Despesa;

/**
 * Created by Abner on 31/05/2016.
 */
public class DespesaDAO {

    private DataBaseSQLite helper;

    private SQLiteDatabase db;

    public void OpenConnectionWrite()    {  db = helper.getWritableDatabase();  }

    public void OpenConnectionRead()
    {
        db = helper.getReadableDatabase();
    }

    public void CloseConnection()
    {
        db.close();
    }

    public DespesaDAO(Context context) {
        helper = new DataBaseSQLite(context);
        db = this.getDb();
    }


    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
        return db;
    }


    public long Insere (Despesa despesa)
    {
        ContentValues dados = new ContentValues();
        dados.put(Despesa.DespesaEntity.COLUMN_NAME_DESCRICAO, despesa.getDescricao());
        dados.put(Despesa.DespesaEntity.COLUMN_NAME_VALOR, despesa.getValor());
        dados.put(Despesa.DespesaEntity.COLUMN_NAME_DIA, despesa.getDia());
        dados.put(Despesa.DespesaEntity.COLUMN_NAME_MES, despesa.getMes());

        try {
            OpenConnectionWrite();
            long variavel = db.insert(Despesa.DespesaEntity.TABLE_NAME, null, dados);
            CloseConnection();
            return variavel;
        }
        catch (Exception e){
            return -1;
        }finally
        {
            CloseConnection();
        }

    }


    public int Atualizar (Despesa despesa)
    {
        try{
            OpenConnectionWrite();
            ContentValues dados = new ContentValues();
            dados.put(Despesa.DespesaEntity.COLUMN_NAME_DESCRICAO, despesa.getDescricao());
            dados.put(Despesa.DespesaEntity.COLUMN_NAME_VALOR, despesa.getValor());
            dados.put(Despesa.DespesaEntity.COLUMN_NAME_DIA, despesa.getDia());
            dados.put(Despesa.DespesaEntity.COLUMN_NAME_MES, despesa.getMes());
            String where = Despesa.DespesaEntity.ID + " = ?";
            String[] sqlArgs = new String[]{ String.valueOf(despesa.getCod_id())};
            int variavel = db.update(Despesa.DespesaEntity.TABLE_NAME, dados, where, sqlArgs);
            CloseConnection();
            return variavel;
        }catch(Exception e){
            return -1;
        }
    }

    public int Deletar (int id)
    {
        try{
            OpenConnectionWrite();
            String where = Despesa.DespesaEntity.ID + " = ?";
            String[] sqlArgs = new String[]{ String.valueOf(id)};
            int variavel = db.delete(Despesa.DespesaEntity.TABLE_NAME, where, sqlArgs);
            CloseConnection();
            return variavel;
        }catch(Exception e){
            return -1;
        }
        finally {
            CloseConnection();
        }
    }


    public ArrayList<HashMap<String,String>> getDespesasMes(int mes)
    {
        ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();

        OpenConnectionRead();
        String where = Despesa.DespesaEntity.COLUMN_NAME_MES + " = ?";
        String[] sqlArgs = new String[]{ String.valueOf(mes)};
        Cursor cursor = db.query(Despesa.DespesaEntity.TABLE_NAME, Despesa.DespesaEntity.COLUNAS, where, sqlArgs, null, null, null);
        cursor.moveToFirst();
        DecimalFormat df = new DecimalFormat("#,##0.00");

        while (!cursor.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ID", cursor.getString(0));
            map.put("Descricao", cursor.getString(1));
            String valor = df.format(Double.parseDouble(cursor.getString(2)));
            map.put("Valor2", valor);
            map.put("Valor", cursor.getString(2));
            map.put("Dia", cursor.getString(3));
            map.put("Mes", cursor.getString(4));
            lista.add(map);
            cursor.moveToNext();
        }

        cursor.close();
        CloseConnection();
        return lista;
    }


    public double totalMes(int mes)
    {

        OpenConnectionRead();
        String where = Despesa.DespesaEntity.COLUMN_NAME_MES + " = ?";
        String[] sqlArgs = new String[]{ String.valueOf(mes)};
        Cursor cursor = db.query(Despesa.DespesaEntity.TABLE_NAME, Despesa.DespesaEntity.COLUNAS, where, sqlArgs, null, null, null);
        cursor.moveToFirst();
        double totalSoma = 0;

        while (!cursor.isAfterLast())
        {
            totalSoma += Double.parseDouble(cursor.getString(2));
            cursor.moveToNext();
        }

        cursor.close();
        CloseConnection();
        return totalSoma;
    }

    public ArrayList<HashMap<String,String>> getTodasDespesas()
    {
        ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();

        OpenConnectionRead();
        Cursor cursor = db.query(Despesa.DespesaEntity.TABLE_NAME, Despesa.DespesaEntity.COLUNAS, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ID", cursor.getString(0));
            map.put("Descricao", cursor.getString(1));
            map.put("Valor", cursor.getString(2));
            map.put("Dia", cursor.getString(3));
            map.put("Mes", cursor.getString(4));
            lista.add(map);
            cursor.moveToNext();
        }

        cursor.close();
        CloseConnection();
        return lista;
    }



}
