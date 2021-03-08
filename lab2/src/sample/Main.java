package sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JPanel implements ActionListener {
    Timer timer;

    private static int maxWidth = 1600;
    private static int maxHeight = 900;

    private static int paddingX = 500;
    private static int paddingY = 250;

    private double angle = 0;

    private double scale = 0.1;
    private double delta = 0.01;

    private final double center_x = 1;
    private final double center_y = 1;

    public Main() {
        timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        g2d.setBackground(new Color(0, 128, 0));
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        g2d.setColor(new Color(255, 255, 0));
        BasicStroke bs1 = new BasicStroke(16, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
        g2d.drawRect(20, 20, 1540, 640);

        g2d.translate(maxWidth / 2, maxHeight / 2);
        g2d.rotate(angle, center_x, center_y);
        g2d.scale(scale, scale);

        GradientPaint gp = new GradientPaint(5, 25, Color.BLUE, 20, 2, Color.YELLOW, true);
        g2d.setPaint(gp);
        double points[][] = {
                { 180, 20 },
                { 110, 170 },
                { 280, 170 }
        };
        GeneralPath rect = new GeneralPath();
        rect.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            rect.lineTo(points[k][0], points[k][1]);
        rect.closePath();
        g2d.fill(rect);

        g2d.setColor(Color.BLUE);

        g2d.drawLine(60, 20, 120, 20);
        g2d.drawLine(240, 20, 310, 20);

        g2d.setColor(Color.RED);
        g2d.drawPolyline(new int[]{20, 85, 315, 370}, new int[]{20, 220, 220, 20}, 4);
    }

    public void actionPerformed(ActionEvent e) {
        if (scale < 0.01) {
            delta = -delta;
        } else if (scale > 0.7) {
            delta = -delta;
        }
        angle -= 0.1;
        scale += delta;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(maxWidth, maxHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new Main());
        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }
}