import processing.core.PVector;


abstract public class Shape {

    protected abstract PVector[] getShape();
    protected abstract boolean setGrowing(boolean g);

    // out of window detection
    public boolean outFrame(PVector[] p1, int width, int height) {
        int offset = 2;
        return polyLine(p1, offset, offset, offset, height-offset) || polyLine(p1, offset, offset, width-offset, offset) || polyLine(p1, offset, height-offset, width-offset, height-offset) || polyLine(p1, width-offset, offset, width-offset, height-offset);
    }

    // Collision detection
    public boolean polyPoly(PVector[] p1, PVector[] p2) {
        int next = 0;
        for (int current = 0; current < p1.length; current++) {
            next = current + 1;
            if (next == p1.length) next = 0;

            PVector vc = p1[current];
            PVector vn = p1[next];

            boolean collision = polyLine(p2, vc.x, vc.y, vn.x, vn.y);
            if (collision) return true;

            collision = polyPoint(p1, p2[0].x, p2[0].y);
            if (collision) return true;
        }
        return false;
    }

    public boolean polyLine(PVector[] verticles, float x1, float y1, float x2, float y2) {
        int next = 0;
        for (int current = 0; current < verticles.length; current++) {
            next = current + 1;
            if (next == verticles.length) next = 0;

            float x3 = verticles[current].x;
            float y3 = verticles[current].y;
            float x4 = verticles[next].x;
            float y4 = verticles[next].y;

            boolean hit = lineLine(x1, y1, x2, y2, x3, y3, x4, y4);
            if (hit) {
                return true;
            }
        }
        return false;
    }

    public boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        // calculate the direction of the lines
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1.065 && uB >= 0 && uB <= 1.065) {
            return true;
        }
        return false;
    }

    public boolean polyPoint(PVector[] verticles, float px, float py) {
        boolean collision = false;

        int next = 0;
        for (int current = 0; current < verticles.length; current++) {
            next = current + 1;
            if (next == verticles.length) next = 0;

            PVector vc = verticles[current];
            PVector vn = verticles[next];

            if (((vc.y > py && vn.y < py) || (vc.y < py && vn.y > py)) && (px < (vn.x - vc.x) * (py - vc.y) / (vn.y - vc.y) + vc.x)) {
                collision = !collision;
            }
        }
        return collision;
    }

    public abstract void setShape(float x, float y, float sz);
    public abstract void show();
    public abstract void grow();



}
