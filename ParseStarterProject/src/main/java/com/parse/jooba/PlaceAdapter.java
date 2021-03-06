package com.parse.jooba;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gal on 01/01/2016.
 */
public class PlaceAdapter extends RecyclerView.Adapter <PlaceAdapter.MyViewHolder> {

    private ArrayList<MyPlace> placesList;
    private Context context;
    private LayoutInflater inflater;
    private int lastPosition = -1;

    public PlaceAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public PlaceAdapter (Context context, ArrayList<MyPlace> list){

        this(context);
        placesList = list;
    }


    /*
    This method creates a new view from the specified layout, it's an expansive operation.
    It uses the custom class "MyViewHolder"
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.single_place_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }
    /*
    This method binds the data from a single "MyPlace" object to a single "MyViewHolder" object.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MyPlace myPlace = placesList.get(position);
        holder.nameTextView.setText(myPlace.getName());
        holder.addressTextView.setText(myPlace.getAddress());

        if (myPlace.getGrade() == 10) {
            holder.gradeTextView.setText(String.format("%.0f ", myPlace.getGrade()));
        } else if (myPlace.getGrade() == -1) {
            holder.gradeTextView.setText("?  ");
        }
        else {
            holder.gradeTextView.setText(String.format("%.1f", myPlace.getGrade()));
        }

        // Set up the wings and the color of the grade

        if ( (position % 2) == 0) {
            holder.wingsPurple.setVisibility(View.INVISIBLE);
            holder.wingsOrange.setVisibility(View.VISIBLE);
            holder.gradeTextView.setTextColor(Color.parseColor("#e57f3e"));
        } else {
            holder.wingsOrange.setVisibility(View.INVISIBLE);
            holder.wingsPurple.setVisibility(View.VISIBLE);
            holder.gradeTextView.setTextColor(Color.parseColor("#9d22b2"));
        }

        if (!myPlace.isOpenJobs()) {
            holder.openJobsText.setVisibility(View.INVISIBLE);
        }

        // This section parse the name of the logo and gets it from the "mipmap" folder..
        // in the future we will need to change it because the logos will be in the server of Parse.

        MyAnimations.animatePlace(holder, lastPosition, position, context);
        lastPosition = position;

        if (myPlace.getImage() != null) {
            holder.logoImageView.setImageBitmap(myPlace.getImage());
        } else {
            int imgId = context.getResources().getIdentifier("joba_small_logo_scaled", "mipmap", context.getPackageName());
            holder.logoImageView.setImageResource(imgId);
        }

       // int imgId = context.getResources().getIdentifier("p".concat(myPlace.getId().toLowerCase()), "mipmap", context.getPackageName());
       // if (imgId != 0) {
          //  holder.logoImageView.setImageResource(imgId);
     //   }
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView addressTextView;
        ImageView logoImageView;
        TextView gradeTextView;
        TextView openJobsText;

        ImageView wingsOrange;
        ImageView wingsPurple;

        public MyViewHolder(View itemView){
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            addressTextView = (TextView) itemView.findViewById(R.id.address_text_view);
            logoImageView = (ImageView) itemView.findViewById(R.id.logo_image_view);
            gradeTextView = (TextView) itemView.findViewById(R.id.grade_text_view);
            openJobsText = (TextView) itemView.findViewById(R.id.new_job_text_view);

            wingsOrange = (ImageView) itemView.findViewById(R.id.wings_image_view_orange);
            wingsPurple = (ImageView) itemView.findViewById(R.id.wings_image_view_purple);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PlaceClickListener p = (PlaceClickListener) context;
            p.itemClicked(v, getAdapterPosition());
        }
    }

    public interface PlaceClickListener {
        public void itemClicked (View view, int position);
    }



    /**
     *
     * @return A list with data to test
     */

    /*
    private List generateDate() {

        List<MyPlace> list = new LinkedList<MyPlace>();

        list.add(new MyPlace("1","Macdonalds", "Cinema Ciy, Rishon Lzion", 8.4));
        list.add(new MyPlace("2", "Cofix", "Ben Gurion 12, Tel Aviv", 8.8));
        list.add(new MyPlace("3", "Vaniglia", "Sokolov 34, Ramat Hasharon", 9.4));
        list.add(new MyPlace("5", "Burger Ranch", "Ramat Aviv Shopping Center", 8.4));
        list.add(new MyPlace("4", "Cafe Cafe", "Ben Gurion 2, Herzliya", 8.4));
        list.add(new MyPlace("6", "Dominos Pizza", "Ben Gurion 34, Herzliya", 7.0));
        list.add(new MyPlace("7", "Agvania Pizza", "Sokolov 35, Herzliya", 10));
        list.add(new MyPlace("8", "Aroma", "Hmlachim 2, Rannim Center", 6.7));
        list.add(new MyPlace("9", "Aldo", "Levi Ashcol 1, Holon", 9.7));


        PlaceComparator c =  new PlaceComparator();

        Collections.sort(list, c.sortByGrade());

        return list;
    }
    */
}
