package com.good.maxky_2208.pro_tree_aaa.Manu.Trees;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.good.maxky_2208.pro_tree_aaa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxky_2208 on 26/9/2560.
 */

public class RecyclerAdapter_Tree extends RecyclerView.Adapter<RecyclerAdapter_Tree.MyViewholder> {
    Context c;
    String message;
    private List<Trees> mArrayList;
    private List<Trees> mFilteredList;
    private static final String EXTRA_MESSAGE = "";

    RecyclerAdapter_Tree(Context ctx, List<Trees> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        this.c = ctx;
    }



    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, int position) {
        holder.name.setText(mFilteredList.get(position).getName());
        holder.pos.setText(mFilteredList.get(position).getDes());
       final String urlimg = c.getResources().getString(R.string.host)+"img/";
        //final String urlimg = "http://192.168.43.205/vu_tree/img/";
        Picasso.with(c)
                .load(urlimg+mFilteredList.get(position).getImage())
                .into(holder.img);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Snackbar.make(v, players.get(pos).getName(), Snackbar.LENGTH_SHORT).show();

                Intent intent = new Intent(c, Showtree.class);
                message = mFilteredList.get(position).getId();

                intent.putExtra(EXTRA_MESSAGE, message);
                c.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Trees> filteredList = new ArrayList<>();

                    for (Trees androidVersion : mArrayList) {

                        if (androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getDes().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<Trees>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, pos;
        ImageView img;
        private ItemClickListener itemClickListener;

        public MyViewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_head);
            pos = (TextView) itemView.findViewById(R.id.txt_sub);
            img = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
}
