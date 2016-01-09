package com.parse.starter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * This is a complex adapter. it chooses the correct layout to infiliate
 * according to the type of the Param (boolean or numeric)
 */
public class ProfileAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private ArrayList<Param> paramsList;
    private Context context;
    private LayoutInflater inflater;

    public ProfileAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.paramsList = new ArrayList<>(generateDate());
    }

    public ProfileAdapter (Context context,  ArrayList<Param> list){
        this(context);
        paramsList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        View v = null;

        switch (viewType) {
            case 0:
                v = inflater.inflate(R.layout.params_layout_boolean, parent, false);
                vh = new BoolViewHolder(v);
                break;
            case 1:
                v = inflater.inflate(R.layout.params_layout_numerict, parent, false);
                vh = new NumViewHolder(v);
                break;
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Param p = paramsList.get(position);

        switch (holder.getItemViewType()) {
            case 0:
                BoolViewHolder vh0 = (BoolViewHolder) holder;
                vh0.bodyTextView.setText(p.getBody());
                if (p.getBoolData()) {
                    vh0.signImageView.setImageResource(R.mipmap.v);
                } else {
                    vh0.signImageView.setImageResource(R.mipmap.x);
                }
                break;
            case 1:
                NumViewHolder vh1 = (NumViewHolder) holder;
                vh1.bodyTextView.setText(p.getBody());

                if (p.getNumData() == 10) {
                    vh1.subGradeTextView.setText(String.format("%.0f", p.getNumData()));
                }else {
                    vh1.subGradeTextView.setText(String.format("%.1f", p.getNumData()));
                }
                break;
        }

    }


    @Override
    public int getItemCount() {
        return paramsList.size();
    }

    @Override
    public int getItemViewType (int position) {

        /*
        0 for boolean question
        1 for numeric question
         */

        return paramsList.get(position).getType();
    }


    class NumViewHolder extends RecyclerView.ViewHolder {

        TextView bodyTextView;
        TextView subGradeTextView;


        public NumViewHolder(View itemView){
            super(itemView);

            bodyTextView = (TextView) itemView.findViewById(R.id.body_text_view);
            subGradeTextView = (TextView) itemView.findViewById(R.id.sub_grade_text_view);
        }

    }


    class BoolViewHolder extends RecyclerView.ViewHolder {

        TextView bodyTextView;
        ImageView signImageView;

        public BoolViewHolder(View itemView) {
            super(itemView);

            bodyTextView = (TextView) itemView.findViewById(R.id.body_text_view);
            signImageView = (ImageView) itemView.findViewById(R.id.sign_image_view);

        }
    }



    /**
     *
     * @return A list with data to test
     */
    private List generateDate() {

        List<Param> list = new LinkedList<Param>();

        list.add(new Param("Average wage per hour", 25));
        list.add(new Param("Payment for apprenticeship", false));
        list.add(new Param("Shifts until 10:00PM", true));
        list.add(new Param("Payments for transportation", true));
        list.add(new Param("Payment until the 10th of the month",true));
        list.add(new Param("General atmosphere", 7));

        return list;

    }

}

