package com.swing;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

public class background extends JPanel {
    private int round = 20;
    private Color strokeColor = new Color(211, 211, 211); // Warna abu-abu muda
    private int strokeWidth = 2; // Ketebalan garis

    public background() {
        setOpaque(false);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        repaint();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        repaint();
    }

    @Override
    public void paint(Graphics grph) {
        Graphics2D g2 = (Graphics2D) grph.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the rounded rectangle
        g2.setColor(Color.WHITE); // Background warna putih
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), round, round);

        // Draw the stroke
        g2.setColor(strokeColor);
        g2.setStroke(new BasicStroke(strokeWidth));
        g2.drawRoundRect(0, 0, getWidth() - strokeWidth, getHeight() - strokeWidth, round, round);

        g2.dispose();
        super.paint(grph);
    }
}
