package com.example.atividadesescolares.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EscolaHelper extends SQLiteOpenHelper {

    public EscolaHelper(@Nullable Context context) {
        super(context, EscolaContract.DB_NAME, null, EscolaContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", EscolaContract.TABLE, EscolaContract.Columns.MATERIA, EscolaContract.Columns.ATIVIDADE, EscolaContract.Columns.DATAENTREGA, EscolaContract.Columns.OBSERVACOES);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EscolaContract.TABLE);
        onCreate(sqLiteDatabase);
    }

}
