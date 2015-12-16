package com.puc.pos.barconta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    TextView valorTotalConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent intent = getIntent();

        Bundle params = intent.getExtras();

        if(params!=null)
        {
            String valorTotal = params.getString("valorTotal");
            valorTotalConta = (TextView)findViewById(R.id.textValorPagar);
            valorTotalConta.setText(valorTotal);
        }

        Button btnCalcular = (Button) findViewById(R.id.btnOk);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retornar(v);
            }
        });
    }

    private void retornar(View v){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
