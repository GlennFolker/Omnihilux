package omnihilux;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;

public class Draws {
    private static final Vec2 v1 = new Vec2(), v2 = new Vec2(), v3 = new Vec2();

    public static void fCircle(Position pos, float radius) {
        fCircle(pos.getX(), pos.getY(), radius);
    }

    public static void fCircle(float x, float y, float radius) {
        fCircle(x, y, radius, 0f, 1f);
    }

    public static void fCircle(Position pos, float radius, float rotation, float fraction) {
        fCircle(pos.getX(), pos.getY(), radius, rotation, fraction);
    }

    public static void fCircle(float x, float y, float radius, float rotation, float fraction) {
        int vertices = Mathf.round((float)Lines.circleVertices(radius * fraction), 2);

        float coverage = 360f * fraction;
        for(int i = 0; i < vertices; i += 2) {
            v1.trns(rotation + (float)i / vertices * coverage, radius).add(x, y);
            v2.trns(rotation + (i + 1f) / vertices * coverage, radius).add(x, y);
            v3.trns(rotation + (i + 2f) / vertices * coverage, radius).add(x, y);
            Fill.quad(x, y, v1.x, v1.y, v2.x, v2.y, v3.x, v3.y);
        }
    }

    public static void tri(float x, float y, float width, float length, float angle) {
        v1.trns(angle - 90f, width / 2f);
        v2.trns(angle, length);

        Fill.tri(
            x - v1.x, y - v1.y,
            x + v2.x, y + v2.y,
            x + v1.x, y + v1.y
        );
    }
}
