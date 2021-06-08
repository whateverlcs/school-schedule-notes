package com.example.atividadesescolares.db;

import android.provider.BaseColumns;

public class EscolaContract {

    public static final String DB_NAME = "atividadesescolares";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "atividades";

    public class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String MATERIA = "materia";
        public static final String ATIVIDADE = "atividade";
        public static final String DATAENTREGA = "dataentrega";
        public static final String OBSERVACOES = "observacoes";
    }
}
