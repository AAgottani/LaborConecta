package com.example.laborconecta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class AnunciosAdapter extends RecyclerView.Adapter<AnunciosAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomeRTextView, telefoneRTextview, ramoRTextView, corpoRTextView;
        public ViewHolder(View itemView){
            super(itemView);
            nomeRTextView= itemView.findViewById(R.id.nomeRTextView);
            telefoneRTextview= itemView.findViewById(R.id.telefoneRTextView);
            ramoRTextView=itemView.findViewById(R.id.ramoRTextView);
            corpoRTextView= itemView.findViewById(R.id.corpoREditTextMulti);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(
                R.layout.anuncio_recycle_view,
                parent,
                false
                );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anuncios ad = DataModel.getInstance().anuncios.get(position);
        holder.nomeRTextView.setText(ad.getNome());
        holder.telefoneRTextview.setText(ad.getTelefone());
        holder.ramoRTextView.setText(ad.getRamo());
        holder.corpoRTextView.setText(ad.getCorpo());
    }

    @Override
    public int getItemCount() {
        return DataModel.getInstance().anuncios.size();
    }
}
