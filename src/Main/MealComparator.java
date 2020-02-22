/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Comparator;

/**
 *
 * @author maba9
 */
public class MealComparator implements Comparator<Meal>{

    @Override
    public int compare(Meal o1, Meal o2) {
        int compare = o1.getName().compareToIgnoreCase(o2.getName());
        return compare;
    }
    
}
