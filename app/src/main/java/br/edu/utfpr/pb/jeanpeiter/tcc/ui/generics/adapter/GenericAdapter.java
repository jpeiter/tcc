package br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.RecyclerViewOnClickInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GenericAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context mContext;
    private List<T> items = new ArrayList<>();


    public GenericAdapter(Context context) {
        this.mContext = context;
    }

    public abstract void onBindData(VH holder, T item);

    public void onBindData(VH holder, T item, View.OnClickListener listener) {
        throw new RuntimeException("Não implementado!");
    }

    public abstract VH setViewHolder(ViewGroup parent);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindData(holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public Context getContext() {
        return this.mContext;
    }
}