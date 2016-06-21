package br.com.cotemig.abner.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.cotemig.abner.entity.Despesa;

public class DataBaseSQLite extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "despesas.db";
    private static int VERSAO = 2;

    public DataBaseSQLite(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    public DataBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Despesa.DespesaEntity.Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Despesa.DespesaEntity.Drop_table);
        onCreate(db);
    }

}
