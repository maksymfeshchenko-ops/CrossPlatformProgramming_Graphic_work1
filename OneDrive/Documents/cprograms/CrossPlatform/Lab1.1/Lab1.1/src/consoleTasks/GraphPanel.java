package consoleTasks;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

class GuiApplication extends JFrame {
    private JTextField paramField;
    private FFunction func = new FFunction();
    private GraphPanel graphPanel;

    public GuiApplication() {
        setTitle("Аналіз функції та її похідної");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Панель налаштувань
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Параметр a:"));
        paramField = new JTextField("0.5", 5);
        controlPanel.add(paramField);

        JButton drawButton = new JButton("Побудувати графіки");
        drawButton.setBackground(new Color(46, 204, 113));
        drawButton.setForeground(Color.WHITE);
        controlPanel.add(drawButton);

        // Панель для графіків
        graphPanel = new GraphPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        drawButton.addActionListener(e -> {
            try {
                double a = Double.parseDouble(paramField.getText());
                func.setA(a);
                graphPanel.updateData(func);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Введіть число!");
            }
        });
    }

    // Внутрішній клас для малювання
    class GraphPanel extends JPanel {
        private List<Point2D> pointsF = new ArrayList<>();
        private List<Point2D> pointsD = new ArrayList<>();

        public void updateData(Evaluatable f) {
            pointsF.clear();
            pointsD.clear();
            // Генеруємо точки для графіка
            for (double x = 0; x <= 10; x += 0.1) {
                pointsF.add(new Point2D(x, f.evalf(x)));
                pointsD.add(new Point2D(x, NumMethods.der(x, 1e-4, f)));
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int margin = 50;

            // Малюємо осі
            g2.drawLine(margin, h / 2, w - margin, h / 2); // X
            g2.drawLine(margin, margin, margin, h - margin); // Y

            if (pointsF.isEmpty()) return;

            // Масштабування (спрощене)
            double xScale = (w - 2.0 * margin) / 10.0;
            double yScale = (h - 2.0 * margin) / 2.0;

            // Малюємо функцію f(x) - СИНЯ
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(2f));
            drawCurve(g2, pointsF, margin, h/2, xScale, yScale);
            g2.drawString("— f(x)", w - 80, 70);

            // Малюємо похідну f'(x) - ЧЕРВОНА
            g2.setColor(Color.RED);
            drawCurve(g2, pointsD, margin, h/2, xScale, yScale);
            g2.drawString("— f'(x)", w - 80, 90);

            g2.setColor(Color.BLACK);
            g2.drawString("0", margin - 15, h/2 + 15);
            g2.drawString("x", w - margin + 10, h/2 + 5);
        }

        private void drawCurve(Graphics2D g2, List<Point2D> pts, int ox, int oy, double xs, double ys) {
            Path2D path = new Path2D.Double();
            path.moveTo(ox + pts.get(0).getX() * xs, oy - pts.get(0).getY() * ys);
            for (int i = 1; i < pts.size(); i++) {
                path.lineTo(ox + pts.get(i).getX() * xs, oy - pts.get(i).getY() * ys);
            }
            g2.draw(path);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiApplication().setVisible(true));
    }
}
