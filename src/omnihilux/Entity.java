package omnihilux;

import arc.math.geom.*;

public abstract class Entity implements Position {
    public Vec2 pos = new Vec2();
    public long id = lastId++;

    private static long lastId = 0;

    public void draw() {

    }

    @Override
    public float getX() {
        return pos.x;
    }

    @Override
    public float getY() {
        return pos.y;
    }
}
