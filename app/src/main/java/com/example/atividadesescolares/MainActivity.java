package com.example.atividadesescolares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atividadesescolares.db.EscolaContract;
import com.example.atividadesescolares.db.EscolaHelper;

public class MainActivity extends AppCompatActivity {

    ListView lstEscola;
    Button btnAdd;
    EscolaHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstEscola = findViewById(R.id.lstEscola);
        btnAdd = findViewById(R.id.btnAdd);
        helper = new EscolaHelper(MainActivity.this);

        atualizarLista();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, CadastroActivity.class));

            }
        });

        lstEscola.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //apagar o produto pelo id
                TextView txtId = view.findViewById(R.id.txtId);
                int id = Integer.parseInt(txtId.getText().toString());

                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                String sql = String.format("DELETE FROM %s WHERE %s = %d", EscolaContract.TABLE, EscolaContract.Columns._ID, id);
                sqLiteDatabase.execSQL(sql);

                atualizarLista();

                Toast.makeText(MainActivity.this, "Lista escolar excluida!", Toast.LENGTH_LONG).show();

                return false;
            }
        });

        lstEscola.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView txtId = view.findViewById(R.id.txtId);
                int id = Integer.parseInt(txtId.getText().toString());

                SQLiteDatabase db = helper.getReadableDatabase();

                String sql = String.format("SELECT  %s, %s, %s, %s FROM %s WHERE %s = ?",
                        EscolaContract.Columns.MATERIA, EscolaContract.Columns.ATIVIDADE, EscolaContract.Columns.DATAENTREGA,
                        EscolaContract.Columns.OBSERVACOES, EscolaContract.TABLE, EscolaContract.Columns._ID);

                Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});

                cursor.moveToFirst();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("materia", cursor.getString(0));
                bundle.putString("atividade", cursor.getString(1));
                bundle.putString("data", cursor.getString(2));
                bundle.putString("obs", cursor.getString(3));
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void atualizarLista(){
        //para exibir dados da lista, precisamos receber uma instancia ativa do banco
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(EscolaContract.TABLE, new String[]{EscolaContract.Columns._ID, EscolaContract.Columns.MATERIA, EscolaContract.Columns.ATIVIDADE, EscolaContract.Columns.DATAENTREGA, EscolaContract.Columns.OBSERVACOES}, null, null, null, null, null);

        //criar um adapter para a listview setando como fonte de dados o cursor que recebeu os dados oriundos da nossa tabela produtos
        SimpleCursorAdapter listaAdapter = new SimpleCursorAdapter(
                MainActivity.this,
                R.layout.celula_lista,
                cursor,
                new String[]{EscolaContract.Columns._ID, EscolaContract.Columns.MATERIA, EscolaContract.Columns.ATIVIDADE, EscolaContract.Columns.DATAENTREGA, EscolaContract.Columns.OBSERVACOES},
                new int[]{R.id.txtId, R.id.txtMateria,R.id.txtAtividade, R.id.txtData, R.id.txtObs}, 0
        );

        lstEscola.setAdapter(listaAdapter); //seta para a listview o adaptador criado com os dados atualizados
    }

}