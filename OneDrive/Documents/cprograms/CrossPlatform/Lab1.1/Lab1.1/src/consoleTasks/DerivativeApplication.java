package consoleTasks;
import java.io.*;

public class DerivativeApplication {
    public static void main(String[] args) throws IOException {
        // Створення тестових даних для табличної функції
        FileListInterpolation tableFun = new FileListInterpolation();
        for (double x = 1.0; x <= 7.0; x += 0.5) {
            tableFun.addPoint(new Point2D(x, Math.sin(x)));
        }
        tableFun.writeToFile("TblFunc.dat");

        // Масив функцій для обробки
        Evaluatable[] functs = new Evaluatable[2];
        functs[0] = new FFunction(0.5);
        functs[1] = tableFun;

        for (Evaluatable f : functs) {
            String fileName = f.getClass().getSimpleName() + ".dat";
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            System.out.println("Processing: " + f.getClass().getSimpleName());

            for (double x = 1.5; x <= 6.5; x += 0.05) {
                double fVal = f.evalf(x);
                double dVal = NumMethods.der(x, 1.0e-4, f);
                out.printf("%16.6e%16.6e%16.6e\n", x, fVal, dVal);
            }
            out.close();
            System.out.println("Results saved to " + fileName);
        }
    }
}
