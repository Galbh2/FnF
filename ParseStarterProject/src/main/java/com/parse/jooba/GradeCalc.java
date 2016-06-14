package com.parse.jooba;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Gal on 24/05/2016.
 */
public class GradeCalc {

    public static void calcParams(List<ParseObject> i_Input, final MyPlace i_Place) {

        int[] results = new int[7];

        if (i_Input.size() == 0) {
            results[6] = -1;
            i_Place.setResults(results);
            return;
        }

        int numOfReviews = i_Input.size();
        int param1 = 0, param2 = 0, param5 = 0, param6 = 0;
        double param3 = 0, param4 = 0;

        for (ParseObject object : i_Input) {

            param1 += boolToInt(object.getBoolean("param_1_bool"));
            param2 += boolToInt(object.getBoolean("param_2_bool"));
            param5 += boolToInt(object.getBoolean("param_5_bool"));
            param6 += boolToInt(object.getBoolean("param_6_bool"));

            param3 += Integer.valueOf(object.getString("param_3_text"));

            param4 += object.getDouble("param_4_num");

            // comments

            String comment = object.getString("param_7_text");

            if (comment != null && !comment.equals("")) {
                i_Place.addComment(comment);
            }
        }

        results[0] = getResult(param1, numOfReviews);
        results[1] = getResult(param2, numOfReviews);
        results[4] = getResult(param5, numOfReviews);
        results[5] = getResult(param6, numOfReviews);

        // avg wage
        results[2] = (int) Math.ceil(param3 / numOfReviews);
        // atmos
        results[3] = calcAtmo(param4, numOfReviews);

        calcFinalGrade(results);

        i_Place.setResults(results);

    }

    public static void calcParams(
            boolean param_1_bool,
            boolean param_2_bool,
            boolean param_5_bool,
            boolean param_6_bool,
            double param_3_num,
            double param_4_num,
            final MyPlace i_Place) {

        int[] results = new int[7];


        int numOfReviews = 1;
        int param1 = 0, param2 = 0, param5 = 0, param6 = 0;
        double param3 = 0, param4 = 0;



            param1 += boolToInt(param_1_bool);
            param2 += boolToInt(param_2_bool);
            param5 += boolToInt(param_5_bool);
            param6 += boolToInt(param_6_bool);

            param3 += param_3_num;
            param4 +=param_4_num;


        results[0] = getResult(param1, numOfReviews);
        results[1] = getResult(param2, numOfReviews);
        results[4] = getResult(param5, numOfReviews);
        results[5] = getResult(param6, numOfReviews);

        // avg wage
        results[2] = (int) Math.ceil(param3 / numOfReviews);
        // atmos
        results[3] = calcAtmo(param4, numOfReviews);

        calcFinalGrade(results);

        i_Place.setResults(results);

    }

    private static void calcFinalGrade(int[] i_Results) {

        double grade = 0;
        double weight = 10 / (double) (i_Results.length - 1);

        grade += i_Results[0] * weight;
        grade += i_Results[1] * weight;
        grade += i_Results[3] * weight;
        grade += i_Results[4] * weight;
        grade += i_Results[5] * weight;

        // minimum wage
        if (i_Results[2] > 19) {
            grade += weight;
        }

        i_Results[6] = (int) Math.ceil(grade);


    }

    private static int calcAtmo(double i_Sum, int i_NumOfElements) {
        double avg = i_Sum / i_NumOfElements;
        return avg >= 0.5 ? 1 : 0;
    }

    private static int getResult(int i_SumOfTrue, int i_NumOfElements){
        if (i_SumOfTrue >= (i_NumOfElements / 2.0)){
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean intToBool(int i_Input){
        return i_Input != 0 ? true : false;
    }

    public static int boolToInt(boolean i_Input){
        return i_Input ? 1 : 0;
    }
}
