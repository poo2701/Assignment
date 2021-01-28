package com.p.assignmentp.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.p.assignmentp.R;
import com.p.assignmentp.database.ProductData;
import com.p.assignmentp.databinding.ProductListItemBinding;

import java.util.List;

public class ProductLIstAdapter extends RecyclerView.Adapter<ProductLIstAdapter.ViewHolder> {
    private Context context;
    private List<ProductData> list;


    public ProductLIstAdapter(Context context, List<ProductData> list) {
        this.context = context;
        this.list = list;
    }

    public void setItems(List<ProductData> filteredModelList) {
        this.list =filteredModelList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductListItemBinding itemBinding;

        public ViewHolder(@NonNull ProductListItemBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding=itemView;
            itemBinding.setClickHandler(new ClickHandler());
        }
    }
    @NonNull
    @Override
    public ProductLIstAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductLIstAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_item, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        ProductData productModel = list.get(position);


        holder.itemBinding.setModel(productModel);
//        holder.itemBinding.setClickHandler(new ClickHandler());






    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ClickHandler{
        public void addToCart(View view){

            Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();


        }
    }


}
