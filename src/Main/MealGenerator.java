/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import java.util.*;
/**
 *
 * @author maba9
 */
public class MealGenerator {
    private static LinkedList<Meal> currentMeals;
    private static LinkedList<Meal> allMeals;
    private static LinkedList<Meal> res;
    
    public MealGenerator(LinkedList<Meal> c, LinkedList<Meal> a) {
        currentMeals = c;
        allMeals = a;
    }
    
    private int getScore(LinkedList<Meal> param) {
        //returns the score of the list of meals given as parameter
        int score = 0;
        int calories = 0;
        int fats = 0;
        int carbs = 0;
        int protein = 0;
        for (Meal m : param) {
            calories += m.getCalories();
            fats += m.getFats();
            carbs += m.getCarbs();
            protein += m.getProtein();
        }
        score += (NewJFrame.pref.getInt("calories", calories) - calories);
        score += (NewJFrame.pref.getInt("fats", fats) - fats);
        score += (NewJFrame.pref.getInt("carbs", carbs) - carbs);
        score += (NewJFrame.pref.getInt("protein", protein) - protein);
        return score;
    }
    
    public static LinkedList<Meal> generateMeals() {
        Random rand = new Random(System.currentTimeMillis());
        
        if (isThere("Breakfast")) {
            int index = NewJFrame.pref.getInt("count", 0) - currentMeals.size(); // how many iterations
            
            
        } else {
            
        }
    }
    
    private static boolean isThere(String type) {
        //checks if current meals contains food with the type listed as parameter
        for (Meal m : currentMeals) {
            if (m.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
    
}
