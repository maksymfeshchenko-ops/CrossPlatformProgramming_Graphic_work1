package consoleTasks;

// Абстрактна точка
abstract class Point {
    private double[] coords;
    public Point(int num) { this.coords = new double[num]; }
    public void setCoord(int num, double x) { coords[num-1] = x; }
    public double getCoord(int num) { return coords[num-1]; }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("(");
        for (double x : coords) res.append(x).append(", ");
        return res.substring(0, res.length()-2) + ")";
    }
}

// Точка 2D з інтерфейсом Comparable
public class Point2D extends Point implements Comparable<Point2D> {
    public Point2D(double x, double y) {
        super(2);
        setX(x); setY(y);
    }
    public Point2D() { this(0, 0); }
    public double getX() { return getCoord(1); }
    public void setX(double x) { setCoord(1, x); }
    public double getY() { return getCoord(2); }
    public void setY(double y) { setCoord(2, y); }

    @Override
    public int compareTo(Point2D pt) {
        return Double.compare(this.getX(), pt.getX());
    }
}
