package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.plaf.basic.BasicArrowButton;

// Classe CustomScrollBarButton para personalizar os botões da barra de rolagem
class CustomScrollBarButton extends BasicArrowButton {
    private Color buttonColor;
    private Color arrowColor;

    public CustomScrollBarButton(int direction, Color buttonColor, Color arrowColor) {
        super(direction);
        this.buttonColor = buttonColor;
        this.arrowColor = arrowColor;
        setPreferredSize(new Dimension(20, 20));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(buttonColor);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(arrowColor);
        int x = (getWidth() - 8) / 2;
        int y = (getHeight() - 4) / 2;

        int[] xs;
        int[] ys;

        if (direction == NORTH) {
            xs = new int[]{x, x + 8, x + 4};
            ys = new int[]{y + 4, y + 4, y};
        } else if (direction == SOUTH) {
            xs = new int[]{x, x + 8, x + 4};
            ys = new int[]{y, y, y + 4};
        } else if (direction == WEST) {
            xs = new int[]{x + 4, x + 4, x};
            ys = new int[]{y, y + 8, y + 4};
        } else { // EAST
            xs = new int[]{x, x, x + 4};
            ys = new int[]{y, y + 8, y + 4};
        }

        g2.fillPolygon(xs, ys, 3);
        g2.dispose();
    }
}
