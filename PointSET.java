/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        this.pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return pointSet.contains(p);
    }

    public void draw() {
        for (Point2D p : this.pointSet) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> allPointsInRange = new ArrayList<>();
        for (Point2D p : this.pointSet) {
            if (rect.contains(p)) {
                allPointsInRange.add(p);
            }
        }
        return allPointsInRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (pointSet.isEmpty()) {
            return null;
        }
        Point2D nearest = this.pointSet.first();
        for (Point2D point : this.pointSet) {
            if (point.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = point;
            }
        }
        return nearest;
    }


    public static void main(String[] args) {

    }
}
