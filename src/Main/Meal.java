/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

/**
 *
 * @author maba9
 */
public class Meal {
    public static String NEW_MEAL = "NEW MEAL";
    public static final String MEAL_NAME = "name:";
    public static final String MEAL_CALORIES = "calories:";
    public static final String MEAL_FATS = "fat:";
    public static final String MEAL_CARBS = "carbs:";
    public static final String MEAL_PROTEIN = "protein:";
    public static final String MEAL_INGREDIENTS = "ingredients:";
    
    private String name;
    private int calories;
    private int fats;
    private int carbs;
    private int protein;
    private String type;
    private LinkedList<String> ingredients;
    private String instructions;
    
    public Meal() {
        ingredients = new LinkedList();
        calories = 0;
        fats = 0;
        carbs = 0;
        protein = 0;
        type = "Breakfast";
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public void setCalories(String c) {
        if (c != null && !c.isEmpty()) {
            int i = Integer.parseInt(c);
            calories = i;
        }
    }
    
    
    public int getFats() {
        return fats;
    }
    
    public void setFats(String f) {
        if (f != null && !f.isEmpty()) {
            int i = Integer.parseInt(f);
            fats = i;
        }
    }
    
    public int getCarbs() {
        return carbs;
    }
    
    public void setCarbs(String ca) {
        if (ca != null && !ca.isEmpty()) {
            int i = Integer.parseInt(ca);
            carbs = i;
        }
    }
    
    public int getProtein() {
        return protein;
    }
    
    public void setProtein(String p) {
        if (p != null && !p.isEmpty()) {
            int i = Integer.parseInt(p);
            protein = i;
        }
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String t) {
        if (t != null && !t.isEmpty()) {
            type = t;
        }
    }
    
    public LinkedList<String> getIngredients (LinkedList l) {
        return ingredients;
    }
    
    public String getIngredientsAsString() {
        String result = "";

        for (int ind = 0; ind < ingredients.size(); ind++) {
            result += ingredients.get(ind) + " ";
        }
        
        return result;
    }
    
    public void setIngredients(String t) {
        String[] i = t.split("\n");
        for (int ind = 0; i.length > ind; ind++) {
            ingredients.add(i[ind]);
        }
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String s) {
        instructions = s;
    }
    
    
}
