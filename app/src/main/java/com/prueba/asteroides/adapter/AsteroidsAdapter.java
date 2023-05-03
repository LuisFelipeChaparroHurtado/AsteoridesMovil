package com.prueba.asteroides.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.asteroides.R;
import com.prueba.asteroides.entity.Asteroids;

import java.util.ArrayList;

public class AsteroidsAdapter extends RecyclerView.Adapter<AsteroidsAdapter.AsteroidsViewHolder> {
    ArrayList<Asteroids> listAsteroids;
    private View.OnClickListener listener;

    public AsteroidsAdapter(ArrayList<Asteroids> listAsteroids){
        this.listAsteroids = listAsteroids;
    }
    @NonNull
    @Override
    public AsteroidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asteroid, null, false) ;
        return new AsteroidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsteroidsViewHolder holder, int position) {
        holder.id.setText("Id: "+listAsteroids.get(position).getId());
        holder.name.setText("Id: "+listAsteroids.get(position).getName());
        holder.nasa.setText("Id: "+listAsteroids.get(position).getNasa_jpl_url());
        holder.absolute.setText("Id: "+listAsteroids.get(position).getAbsolute_magnitude_h());
    }

    @Override
    public int getItemCount() {
        return listAsteroids.size();
    }

    public class AsteroidsViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, nasa, absolute;
        public AsteroidsViewHolder(@NonNull View itemView) {

            super(itemView);
            id= itemView.findViewById(R.id.id_asteroid);
            name = itemView.findViewById(R.id.name_asteroid);
            nasa = itemView.findViewById(R.id.nasa_jpl_url);
            absolute = itemView.findViewById(R.id.absolute_magnitude_h);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context =view.getContext();
                    Toast.makeText(context, "Acción de ver datos del asteroide clickeado...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
