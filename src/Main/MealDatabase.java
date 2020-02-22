/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author maba9
 */
public class MealDatabase {
    private static Connection conn;
    private static boolean beenInitialized = false;
    
    public static ResultSet displayMeals() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement s = conn.createStatement();
        ResultSet res = s.executeQuery("SELECT mname, calories, fats, carbs, protein, "
                + "type, ingredients, instructions FROM meals ORDER BY mname");
        return res;
    }

    private static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        
        conn = DriverManager.getConnection("jdbc:sqlite:Meals.db");
        initialize();
    }

    private static void initialize() throws SQLException {
        if (!beenInitialized) {
            beenInitialized = true;
            Statement s = conn.createStatement();
            ResultSet res = s.executeQuery("SELECT name FROM sqlite_master "
                    + "WHERE type='table' AND name='meals'");
     
            if (!res.next()) {
                System.out.println("Building Meal table...");
                Statement s2 = conn.createStatement();
                s2.execute("CREATE TABLE meals(id integer PRIMARY KEY,"
                        + "mname varchar(50)," + "calories integer," + "fats integer,"
                        + "carbs integer," + "protein integer," + "type varchar(20),"
                        + "ingredients varchar(200)," + "instructions varchar(200));");
                
            }
        }
    }
    
    public static void delete(String name) throws SQLException {
        String sql = "DELETE FROM meals WHERE mname = ?";
        PreparedStatement prem = conn.prepareStatement(sql);
        prem.setString(1, name);
        prem.executeUpdate();
        
    }
    
    public static void update(String oldname, Meal m) throws SQLException {
        
        String sql = "UPDATE meals SET mname = ?, "
                + "calories = ?, carbs = ?, fats = ?, protein = ?, type = ?, "
                + "ingredients = ?, instructions = ?"
                + "WHERE mname = ?";
        PreparedStatement prem = conn.prepareStatement(sql);
        prem.setString(1, m.getName());
        prem.setInt(2, m.getCalories());
        prem.setInt(3, m.getCarbs());
        prem.setInt(4, m.getFats());
        prem.setInt(5, m.getProtein());
        prem.setString(6, m.getType());
        prem.setString(7, m.getIngredientsAsString());
        prem.setString(8, m.getInstructions());
        prem.setString(9, oldname);
        prem.executeUpdate();
    }
    
    public static void addMeal(Meal m) throws ClassNotFoundException, SQLException {
        if (conn == null) {
            createConnection();
        }
        PreparedStatement p = conn.prepareStatement("INSERT INTO meals(mname,calories,fats,carbs,protein,type,ingredients,instructions) values(?,?,?,?,?,?,?,?);");

        p.setString(1, m.getName());
        p.setInt(2, m.getCalories());
        p.setInt(3, m.getFats());
        p.setInt(4, m.getCarbs());
        p.setInt(5, m.getProtein());
        p.setString(6, m.getType());
        p.setString(7, m.getIngredientsAsString());
        p.setString(8, m.getInstructions());
        p.execute();
    }
}
