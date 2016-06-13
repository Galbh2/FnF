package com.parse.jooba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * This is a complex adapter. it chooses the correct layout to infiliate
 * according to the type of the Param (boolean or numeric)
 */
public class ProfileAdapter extends RecyclerView.Adapter <ProfileAdapter.TextViewHolder> {

    private ArrayList<String> paramsList;
    private Context context;
    private LayoutInflater inflater;

    public ProfileAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.paramsList = new ArrayList<>(generateDateForComments());
    }

    public ProfileAdapter (Context context,  ArrayList<String> list){
        this(context);
        paramsList = list;
    }


    @Override
    public ProfileAdapter.TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
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



        */
        return new TextViewHolder(inflater.inflate(R.layout.comment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {

        holder.bodyTextView.setText(paramsList.get(position));

        /**

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

         */

    }


    @Override
    public int getItemCount() {
        return paramsList.size();
    }

    @Override
    public int getItemViewType (int position) {
        return super.getItemViewType(position);
        /*
        0 for boolean question
        1 for numeric question
         */
/*
        return paramsList.get(position).getType();
        */
    }

    public interface InfoClickListener {
        public void showInfoDialog (int index, int[] position);
        public void showInfoDialog (int index);
    }


    class TextViewHolder extends RecyclerView.ViewHolder {

        TextView bodyTextView;

        public TextViewHolder(View itemView){
            super(itemView);

            bodyTextView = (TextView) itemView.findViewById(R.id.body_text_view);
        }
    }

    /*
    class BoolViewHolder extends RecyclerView.ViewHolder {

        TextView bodyTextView;
        ImageView signImageView;
        ImageView infoImageView;

        public BoolViewHolder(View itemView) {
            super(itemView);

            bodyTextView = (TextView) itemView.findViewById(R.id.body_text_view);
            signImageView = (ImageView) itemView.findViewById(R.id.sign_image_view);
            infoImageView = (ImageView) itemView.findViewById((R.id.info_image_view));
            infoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InfoClickListener i = (InfoClickListener) context;


                    int[] position = new int[2];
                    v.getLocationInWindow(position);
                    i.showInfoDialog(getAdapterPosition(),position);


                    i.showInfoDialog(getAdapterPosition());

                }
            });

        }

    }
*/


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
        list.add(new Param("Payment for crew meetings",true));
        list.add(new Param("General atmosphere", 7));

        return list;

    }

    private List generateDateForComments() {

        List<String> list = new LinkedList<String>();

        list.add("המקום הכי שווה בעיר");
        list.add("מקום עבודה מומלץ");

        return list;

    }

}

