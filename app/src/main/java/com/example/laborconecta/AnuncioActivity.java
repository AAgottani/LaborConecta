package com.example.laborconecta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class AnuncioActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        if (bundle!= null){
            String nome= bundle.getString("nome");
            String telefone= bundle.getString("telefone");
            String ramo= bundle.getString("ramo");
            String corpo= bundle.getString("corpo");


        }
        recyclerView = findViewById(R.id.anunciosRecyclerView);


    }
}