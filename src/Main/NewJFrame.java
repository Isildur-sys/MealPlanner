/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.scene.control.ComboBox;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.*;

/**
 *
 * @author maba9
 */
public class NewJFrame extends javax.swing.JFrame {
    public static LinkedList<Meal> savedMeals = new LinkedList();
    private static JTable planGrid;
    private static DefaultTableModel planGridMod;
    private int menuPanelX;
    private int menuPanelY;
    public static String[][] previousMatrix;
    public static Preferences pref;
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        pref = Preferences.userNodeForPackage(NewJFrame.class);
        previousMatrix = new String[20][7];
        this.setResizable(false);
        this.setUndecorated(true);
        this.setVisible(true);
        initComponents();
        File f = new File("meals.db");
        if (f.exists() && savedMeals.size() == 0) {
            try { 
                loadMealsFromDatabase();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        addPanels();
        jPanel7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jPanel3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jPanel6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setPreferredSize(screenDimension());
        pack();
        this.setLocationRelativeTo(null);
    }
    
    public static boolean savedMealsContains(String name) {
        for (Meal m: savedMeals) {
            if (m.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public void removeFromDatabase(String name) {
        try {
            FileInputStream fis = new FileInputStream("meals.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            
            String line = reader.readLine();
            while (line != null) {
                if (line.equals("name:" + name)) {
                    
                }
            }
        } catch (Exception e) {
            
        }
    }
    
    private Dimension screenDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth()*0.8;
        double height = screenSize.getHeight()*0.5;
        return new Dimension((int)width, (int)height);
    }
    
    
    private DefaultCellEditor createCombo(int row, int column) {
        JComboBox cb = new JComboBox();
        cb.setEditable(true);
        cb.setModel(new javax.swing.DefaultComboBoxModel<>());
        cb.addItem("");
        for (Meal m: savedMeals) {
            cb.addItem(m.getName());
        }
       
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            
        });
        
        cb.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()== ItemEvent.SELECTED) {
                    String str = cb.getSelectedItem().toString();

                    if (previousMatrix[row][column] == null) {
                        previousMatrix[row][column] = ""; 
                    }
                    String previous = previousMatrix[row][column];
                    if (!previous.equals(str)) { 
                        mapComboToInfo(column, str, previous);
                        previousMatrix[row][column] = str;
                        
                    }
                    if (valueExists(str)) {
                        if (row+1 == planGrid.getRowCount()) {
                            planGridMod.setRowCount(planGrid.getRowCount()+1);

                        }
                    }
                    
                }
                
            }
            
        });
        DefaultCellEditor e = new DefaultCellEditor(cb);
        return e;
    }
    
    private void mapComboToInfo(int col, String sel, String prev) {
        Meal m;
        Meal pre;
        int newCals;
        int fats;
        int carbs;
        int protein;
        switch (col) {
           
            case 0:
                m = new Meal();
                pre = new Meal();
                for (Meal me: savedMeals) {
                    if (me.getName().equals(sel)) {
                        m = me;   
                    }
                    if (me.getName().equals(prev)) {
                        pre = me;
                    }
                }
                //cals
                newCals = Integer.parseInt(calories0.getText()) + m.getCalories() - pre.getCalories();
                calories0.setText(newCals + "");
                //fats
                fats = Integer.parseInt(fats0.getText()) + m.getFats() - pre.getFats();
                fats0.setText(fats + "");
                //carbs
                carbs = Integer.parseInt(carbs0.getText()) + m.getCarbs() - pre.getCarbs();
                carbs0.setText(carbs + "");
                //protein
                protein = Integer.parseInt(protein0.getText()) + m.getProtein() - pre.getProtein();
                protein0.setText(protein + "");
                break;
            case 1:
                m = new Meal();
                pre = new Meal();
                for (Meal me: savedMeals) {
                    if (me.getName().equals(sel)) {
                        m = me;  
                    }
                    if (me.getName().equals(prev)) {
                        pre = me;
                    }
                }
                //cals
                newCals = Integer.parseInt(calories1.getText()) + m.getCalories() - pre.getCalories();
                calories1.setText(newCals + "");
                //fats
                fats = Integer.parseInt(fats1.getText()) + m.getFats() - pre.getFats();
                fats1.setText(fats + "");
                //carbs
                carbs = Integer.parseInt(carbs1.getText()) + m.getCarbs() - pre.getCarbs();
                carbs1.setText(carbs + "");
                //protein
                protein = Integer.parseInt(protein1.getText()) + m.getProtein() - pre.getProtein();
                protein1.setText(protein + "");
                break;
            case 2:
                m = new Meal();
                pre = new Meal();
                for (Meal me: savedMeals) {
                    if (me.getName().equals(sel)) {
                        m = me;   
                    }
                    if (me.getName().equals(prev)) {
                        pre = me;
                    }
                }
                //cals
                newCals = Integer.parseInt(calories2.getText()) + m.getCalories() - pre.getCalories();
                calories2.setText(newCals + "");
                //fats
                fats = Integer.parseInt(fats2.getText()) + m.getFats() - pre.getFats();
                fats2.setText(fats + "");
                //carbs
                carbs = Integer.parseInt(carbs2.getText()) + m.getCarbs() - pre.getCarbs();
                carbs2.setText(carbs + "");
                //protein
                protein = Integer.parseInt(protein2.getText()) + m.getProtein() - pre.getProtein();
                protein2.setText(protein + "");
                break;
            case 3:
                m = new Meal();
                pre = new Meal();
                for (Meal me: savedMeals) {
                    if (me.getName().equals(sel)) {
                        m = me;    
                    }
                    if (me.getName().equals(prev)) {
                        pre = me;
                    }
                }
                //cals
                newCals = Integer.parseInt(calories3.getText()) + m.getCalories() - pre.getCalories();
                calories3.setText(newCals + "");
                //fats
                fats = Integer.parseInt(fats3.getText()) + m.getFats() - pre.getFats();
                fats3.setText(fats + "");
                //carbs
                carbs = Integer.parseInt(carbs3.getText()) + m.getCarbs() - pre.getCarbs();
                carbs3.setText(carbs + "");
                //protein
                protein = Integer.parseInt(protein3.getText()) + m.getProtein() - pre.getProtein();
                protein3.setText(protein + "");
                break;
            case 4:
                m = new Meal();
                pre = new Meal();
                for (Meal me: savedMeals) {
                    if (me.getName().equals(sel)) {
                        m = me;
                    }
                    if (me.getName().equals(prev)) {
                        pre = me;
                    }
                }
                //cals
                newCals = Integer.parseInt(calories4.getText()) + m.getCalories() - pre.getCalories();
                calories4.setText(newCals + "");
                //fats
                fats = Integer.parseInt(fats4.getText()) + m.getFats() - pre.getFats();
                fats4.setText(fats + "");
                //carbs
                carbs = Integer.parseInt(carbs4.getText()) + m.getCarbs() - pre.getCarbs();
                carbs4.setText(carbs + "");
                //protein
                protein = Integer.parseInt(protein4.getText()) + m.getProtein() - pre.getProtein();
                protein4.setText(protein + "");
                break;
            case 5:
                m = new Meal();
                pre = new Meal();
                for (Meal me: savedMeals) {
                    if (me.getName().equals(sel)) {
                        m = me;
                    }
                    if (me.getName().equals(prev)) {
                        pre = me;
                    }
                }
                //cals
                newCals = Integer.parseInt(calories5.getText()) + m.getCalories() - pre.getCalories();
                calories5.setText(newCals + "");
                //fats
                fats = Integer.parseInt(fats5.getText()) + m.getFats() - pre.getFats();
                fats5.setText(fats + "");
                //carbs
                carbs = Integer.parseInt(carbs5.getText()) + m.getCarbs() - pre.getCarbs();
                carbs5.setText(carbs + "");
                //protein
                protein = Integer.parseInt(protein5.getText()) + m.getProtein() - pre.getProtein();
                protein5.setText(protein + "");
                break;
                
        }
    }
    
    public static boolean valueExists(String name) {
        for (Meal m: savedMeals) {
            if (m.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private void addPanels() { 
        //plangrid creation
        Object[] columns = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        planGridMod = new DefaultTableModel(columns, 6);
        //planGridMod.setColumnIdentifiers(columns);
        planGrid = new JTable(planGridMod){
            @Override
            public final TableCellEditor getCellEditor(int row, int column) {
                return createCombo(row, column);
            }
        };

        planGrid.setGridColor(Color.decode("#6F3998"));
        planGrid.setShowGrid(true);
        planGrid.setSelectionBackground(Color.decode("#9F7BBA"));
        planGrid.setBackground(Color.decode("#9F7BBA"));
        planGrid.setFont(new Font("",1,16));
        planGrid.setRowHeight(30);
        
        //panels
        panelWest.setBackground(Color.decode("#4F0D82"));
        panelSouth.setBackground(Color.decode("#6F3998"));
        panelNorth.getViewport().setBackground(Color.WHITE); 
        panelNorth.getViewport().add(planGrid);
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelWest = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelSouth = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        calories0 = new javax.swing.JLabel();
        fats0 = new javax.swing.JLabel();
        carbs0 = new javax.swing.JLabel();
        protein0 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        calories1 = new javax.swing.JLabel();
        fats1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        carbs1 = new javax.swing.JLabel();
        protein1 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        calories2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        fats2 = new javax.swing.JLabel();
        carbs2 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        protein2 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        protein3 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        carbs3 = new javax.swing.JLabel();
        fats3 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        calories3 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        calories4 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        fats4 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        carbs4 = new javax.swing.JLabel();
        protein4 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        calories5 = new javax.swing.JLabel();
        fats5 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        protein5 = new javax.swing.JLabel();
        carbs5 = new javax.swing.JLabel();
        calories6 = new javax.swing.JLabel();
        fats6 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        carbs6 = new javax.swing.JLabel();
        protein6 = new javax.swing.JLabel();
        panelNorth = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MainFrame");
        setLocation(new java.awt.Point(0, 0));
        setSize(new java.awt.Dimension(0, 0));

        panelWest.setMaximumSize(new java.awt.Dimension(200, 200));
        panelWest.setPreferredSize(new java.awt.Dimension(1, 1));

        jPanel4.setBackground(new java.awt.Color(79, 13, 130));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icons/icons8_add_file_32px_1.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(230, 230, 230));
        jLabel10.setText("Write Plan To File");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("MealPlanner");

        jSeparator2.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jSeparator2.setMinimumSize(new java.awt.Dimension(100, 10));

        jPanel6.setBackground(new java.awt.Color(79, 13, 130));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel6MouseReleased(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icons/icons8_plus_32px.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(230, 230, 230));
        jLabel12.setText("Add/Edit Recipes");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(79, 13, 130));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icons/icons8_save_32px_1.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(230, 230, 230));
        jLabel8.setText("Save This Plan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(79, 13, 130));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel7MousePressed(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icons/icons8_goal_32px_1.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(230, 230, 230));
        jLabel16.setText("Set My Goals");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelWestLayout = new javax.swing.GroupLayout(panelWest);
        panelWest.setLayout(panelWestLayout);
        panelWestLayout.setHorizontalGroup(
            panelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelWestLayout.createSequentialGroup()
                .addGroup(panelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelWestLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelWestLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelWestLayout.setVerticalGroup(
            panelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWestLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel5.setText("Calories:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel6.setText("Fats:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel13.setText("Carbs:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel14.setText("Protein:");

        calories0.setText("0");

        fats0.setText("0");

        carbs0.setText("0");

        protein0.setText("0");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel19.setText("Calories:");

        calories1.setText("0");

        fats1.setText("0");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel22.setText("Fats:");

        carbs1.setText("0");

        protein1.setText("0");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel25.setText("Protein:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel26.setText("Carbs:");

        calories2.setText("0");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel20.setText("Calories:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel23.setText("Fats:");

        fats2.setText("0");

        carbs2.setText("0");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel27.setText("Carbs:");

        protein2.setText("0");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel28.setText("Protein:");

        protein3.setText("0");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel29.setText("Protein:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel30.setText("Carbs:");

        carbs3.setText("0");

        fats3.setText("0");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel24.setText("Fats:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel21.setText("Calories:");

        calories3.setText("0");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel31.setText("Calories:");

        calories4.setText("0");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel32.setText("Fats:");

        fats4.setText("0");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel33.setText("Carbs:");

        carbs4.setText("0");

        protein4.setText("0");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel34.setText("Protein:");

        calories5.setText("0");

        fats5.setText("0");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel35.setText("Fats:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel36.setText("Carbs:");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel37.setText("Calories:");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel38.setText("Protein:");

        protein5.setText("0");

        carbs5.setText("0");

        calories6.setText("0");

        fats6.setText("0");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel39.setText("Calories:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel40.setText("Fats:");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel41.setText("Carbs:");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel42.setText("Protein:");

        carbs6.setText("0");

        protein6.setText("0");

        javax.swing.GroupLayout panelSouthLayout = new javax.swing.GroupLayout(panelSouth);
        panelSouth.setLayout(panelSouthLayout);
        panelSouthLayout.setHorizontalGroup(
            panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSouthLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fats0, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(calories0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel22)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calories1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(fats1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calories2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(fats2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calories3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fats3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calories4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fats4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calories5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fats5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calories6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fats6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(carbs6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(protein6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelSouthLayout.setVerticalGroup(
            panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSouthLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(calories6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(fats6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(carbs6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(protein6)))
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(calories5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(fats5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(carbs5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(protein5)))
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(calories4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(fats4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(carbs4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(protein4)))
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(calories2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(fats2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(carbs2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(protein2)))
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(calories1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(fats1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(carbs1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(protein1)))
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(calories0))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(fats0))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(carbs0))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(protein0)))
                    .addGroup(panelSouthLayout.createSequentialGroup()
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(calories3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(fats3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(carbs3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(protein3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(79, 13, 130));
        jPanel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel5MouseDragged(evt);
            }
        });
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel5MousePressed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(79, 13, 130));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icons/icons8_multiply_32px.png"))); // NOI18N
        jLabel4.setOpaque(true);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelWest, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelNorth)
                    .addComponent(panelSouth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(panelSouth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelWest, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelNorth)
                        .addGap(336, 336, 336))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            this.dispose();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        jLabel4.setBackground(Color.red);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setBackground(Color.decode("#4f0d82"));
    }//GEN-LAST:event_jLabel4MouseExited

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        jPanel3.setBackground(Color.decode("#9f7bba"));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        jPanel3.setBackground(Color.decode("#4f0d82"));
    }//GEN-LAST:event_jPanel3MouseExited

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        jPanel4.setBackground(Color.decode("#9f7bba"));
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        jPanel4.setBackground(Color.decode("#4f0d82"));
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-menuPanelX, y-menuPanelY);
    }//GEN-LAST:event_jPanel5MouseDragged

    private void jPanel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
        menuPanelX = evt.getX();
        menuPanelY = evt.getY();
    }//GEN-LAST:event_jPanel5MousePressed

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        jPanel6.setBackground(Color.decode("#9f7bba"));
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        jPanel6.setBackground(Color.decode("#4f0d82"));
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MousePressed
        jPanel6.setBackground(Color.decode("#2D1A44"));
        new EditFrame();
    }//GEN-LAST:event_jPanel6MousePressed

    private void jPanel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseReleased
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>(){

            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(80);
                jPanel6.setBackground(Color.decode("#4f0d82"));
                return null;
            }
         };
        worker.execute();
    }//GEN-LAST:event_jPanel6MouseReleased

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        jPanel7.setBackground(Color.decode("#4f0d82"));
    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        jPanel7.setBackground(Color.decode("#9f7bba"));
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MousePressed
        new GoalsFrame();
    }//GEN-LAST:event_jPanel7MousePressed
    
    public static void updateDatabase() {
        //update database
        try {
            File temp = new File("temp.txt");
            File old = new File("meals.txt");
            
            FileWriter fw = new FileWriter(temp, true);
            BufferedWriter w = new BufferedWriter(fw);
            Collections.sort(savedMeals, new MealComparator());

            for (Meal m: savedMeals) {
                w.write(Meal.NEW_MEAL + "\n");
                w.write(Meal.MEAL_NAME + m.getName() + "\n");
                w.write(Meal.MEAL_CALORIES + m.getCalories() + "\n");
                w.write(Meal.MEAL_FATS + m.getFats() + "\n");
                w.write(Meal.MEAL_CARBS + m.getCarbs() + "\n");
                w.write(Meal.MEAL_PROTEIN + m.getProtein() + "\n");
                w.write(Meal.MEAL_INGREDIENTS + m.getIngredientsAsString() + "\n");
            }
            
            w.close();
            if (old.exists()) {
                Path path = Paths.get(old.getAbsolutePath());
                Files.delete(path);
            }
            Path tempPath = Paths.get(temp.getAbsolutePath());
            temp.renameTo(old);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadMealsFromDatabase() throws ClassNotFoundException, SQLException {
        ResultSet res = MealDatabase.displayMeals();
        while(res.next()) {
            Meal m = new Meal();
            m.setName(res.getString("mname"));
            m.setCalories(res.getString("calories"));
            m.setCarbs(res.getString("carbs"));
            m.setFats(res.getString("fats"));
            m.setProtein(res.getString("protein"));
            m.setIngredients(res.getString("ingredients"));
            m.setInstructions(res.getString("instructions"));
            savedMeals.add(m);
            System.out.println(m.getName());
        }
        
    }
    
    public void loadMeals(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            
            String line = reader.readLine();
            while (line != null) {
                Meal current = new Meal();
                line = reader.readLine();
                if (line.startsWith(current.MEAL_NAME)) {
                    current.setName(line.substring(current.MEAL_NAME.length()).trim());
                }
                line = reader.readLine();
                if (line.startsWith(current.MEAL_CALORIES)) {
                    current.setCalories(line.substring(current.MEAL_CALORIES.length()).trim());
                }
                line = reader.readLine();
                if (line.startsWith(current.MEAL_FATS)) {
                    current.setFats(line.substring(current.MEAL_FATS.length()).trim());
                }
                line = reader.readLine();
                if (line.startsWith(current.MEAL_CARBS)) {
                    current.setCarbs(line.substring(current.MEAL_CARBS.length()).trim());
                }
                line = reader.readLine();
                if (line.startsWith(current.MEAL_PROTEIN)) {
                    current.setProtein(line.substring(current.MEAL_PROTEIN.length()).trim());
                }
                line = reader.readLine();
                if (line.startsWith(current.MEAL_INGREDIENTS)) {
                    String ing = line.substring(current.MEAL_INGREDIENTS.length()).trim() + "\n";
                    line = reader.readLine();
                    while (line != null && !line.equals(current.NEW_MEAL)) {
                        ing += line + "\n";
                        line = reader.readLine();
                    }
                    current.setIngredients(ing);
                }
                
                savedMeals.add(current);
            }
            
            reader.close();
            fis.close();
        } catch (IOException e) {
            
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel calories0;
    private javax.swing.JLabel calories1;
    private javax.swing.JLabel calories2;
    private javax.swing.JLabel calories3;
    private javax.swing.JLabel calories4;
    private javax.swing.JLabel calories5;
    private javax.swing.JLabel calories6;
    private javax.swing.JLabel carbs0;
    private javax.swing.JLabel carbs1;
    private javax.swing.JLabel carbs2;
    private javax.swing.JLabel carbs3;
    private javax.swing.JLabel carbs4;
    private javax.swing.JLabel carbs5;
    private javax.swing.JLabel carbs6;
    private javax.swing.JLabel fats0;
    private javax.swing.JLabel fats1;
    private javax.swing.JLabel fats2;
    private javax.swing.JLabel fats3;
    private javax.swing.JLabel fats4;
    private javax.swing.JLabel fats5;
    private javax.swing.JLabel fats6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane panelNorth;
    private javax.swing.JPanel panelSouth;
    private javax.swing.JPanel panelWest;
    private javax.swing.JLabel protein0;
    private javax.swing.JLabel protein1;
    private javax.swing.JLabel protein2;
    private javax.swing.JLabel protein3;
    private javax.swing.JLabel protein4;
    private javax.swing.JLabel protein5;
    private javax.swing.JLabel protein6;
    // End of variables declaration//GEN-END:variables
}
