package com.example.dicbio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MostraSignivicado extends AppCompatActivity {
    String Palavra = "";
    String Origem = "";
    String Descricao = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_signivicado);
        TextView Palavratx,Origemtx,Descricaotx;
        Palavratx = (TextView)  findViewById(R.id.txt_Palavra);
        Origemtx = (TextView) findViewById(R.id.txt_Origem);

        Descricaotx = (TextView) findViewById(R.id.txt_Descricao);
        Intent intent = getIntent();

        Palavra = (String) intent.getSerializableExtra("Palavra");
        Origem = (String) intent.getSerializableExtra("Origem");
        Descricao = (String) intent.getSerializableExtra("Descricao");

        Palavratx.setText(Palavra);
        Origemtx.setText(Origem);
        Descricaotx.setText(Descricao);



    }
}
