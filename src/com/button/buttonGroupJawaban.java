package com.button;
import javax.swing.*;
import java.awt.*;

public class buttonGroupJawaban extends JPanel {
    private JLabel valueLabel;
    private int selectedValue = -1; 
    
    public buttonGroupJawaban() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton buruk = new JRadioButton("Buruk");
        JRadioButton kurangBaik = new JRadioButton("Kurang Baik");
        JRadioButton cukupBaik = new JRadioButton("Cukup Baik");
        JRadioButton baik = new JRadioButton("Baik");
        JRadioButton sangatBaik = new JRadioButton("Sangat Baik");
        
        group.add(buruk);
        group.add(kurangBaik);
        group.add(cukupBaik);
        group.add(baik);
        group.add(sangatBaik);
        
        // Nilai langsung disesuaikan dengan skala 2-10
        buruk.addActionListener(e -> {
            selectedValue = 2;
            valueLabel.setText("Nilai: 2");
        });
        kurangBaik.addActionListener(e -> {
            selectedValue = 4;
            valueLabel.setText("Nilai: 4");
        });
        cukupBaik.addActionListener(e -> {
            selectedValue = 6;
            valueLabel.setText("Nilai: 6");
        });
        baik.addActionListener(e -> {
            selectedValue = 8;
            valueLabel.setText("Nilai: 8");
        });
        sangatBaik.addActionListener(e -> {
            selectedValue = 10;
            valueLabel.setText("Nilai: 10");
        });
        
        valueLabel = new JLabel("Nilai: -");
        
        add(buruk);
        add(kurangBaik);
        add(cukupBaik);
        add(baik);
        add(sangatBaik);
        add(valueLabel);
        
        setBackground(Color.WHITE);
    }
    
    public int getSelectedValue() {
        return selectedValue;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Survey Kepuasan");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.add(new buttonGroupJawaban());
            frame.setVisible(true);
        });
    }
}
