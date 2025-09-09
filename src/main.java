package org.clkairos;

import javax.swing.*;  
import java.awt.*;

public class Main {
    public static void main(String[] args){
        Main main = new Main();
        main.init();
    }

    public void init(){
        JFrame frame = new JFrame("Test JFrame");  
        JPanel panel = new JPanel();  
        JButton button = new JButton("Click Me");  
        JLabel label = new JLabel("Hello World!");  
        panel.add(button);  
        panel.add(label);  
        frame.add(panel);
    }

}