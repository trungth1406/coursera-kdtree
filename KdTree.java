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
        this.root = insert(root, null, p, true, false);
    }


    private Node insert(Node curent, Node parentNode, Point2D point2D, boolean isVertical,
                        boolean isLeftOrDown) {
        if (curent == null) {
            RectHV rectHV;
            if (parentNode == null) {
                rectHV = new RectHV(0, 0, 1, 1);
            }
            else if (!isVertical) {
                RectHV parentNodeRect = parentNode.rect;
                Point2D parentPoint = parentNode.p;
                if (isLeftOrDown) {
                    rectHV = new RectHV(parentNodeRect.xmin(), parentNodeRect.ymin(),
                                        parentPoint.x(), parentNodeRect.ymax());
                }
                else {
                    rectHV = new RectHV(parentPoint.x(), parentNodeRect.ymin(),
                                        parentNodeRect.xmax(), parentNodeRect.ymax());
                }
            }
            else {
                RectHV parentNodeRect = parentNode.rect;
                Point2D parentPoint = parentNode.p;
                if (isLeftOrDown) {
                    rectHV = new RectHV(parentNodeRect.xmin(), parentNodeRect.ymin(),
                                        parentNodeRect.xmax(), parentPoint.y());
                }
                else {
                    rectHV = new RectHV(parentNodeRect.xmin(), parentPoint.y(),
                                        parentNodeRect.xmax(), parentNodeRect.ymax());
                }
            }
            this.size += 1;
            return new Node(point2D, rectHV);
        }

        if (isVertical) {
            if (point2D.x() < curent.p.x()) {
                curent.lb = insert(curent.lb, curent, point2D, false, true);
            }
            else if (point2D.x() >= curent.p.x()) {
                curent.rt = insert(curent.rt, curent, point2D, false, false);
            }
        }
        else {
            if (point2D.y() < curent.p.y()) {
                curent.lb = insert(curent.lb, curent, point2D, true, true);
            }
            else if (point2D.y() >= curent.p.y()) {
                curent.rt = insert(curent.rt, curent, point2D, true, false);
            }
        }

        return curent;
    }


    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return search(p) != null;
    }

    private Node search(Point2D point2D) {
        return search(root, point2D, true);
    }

    private Node search(Node curent, Point2D point2D, boolean isVertical) {
        if (curent == null) {
            return null;
        }
        if (curent.p.equals(point2D)) return curent;

        if (isVertical) {
            if (point2D.x() < curent.p.x()) {
                return search(curent.lb, point2D, false);
            }
            else if (point2D.x() >= curent.p.x()) {
                return search(curent.rt, point2D, false);
            }
        }
        else {
            if (point2D.y() < curent.p.y()) {
                return search(curent.lb, point2D, false);
            }
            else if (point2D.y() >= curent.p.y()) {
                return search(curent.rt, point2D, false);
            }
        }
        return null;
    }


    public void draw() {
        draw(root, true);
    }

    private void draw(Node current, boolean isVertical) {
        if (current == null) return;
        // Draw root
        this.drawLine(current, isVertical);
        if (isVertical) {
            if (current.lb != null) {
                draw(current.lb, false);
            }

            if (current.rt != null) {
                draw(current.rt, false);
            }
        }
        else {
            if (current.lb != null) {
                draw(current.lb, true);
            }

            if (current.rt != null) {
                draw(current.rt, true);
            }
        }
    }

    private void drawLine(Node current, boolean isVertical) {
        StdDraw.setPenRadius(0.009);
        current.p.draw();
        Point2D currentPoint = current.p;
        StdDraw.setPenRadius();
        if (current.rect == null) {
            currentPoint.drawTo(new Point2D(currentPoint.x(), 1));
            currentPoint.drawTo(new Point2D(currentPoint.x(), 0));
        }
        else if (isVertical) {
            currentPoint.drawTo(new Point2D(currentPoint.x(), current.rect.ymax()));
            currentPoint.drawTo(new Point2D(currentPoint.x(), current.rect.ymin()));
        }
        else {
            currentPoint.drawTo(new Point2D(current.rect.xmax(), currentPoint.y()));
            currentPoint.drawTo(new Point2D(current.rect.xmin(), currentPoint.y()));
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> allPointsInRange = new ArrayList<>();
        this.range(root, rect, allPointsInRange);
        return allPointsInRange;
    }

    private void range(Node node, RectHV rect, List<Point2D> allPointsInRange) {
        if (node == null) return;

        if (rect.contains(node.p) && !allPointsInRange.contains(node.p)) {
            allPointsInRange.add(node.p);
        }

        if (node.lb != null && rect.intersects(node.lb.rect)) {
            range(node.lb, rect, allPointsInRange);
        }

        if (node.rt != null && rect.intersects(node.rt.rect)) {
            range(node.rt, rect, allPointsInRange);
        }

    }


    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node currentNode, Point2D p, Point2D currentNearest) {

        Point2D currentNodePoint = currentNode.p;
        if (currentNode.rect.distanceSquaredTo(p) < currentNearest.distanceSquaredTo(p)) {
            if (currentNodePoint.distanceSquaredTo(p) < currentNearest.distanceSquaredTo(p)) {
                currentNearest = new Point2D(currentNodePoint.x(), currentNodePoint.y());
            }

            if (currentNode.lb != null) {
                currentNearest = nearest(currentNode.lb, p, currentNearest);
            }

            if (currentNode.rt != null) {
                currentNearest = nearest(currentNode.rt, p, currentNearest);
            }
        }

        return new Point2D(currentNearest.x(), currentNearest.y());
    }


    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
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
