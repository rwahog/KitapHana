package com.kitaphana.algorithm;

import javax.swing.*;
import java.util.Scanner;

public class GUI {
    static Scanner scn = new Scanner(System.in);
    public static String read(){
        return JOptionPane.showInputDialog("");
    }
    public static void print(String s){

        JOptionPane.showMessageDialog(null,s);
    }
    public static void exit(){
        scn.close();

        print("Good bye, my friend");
        System.exit(0);
    }
    public static int readInt(){
        return Integer.parseInt(JOptionPane.showInputDialog(""));
    }
}
