package com.puc.pos.barconta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItem(v);
            }
        });

        Button btnFecharConta = (Button) findViewById(R.id.btnFecharConta);
        btnFecharConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecharConta(v);
            }
        });
    }

    public void adicionarItem(View v) {
        Intent itemActivity = new Intent(this, NovoItemActivity.class);
        startActivity(itemActivity);
    }

    public void fecharConta(View v) {
        Intent contaActivity = new Intent(this, ResumoContaActivity.class);
        startActivity(contaActivity);
    }
}
