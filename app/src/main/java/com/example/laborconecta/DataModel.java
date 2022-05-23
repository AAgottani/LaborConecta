package com.example.laborconecta;

import java.util.ArrayList;

public class DataModel {
    private static DataModel instance = new DataModel();


    DataModel(){
        anuncios.add(
                new Anuncios("nome", "telefone", "ramo", "corpo")
        );

    }



    public static DataModel getInstance(){
        return instance;
    }
   public ArrayList<Anuncios> anuncios =new ArrayList<>();




}

