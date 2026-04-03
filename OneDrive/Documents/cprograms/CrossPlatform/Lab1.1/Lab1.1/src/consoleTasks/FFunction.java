package consoleTasks;

public class FFunction implements Evaluatable {
    private double a;

    public FFunction(double a) { this.a = a; }
    public FFunction() { this(1.0); }

    public double getA() { return a; }
    public void setA(double a) { this.a = a; }

    @Override
    public double evalf(double x) {
        return Math.exp(-a * x * x) * Math.sin(x);
    }
}
