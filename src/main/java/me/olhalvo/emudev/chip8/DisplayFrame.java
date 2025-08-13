package me.olhalvo.emudev.chip8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DisplayFrame extends JFrame {
    private static final int LOGICAL_WIDTH = 64;
    private static final int LOGICAL_HEIGHT = 32;
    private static final int SCALE = 15; // Integer scaling factor
    private static final int DRAWN_WIDTH = LOGICAL_WIDTH * SCALE;   // 960
    private static final int DRAWN_HEIGHT = LOGICAL_HEIGHT * SCALE; // 480

    private long[] gameDisplay = new long[LOGICAL_HEIGHT];
    private final DisplayPanel panel;

    public DisplayFrame() {
        super("Monochrome 64x32 Display - 960x480");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DisplayPanel();
        panel.setPreferredSize(new Dimension(DRAWN_WIDTH, DRAWN_HEIGHT));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    public void render(long[] display) {
        gameDisplay = display;
        panel.repaint();
    }

    private class DisplayPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Red background
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw pixels
            for (int y = 0;  y < LOGICAL_HEIGHT; y++) {
                long row = gameDisplay[y];
                for (int x = 0;  x < LOGICAL_WIDTH; x++) {
                    boolean on = ((row >>> (LOGICAL_WIDTH - x)) & 1L) != 0;
                    g.setColor(on ? Color.WHITE : Color.BLACK);
                    g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
                }
            }
        }
    }

}