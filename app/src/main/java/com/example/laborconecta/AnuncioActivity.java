package com.example.laborconecta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnuncioActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    String nome, telefone, ramo, corpo;
    AnunciosAdapter adapter= new AnunciosAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        if (bundle!= null){
             nome= bundle.getString("nome");
             telefone= bundle.getString("telefone");
             ramo= bundle.getString("ramo");
             corpo= bundle.getString("corpo");
        }
        recyclerView = findViewById(R.id.anunciosRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(AnuncioActivity.this)
        );
    }

    public void addNovoAnuncioOnClick(View v){
        DataModel.getInstance().anuncios.add(
                new Anuncios(nome, telefone, ramo, corpo)
        );
        adapter.notifyItemInserted(0);
    }
}