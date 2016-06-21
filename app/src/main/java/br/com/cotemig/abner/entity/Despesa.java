package br.com.cotemig.abner.entity;


/**
 * Created by Abner on 31/05/2016.
 */
public class Despesa {

    public int getCod_id() {
        return cod_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public int getMes() { return mes; }

    public int getDia() {
        return dia;
    }

    public Despesa (){

    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    private int cod_id;
    private String descricao;
    private double valor;
    private int mes;
    private int dia;

    public Despesa (int cod, String descricao, double valor, int dia, int mes){
        this.cod_id = cod;
        this.descricao = descricao;
        this.valor = valor;
        this.mes = mes;
        this.dia = dia;
    }

    public String toString (){
        return "Descrição: " + this.descricao + "Valor: " + this.valor + "Dia:" + this.dia + "Mes:" + this.mes;
    }

    public static abstract class DespesaEntity {

        public static final String TABLE_NAME = "despesas";
        public static final String ID = "despesaId";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_VALOR = "valor";
        public static final String COLUMN_NAME_DIA = "diaDespesa";
        public static final String COLUMN_NAME_MES = "mesDespesa";

        public static final String [] COLUNAS = new String [] { ID , COLUMN_NAME_DESCRICAO , COLUMN_NAME_VALOR , COLUMN_NAME_DIA ,COLUMN_NAME_MES };

        public static final String Create_Table = "CREATE TABLE " +  TABLE_NAME + "(" +ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_NAME_DESCRICAO + " TEXT NOT NULL, " + COLUMN_NAME_VALOR + " REAL NOT NULL, " + COLUMN_NAME_DIA + " INTEGER NOT NULL, "
                + COLUMN_NAME_MES + " INTEGER NOT NULL);";
        public static final String Drop_table = " DROP TABLE IF EXISTS " + DespesaEntity.TABLE_NAME ;

    }

}
