package com.example.laborconecta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NovoAnuncioActivity extends AppCompatActivity {

    TextView nome, telefone, ramo, corpoDoAnuncio;
    Button anunciarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_anuncio);
        nome= findViewById(R.id.nomeNovoAnuncioTextView);
        telefone= findViewById(R.id.telefoneNovoAnuncioTextView);
        ramo= findViewById(R.id.ramoAtividadeTextView);
        corpoDoAnuncio= findViewById(R.id.corpoAnuncioEditText);
        anunciarBtn = findViewById(R.id.anunciarNovoBtn);

        String nome_usuario= nome.getText().toString();
        String telefone_usuario= telefone.getText().toString();
        String ramo_atividade= ramo.getText().toString();
        String corpo= corpoDoAnuncio.getText().toString();


        anunciarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(NovoAnuncioActivity.this, AnuncioActivity.class);
                intent.putExtra("nome", nome_usuario);
                intent.putExtra("telefone", telefone_usuario);
                intent.putExtra("ramo", ramo_atividade);
                intent.putExtra("corpo", corpo);
                startActivity(intent);
            }
        });
    }



}