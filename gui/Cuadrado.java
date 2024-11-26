package gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class Cuadrado extends JPanel {

    private int x;
    private int y;
    private int alto;
    private int ancho;
    private Rectangle2D cuadro;
    private Color color = Color.WHITE;

    public Cuadrado(int x, int y, int alto, int ancho) {
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
        // Configurar el tamaño preferido del panel
        setPreferredSize(new Dimension(ancho, alto));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        cuadro = new Rectangle2D.Double(x, y, ancho, alto);
        g2d.fill(cuadro);
    }

    public void letraCorrecta() {
        this.setBackground(Color.GREEN);
    }

    public void letraIncorrecta() {
        this.setBackground(Color.DARK_GRAY);
    }

    public void letraPresente() {
        this.setBackground(Color.YELLOW);
    }

    public void setbg() {
        this.setBackground(Color.BLACK);
    }

    public void setVacio() {
        this.setBackground(Color.LIGHT_GRAY);
    }

}

