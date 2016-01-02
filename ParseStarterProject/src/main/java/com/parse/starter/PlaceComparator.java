package com.parse.starter;

import java.util.Comparator;

/**
 * This class provides the "Comparator" objects for sorting the list of places
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
