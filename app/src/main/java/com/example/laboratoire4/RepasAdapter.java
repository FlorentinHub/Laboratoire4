package com.example.laboratoire4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class    RepasAdapter extends RecyclerView.Adapter<RepasAdapter.RepasViewHolder> {

    private Context context;
    private List<Repas> repasList;
    private OnItemClickListener clickListener;

    // Interface pour gérer les clics d'éléments
    public interface OnItemClickListener {
        void onItemClick(String nomRepas);
    }

    public RepasAdapter(Context context, List<Repas> repasList, OnItemClickListener clickListener) {
        this.context = context;
        this.repasList = repasList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RepasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new RepasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepasViewHolder holder, int position) {
        // Récupérez l'élément de données pour cette position
        Repas repas = repasList.get(position);

        // Assurez-vous que le TextView est correctement initialisé
        TextView tvmenuItemName = holder.itemView.findViewById(R.id.tv_menuItemName);
        TextView tvmenuItemPrice= holder.itemView.findViewById(R.id.tv_menuItemPrice);
        TextView test= holder.itemView.findViewById(R.id.editNomClient);
        TextView test2= holder.itemView.findViewById(R.id.editNumTel);

        if (tvmenuItemName != null) {
            tvmenuItemName.setText(repas.getNom());
            tvmenuItemPrice.setText(String.valueOf(repas.getPrix()+"$"));
//            tvmenuItemPrice.setText((int) repas.getPrix());
//            test.setText(repas.getCategorie());
//            test2.setText(repas.getDescription());
        }else{
            Log.e("Erreur Logee manuellement - ","Le textView est null:");
        }
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onItemClick(repas.getNom());
            }
        });
    }


    @Override
    public int getItemCount() {
        if(repasList!=null) {
            return repasList.size();
        }
        else{
            return -1;
        }
    }

    public class RepasViewHolder extends RecyclerView.ViewHolder {
        TextView nomRepasTextView;
        TextView prixRepasTextView;

        public RepasViewHolder(View itemView) {
            super(itemView);
            nomRepasTextView = itemView.findViewById(R.id.tv_menuItemName);
//            prixRepasTextView = itemView.findViewById(R.id.menuScrollView);
        }
    }
}
