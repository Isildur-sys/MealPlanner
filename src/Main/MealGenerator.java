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
    private static Meal[] currentMeals;
    private static Meal[] allMeals;
    private static Meal[] bestRes;
    
    private static int lowestScore;
    
    //macros from foods already selected in the daily plan
    private static int currCalories;
    private static int currCarbs;
    private static int currFats;
    private static int currProtein;
    
    public MealGenerator(LinkedList<Meal> c, LinkedList<Meal> a) {
        //transfer all meals and meals already added to the week into arrays
        currentMeals = new Meal[c.size()];
        for (int i = 0; i < c.size(); i++) {
            currentMeals[i] = c.get(i);
            Meal curr = currentMeals[i];
            currCalories += curr.getCalories();
            currCarbs += curr.getCarbs();
            currFats += curr.getFats();
            currProtein += curr.getProtein();
        }
        allMeals = new Meal[a.size()];
        for(int i = 0; i < a.size(); i++) {
            allMeals[i] = a.get(i);
        }
        
        lowestScore = -1;
    }
    
    private static int getScore(Meal[] param) {
        //returns the score of the list of meals given as parameter
        int score = 0;
        int calories = currCalories;
        int fats = currFats;
        int carbs = currCarbs;
        int protein = currProtein;
        
        for (Meal m : param) {
            calories += m.getCalories();
            fats += m.getFats();
            carbs += m.getCarbs();
            protein += m.getProtein();
        }
        score += (NewJFrame.gridPref.getInt("calories", calories) - calories);
        score += (NewJFrame.gridPref.getInt("fats", fats) - fats);
        score += (NewJFrame.gridPref.getInt("carbs", carbs) - carbs);
        score += (NewJFrame.gridPref.getInt("protein", protein) - protein);
        return Math.abs(score);
    }
    
    public Meal[] generateMeals() {
        generateBest(allMeals, NewJFrame.gridPref.getInt("count", 0), 0, new Meal[NewJFrame.gridPref.getInt("count", 0)]);
        return bestRes;
    }
    
    private static void generateBest(Meal[] seq, int num, int start, Meal[] res) {
        //generate best binomial coefficient from all the foods available and amount
        //of meals preferred
        
        if (num == 0) {
            System.out.println(NewJFrame.gridPref.getInt("count", 0));
            int currScore = getScore(res);
            
            if (currScore == -1 || currScore < lowestScore) {
                lowestScore = currScore;
                bestRes = res;
            }
            return;
        }
        for (int i = start; i <= seq.length-num; i++) {
            res[res.length-num] = seq[i];
            generateBest(seq, num-1, i+1, res);
        }
    }
    
    
    
    private static boolean thereIs(String type) {
        //checks if current meals contains food with the type listed as parameter
        for (Meal m : currentMeals) {
            if (m.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
    
}
