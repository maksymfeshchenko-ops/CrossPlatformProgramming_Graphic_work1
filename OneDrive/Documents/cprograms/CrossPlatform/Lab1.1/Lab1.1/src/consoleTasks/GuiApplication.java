package consoleTasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GuiApplication extends JFrame {
    private JTextField paramField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private FFunction func = new FFunction();

    public GuiApplication() {
        setTitle("Розрахунково-графічна робота №1");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Панель керування (вгорі)
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Параметр a:"));
        paramField = new JTextField("0.5", 5);
        topPanel.add(paramField);

        JButton calcButton = new JButton("Обчислити");
        calcButton.setBackground(new Color(70, 130, 180));
        calcButton.setForeground(Color.WHITE);
        topPanel.add(calcButton);

        JButton saveButton = new JButton("Зберегти у файл");
        topPanel.add(saveButton);

        // Таблиця результатів (центр)
        String[] columns = {"x", "f(x)", "f'(x) (похідна)"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Додавання компонентів у вікно
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Логіка кнопки "Обчислити"
        calcButton.addActionListener(e -> calculate());

        // Логіка кнопки "Зберегти"
        saveButton.addActionListener(e -> saveToFile());
    }

    private void calculate() {
        try {
            double a = Double.parseDouble(paramField.getText());
            func.setA(a);
            tableModel.setRowCount(0); // Очищення таблиці

            for (double x = 1.5; x <= 6.5; x += 0.2) { // Крок 0.2 для наочності в таблиці
                double fVal = func.evalf(x);
                double dVal = NumMethods.der(x, 1.0e-4, func);
                tableModel.addRow(new Object[]{
                        String.format("%.2f", x),
                        String.format("%.6e", fVal),
                        String.format("%.6e", dVal)
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Введіть коректне число для параметра a");
        }
    }

    private void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter("GUI_Results.dat"))) {
            out.printf("%10s %15s %15s\n", "x", "f(x)", "f'(x)");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                out.printf("%10s %15s %15s\n",
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2));
            }
            JOptionPane.showMessageDialog(this, "Дані збережено у файл GUI_Results.dat");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Помилка при збереженні файлу");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GuiApplication().setVisible(true);
        });
    }
}
