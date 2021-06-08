package com.example.atividadesescolares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atividadesescolares.db.EscolaContract;
import com.example.atividadesescolares.db.EscolaHelper;

public class CadastroActivity extends AppCompatActivity {

    EditText edtMateria;
    EditText edtAtividade;
    EditText edtData;
    EditText edtObs;
    Button btnSalvar;
    EscolaHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtMateria = findViewById(R.id.edtMateria);
        edtAtividade = findViewById(R.id.edtAtividade);
        edtData = findViewById(R.id.edtData);
        edtObs = findViewById(R.id.edtObs);
        btnSalvar = findViewById(R.id.btnSalvar);
        helper = new EscolaHelper(CadastroActivity.this);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();


        if(bundle != null)
        {
            String materia = bundle.getString("materia");
            String atividade = bundle.getString("atividade");
            String data = bundle.getString("data");
            String observacao = bundle.getString("obs");

            edtMateria.setText(materia);
            edtAtividade.setText(atividade);
            edtData.setText(data);
            edtObs.setText(observacao);

        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(edtMateria.getText().toString().isEmpty() ||
                        edtAtividade.getText().toString().isEmpty() ||
                        edtData.getText().toString().isEmpty() ||
                        edtObs.getText().toString().isEmpty())
                {
                    Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String materia = edtMateria.getText().toString();
                    String atividade = edtAtividade.getText().toString();
                    String data = edtData.getText().toString();
                    String obs = edtObs.getText().toString();

                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(EscolaContract.Columns.MATERIA, materia);
                    values.put(EscolaContract.Columns.ATIVIDADE, atividade);
                    values.put(EscolaContract.Columns.DATAENTREGA, data);
                    values.put(EscolaContract.Columns.OBSERVACOES, obs);

                    if(bundle != null)
                    {
                        final int id = bundle.getInt("id");
                        db.update(EscolaContract.TABLE, values, EscolaContract.Columns._ID + "=" + id, null);
                        Toast.makeText(CadastroActivity.this, "Lista escolar atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        db.insertWithOnConflict(EscolaContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                        Toast.makeText(CadastroActivity.this, "Lista escolar cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    }


                    startActivity(new Intent(CadastroActivity.this, MainActivity.class));

                }

            }
        });

    }
}