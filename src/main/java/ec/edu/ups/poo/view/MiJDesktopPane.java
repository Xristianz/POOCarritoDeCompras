package ec.edu.ups.poo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MiJDesktopPane extends JDesktopPane {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Activar suavizado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo negro
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Pistolas estilo tatuaje vector
        drawDetailedGun(g2, getWidth()/2 - 60, getHeight()/2, -45);
        drawDetailedGun(g2, getWidth()/2 + 60, getHeight()/2, 45);

        // Rosas tatuaje rojo en 4 esquinas
        drawRoseVector(g2, 80, 80, 40, Color.RED);
        drawRoseVector(g2, getWidth()-120, 80, 40, Color.RED);
        drawRoseVector(g2, 80, getHeight()-120, 40, Color.RED);
        drawRoseVector(g2, getWidth()-120, getHeight()-120, 40, Color.RED);

        // Texto con degradado
        GradientPaint gp = new GradientPaint(0, getHeight() - 100, Color.RED, 0, getHeight(), Color.WHITE);
        g2.setPaint(gp);
        g2.setFont(new Font("Serif", Font.BOLD, 48));
        String text = "GUN SHOP";
        FontMetrics fm = g2.getFontMetrics();
        int xText = (getWidth() - fm.stringWidth(text)) / 2;
        g2.drawString(text, xText, getHeight() - 50);
    }

    private void drawDetailedGun(Graphics2D g2, int cx, int cy, double angle) {
        AffineTransform old = g2.getTransform();
        g2.translate(cx, cy);
        g2.rotate(Math.toRadians(angle));

        // Corredera (parte superior)
        GradientPaint slidePaint = new GradientPaint(-30, 0, Color.LIGHT_GRAY, 30, 0, Color.DARK_GRAY);
        g2.setPaint(slidePaint);
        g2.fillRoundRect(-30, -8, 60, 16, 4, 4);

        // Detalle línea de la corredera
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(-28, -2, 28, -2);

        // Cañón delantero
        g2.setColor(Color.GRAY);
        g2.fillRect(30, -3, 10, 6);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(30, -3, 10, 6);

        // Empuñadura (mango)
        Polygon grip = new Polygon();
        grip.addPoint(20, 8);
        grip.addPoint(35, 8);
        grip.addPoint(28, 30);
        grip.addPoint(13, 30);
        g2.setColor(new Color(80,80,80)); // gris medio
        g2.fillPolygon(grip);

        // Detalles de la empuñadura
        g2.setColor(Color.WHITE);
        g2.drawLine(15, 10, 30, 10);
        g2.drawLine(15, 15, 30, 15);

        // Gatillo (pequeño arco)
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawArc(8, 5, 5, 5, 0, -180);

        // Contornos generales
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(-30, -8, 60, 16, 4, 4); // corredera
        g2.drawPolygon(grip);

        // Simular texturizado
        g2.setColor(Color.LIGHT_GRAY);
        for (int i=14; i<=30; i+=4) {
            g2.drawLine(18, i, 30, i);
        }

        g2.setTransform(old);
    }


    private void drawRoseVector(Graphics2D g2, int cx, int cy, int radius, Color color) {
        AffineTransform old = g2.getTransform();
        g2.translate(cx, cy);

        // pétalos externos con gradientes
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60);
            int px = (int)(radius * Math.cos(angle));
            int py = (int)(radius * Math.sin(angle));

            GradientPaint petalPaint = new GradientPaint(
                    px - 15, py - 15, new Color(139, 0, 0),
                    px + 15, py + 15, new Color(220, 20, 60)
            );
            g2.setPaint(petalPaint);
            g2.fillArc(px - 15, py - 15, 30, 30, 0, 360);

            // borde blanco de cada pétalo
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1));
            g2.drawArc(px - 15, py - 15, 30, 30, 0, 360);
        }

        // pétalos internos tipo espiral con degradado
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60 + 30);
            int px = (int)(radius / 2 * Math.cos(angle));
            int py = (int)(radius / 2 * Math.sin(angle));

            GradientPaint innerPetalPaint = new GradientPaint(
                    px - 10, py - 10, new Color(220, 20, 60),
                    px + 10, py + 10, Color.RED
            );
            g2.setPaint(innerPetalPaint);
            g2.fillArc(px - 10, py - 10, 20, 20, 0, 360);

            g2.setColor(Color.WHITE);
            g2.drawArc(px - 10, py - 10, 20, 20, 0, 360);
        }

        // centro con sombra y brillo
        g2.setColor(new Color(80, 0, 0));
        g2.fillOval(-8, -8, 16, 16);
        g2.setColor(Color.WHITE);
        g2.drawArc(-8, -8, 16, 16, 45, 180);

        // anillo de pétalos superpuestos para dar más realismo
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int px = (int)(radius * 0.7 * Math.cos(angle));
            int py = (int)(radius * 0.7 * Math.sin(angle));
            GradientPaint overlayPetal = new GradientPaint(
                    px - 8, py - 8, new Color(255, 0, 0),
                    px + 8, py + 8, new Color(128, 0, 0)
            );
            g2.setPaint(overlayPetal);
            g2.fillOval(px - 8, py - 8, 16, 16);

            g2.setColor(Color.WHITE);
            g2.drawOval(px - 8, py - 8, 16, 16);
        }

        // líneas de sombreado estilo tatuaje
        g2.setColor(new Color(50, 0, 0, 150));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(-radius, -radius, radius * 2, radius * 2);

        g2.setTransform(old);
    }

}
