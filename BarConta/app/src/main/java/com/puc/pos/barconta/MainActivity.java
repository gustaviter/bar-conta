package com.puc.pos.barconta;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticar(v);
            }
        });

        DBAdapter db = new DBAdapter(this);

        db.init();
        db.insertContact("Artur","amol@pucminas.br", "123456");
        db.close();
    }

    public void autenticar(View v) {
        DBAdapter db = new DBAdapter(this);

        email = (EditText)findViewById(R.id.login);
        senha = (EditText)findViewById(R.id.senha);

        Boolean usuarioSenhaValido = false;

        db.open();

        Cursor c = db.getAllContacts();

        if(c.moveToFirst()){
            do {
                String _email = c.getString(2);
                if(_email != null && _email.equals(email.getText().toString())){
                    String _senha = c.getString(3);
                    if(_senha != null){
                        if (_senha.equals(senha.getText().toString())) {
                            usuarioSenhaValido = true;
                            break;
                        }
                    }
                }
            }while (c.moveToNext());
        }

        db.close();

        if(usuarioSenhaValido){
            Intent menuActivity = new Intent(this, MenuActivity.class);
            startActivity(menuActivity);
        }
    }
}
