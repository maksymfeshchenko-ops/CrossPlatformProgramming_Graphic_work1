package consoleTasks;

public class NumMethods {
    private static double meth(double x, double h, Evaluatable f) {
        return 0.5 * (f.evalf(x + h) - f.evalf(x - h)) / h;
    }

    public static double der(double x, double tol, Evaluatable f) {
        double h = 0.1;
        double one = meth(x, h, f);
        h *= 0.1;
        double two = meth(x, h, f);
        double tmp;
        while (Math.abs(two - one) >= tol) {
            h *= 0.1;
            tmp = meth(x, h, f);
            one = two;
            two = tmp;
        }
        return two;
    }
}
