package com.parse.starter;

import java.util.Comparator;

/**
 * Created by Gal on 01/01/2016.
 */
public class PlaceComparator implements Comparator<Place>{


    public PlaceComparator sortByGrade() {

       return new PlaceComparator() {

           @Override
           public int compare(Place lhs, Place rhs) {
               if (lhs.getGrade() < rhs.getGrade())
                   return 1;
               else
                   return -1;
           }
       };
    }

    public PlaceComparator sortByName() {

        return new PlaceComparator() {
            @Override
            public int compare(Place lhs, Place rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        };
    }






    @Override
    public int compare(Place lhs, Place rhs) {
        return 0;
    }
}
