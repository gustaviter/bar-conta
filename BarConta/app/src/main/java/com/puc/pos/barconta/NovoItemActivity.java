package com.puc.pos.barconta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NovoItemActivity extends AppCompatActivity {

    EditText nome;
    EditText valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        Button btnSalvarItem = (Button) findViewById(R.id.btnSalvarItem);
        btnSalvarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItem(v);
            }
        });
    }

    public void adicionarItem(View v) {
        nome = (EditText)findViewById(R.id.nomeItem);
        valor = (EditText)findViewById(R.id.valorItem);

        if(nome.getText().length() > 0 && valor.getText().length() > 0){
            String _nome = nome.getText().toString();
            Double _valor = Double.parseDouble(valor.getText().toString());

            DBAdapter db = new DBAdapter(this);
            db.open();
            db.insertItem(_nome, _valor.toString());
            db.close();

            Intent menuActivity = new Intent(this, MenuActivity.class);
            startActivity(menuActivity);
        }
    }
}
