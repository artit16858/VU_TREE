package com.good.maxky_2208.pro_tree_aaa.Manu.New.TOP5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.good.maxky_2208.pro_tree_aaa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxky_2208 on 15/5/2560.
 */

public class Adapter_tree extends ArrayAdapter {
    List list = new ArrayList();

    public Adapter_tree( Context context,  int resource) {
        super(context, resource);
    }


    public void add( contacts_tree object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = layoutInflater.inflate(R.layout.row,parent,false);
            contactHolder = new ContactHolder();
            contactHolder.tx_name=(TextView) row.findViewById(R.id.txt_head);
            contactHolder.ratingBar=(RatingBar) row.findViewById(R.id.rating_tree);


            row.setTag(contactHolder);

        }else {
            contactHolder = (ContactHolder)row.getTag();
        }
        contacts_tree contacts = (contacts_tree) this.getItem(position);
        contactHolder.tx_name.setText(contacts.getTreename());
        String rating;
        rating = contacts.getRatting();

        contactHolder.ratingBar.setRating(Float.parseFloat(rating));


        return row;
    }

    static  class  ContactHolder{
        TextView tx_name;
        RatingBar ratingBar;


    }
}
