package consoleTasks;
import java.util.*;

abstract class Interpolator implements Evaluatable {
    abstract public int numPoints();
    abstract public Point2D getPoint(int i);

    @Override
    public double evalf(double x) {
        double res = 0.0;
        int numData = numPoints();
        for (int k = 0; k < numData; k++) {
            double numer = 1.0, denom = 1.0;
            for (int j = 0; j < numData; j++) {
                if (j != k) {
                    numer *= (x - getPoint(j).getX());
                    denom *= (getPoint(k).getX() - getPoint(j).getX());
                }
            }
            res += getPoint(k).getY() * numer / denom;
        }
        return res;
    }
}

public class ListInterpolation extends Interpolator {
    private List<Point2D> data = new ArrayList<>();

    public void addPoint(Point2D pt) { data.add(pt); }
    public void clear() { data.clear(); }
    public void sort() { Collections.sort(data); }
    @Override public int numPoints() { return data.size(); }
    @Override public Point2D getPoint(int i) { return data.get(i); }
}
