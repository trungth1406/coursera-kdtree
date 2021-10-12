/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        this.root = insert(root, p, true);
    }


    private Node insert(Node curent, Point2D point2D, boolean isVertical) {
        if (curent == null) return new Node(point2D, null);

        if (isVertical) {
            if (point2D.x() < curent.p.x()) {
                curent.lb = insert(curent.lb, point2D, false);
            }
            else if (point2D.x() > curent.p.x()) {
                curent.rt = insert(curent.rt, point2D, false);
            }
        }
        else {
            if (point2D.y() < curent.p.y()) {
                curent.lb = insert(curent.lb, point2D, true);
            }
            else if (point2D.y() > curent.p.y()) {
                curent.rt = insert(curent.rt, point2D, true);
            }
        }

        return curent;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return false;
    }


    public void draw() {
        draw(root, true);
    }

    private void draw(Node current, boolean isVertical) {
        if (current != null) {
            this.drawLine(current, isVertical);
            if (isVertical) {
                if (current.lb != null) {
                    drawLine(current.lb, false);
                }

                if (current.rt != null) {
                    drawLine(current.rt, false);
                }
            }
            else {
                if (current.lb != null) {
                    drawLine(current.lb, true);
                }

                if (current.rt != null) {
                    drawLine(current.rt, true);
                }
            }


        }
    }

    private void drawLine(Node current, boolean isVertical) {
        current.p.draw();
        Point2D currentPoint = current.p;
        if (isVertical) {
            Point2D nearest = this.nearest(currentPoint);
            if (nearest != null) {
                currentPoint.drawTo(new Point2D(currentPoint.x(), 0));
                currentPoint.drawTo(new Point2D(currentPoint.x(), 1));
            }
            else {
                currentPoint.drawTo(new Point2D(currentPoint.x(), 0));
                currentPoint.drawTo(new Point2D(currentPoint.x(), 1));
            }
        }
        else {
            currentPoint.drawTo(new Point2D(0, currentPoint.y()));
            currentPoint.drawTo(new Point2D(1, currentPoint.y()));
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> allPointsInRange = new ArrayList<>();

        return allPointsInRange;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return null;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }


    public static void main(String[] args) {

    }
}
