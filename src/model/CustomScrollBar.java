package model;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

// Classe CustomScrollBar para personalizar a aparência da barra de rolagem
public class CustomScrollBar extends BasicScrollBarUI {
	private Color trackColor = new Color(0xDEDEDE);
	private Color thumbColor = new Color(0xC4C4C4);
	private Color buttonColor = new Color(0xDEDEDE);
	private Color arrowColor = new Color(0x8B8B8B);

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new CustomScrollBarButton(orientation, buttonColor, arrowColor);
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new CustomScrollBarButton(orientation, buttonColor, arrowColor);
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(trackColor);
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(thumbColor);
		g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 0, 0);

		g2.dispose();
	}
}
