package com.puc.pos.barconta;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ResumoContaActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;
    EditText qtdPessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_conta);

        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularConta(v);
            }
        });

        //Pega a referencia do ListView
        listView = (ListView) findViewById(R.id.listItem);

        createListView();
    }

    private void createListView()
    {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();

        DBAdapter db = new DBAdapter(this);
        db.open();

        Cursor c = db.getAllItem();

        if(c.moveToFirst()){
            do {
                StringBuilder item = new StringBuilder(c.getString(1));
                item.append("... R$");
                item.append(c.getString(2));
                itens.add(new ItemListView(item.toString()));
            }while (c.moveToNext());
        }

        db.close();

        //Cria o adapter
        adapterListView = new AdapterListView(this, itens);

        //Define o Adapter
        listView.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para ralagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    private void calcularConta(View v){

        qtdPessoas = (EditText)findViewById(R.id.qtdPessoas);
        Integer _qtdPessoas = Integer.valueOf(qtdPessoas.getText().toString());
        Double valorTotal = 0.0;

        DBAdapter db = new DBAdapter(this);
        db.open();

        Cursor c = db.getAllItem();

        if(c.moveToFirst()){
            do {
                valorTotal += Double.valueOf(c.getString(2));
            }while (c.moveToNext());
        }

        db.close();

        valorTotal = valorTotal/_qtdPessoas;

        Intent intent = new Intent(v.getContext(), ResultadoActivity.class);
        Bundle params = new Bundle();

        params.putString("valorTotal", valorTotal.toString());
        intent.putExtras(params);
        startActivity(intent);
    }
}
