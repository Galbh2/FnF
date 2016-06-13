package com.parse.jooba;

import java.util.Comparator;

/**
 * This class provides the "Comparator" objects for sorting the list of places
 */
public class PlaceComparator implements Comparator<MyPlace>{


    public PlaceComparator sortByGrade() {

       return new PlaceComparator() {

           @Override
           public int compare(MyPlace lhs, MyPlace rhs) {
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
            public int compare(MyPlace lhs, MyPlace rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        };
    }






    @Override
    public int compare(MyPlace lhs, MyPlace rhs) {
        return 0;
    }
}
