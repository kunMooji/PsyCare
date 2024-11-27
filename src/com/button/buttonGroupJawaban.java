package com.button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class buttonGroupJawaban extends JPanel {
    private int selectedValue = -1;
    private SurveyButton[] surveyButtons;
    private JLabel valueLabel;
    
    // Custom button class for a more elegant look
    private class SurveyButton extends JToggleButton {
        private static final int ARC_RADIUS = 15;
        private boolean isHovered = false;
        
        public SurveyButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color baseColor = isSelected() ? new Color(52, 152, 219) : 
                              isHovered ? new Color(41, 128, 185) : 
                              new Color(189, 195, 199);
            
            g2.setColor(baseColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, ARC_RADIUS, ARC_RADIUS));
            
            g2.setColor(Color.WHITE);
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 40);
        }
    }
    
    public buttonGroupJawaban() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        setBackground(Color.WHITE);
        
        ButtonGroup group = new ButtonGroup();
        surveyButtons = new SurveyButton[5];
        
        // Define button configurations
        String[] buttonLabels = {"Buruk", "Kurang Baik", "Cukup Baik", "Baik", "Sangat Baik"};
        int[] buttonValues = {2, 4, 6, 8, 10};
        
        // Create and configure buttons
        for (int i = 0; i < buttonLabels.length; i++) {
            final int value = buttonValues[i];
            surveyButtons[i] = new SurveyButton(buttonLabels[i]);
            
            surveyButtons[i].addActionListener(e -> {
                selectedValue = value;
                updateValueLabel();
                highlightSelectedButton();
            });
            
            group.add(surveyButtons[i]);
            add(surveyButtons[i]);
        }
        
        // Create styled value label
        valueLabel = new JLabel("Pilih Penilaian");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel.setForeground(new Color(44, 62, 80));
        add(valueLabel);
    }
    
    private void updateValueLabel() {
        valueLabel.setText(String.format("Nilai Anda: %d", selectedValue));
    }
    
    private void highlightSelectedButton() {
        for (SurveyButton button : surveyButtons) {
            button.repaint();
        }
    }
    
    public int getSelectedValue() {
        return selectedValue;
    }
    
    // Test method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Survei Interaktif");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 200);
            frame.setLocationRelativeTo(null);
            
            buttonGroupJawaban surveyPanel = new buttonGroupJawaban();
            frame.add(surveyPanel);
            
            frame.setVisible(true);
        });
    }
}