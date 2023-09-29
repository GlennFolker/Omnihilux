package omnihilux;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;

public class Blob extends Entity {
    public float health = 100f;

    public Color borderColor = new Color(0xedcb4fff);
    public Color eyeColor = new Color(0xe92f70ff);
    public Color cellColor = new Color(0xbd14c1ff);

    public Vec2 look = new Vec2();
    public float lookRange = 16f;

    @Override
    public void draw() {
        float r = radius();
        Draw.color(cellColor, cellColor.a * 0.15f);
        Fill.light(pos.x, pos.y, Lines.circleVertices(r), r, Tmp.c1.set(eyeColor).a(0f), Tmp.c2.set(cellColor).mulA(0.2f));

        for(int i = 0; i < 32; i++) {
            long baseId = id * 32 + i;
            float origin = i / 32f * 360f;
            float deviate = Mathf.randomSeed(baseId, 30f);
            float moveScl = Mathf.randomSeed(baseId + 2, 32f, 64f);
            float start = Mathf.randomSeed(baseId + 3, 0.25f, 0.4f) * r;
            float end = Mathf.randomSeed(baseId + 4, 0.6f, 1f) * r;
            float width = Mathf.randomSeed(baseId + 5, 0.05f, 0.08f) * r;
            float alpha = Mathf.randomSeed(baseId + 6, 0.1f, 0.7f);

            Tmp.v1.trns(origin + Mathf.sin(Time.time + i * moveScl, moveScl, deviate / 2f), start);
            Lines.stroke(width);
            Draw.color(cellColor, eyeColor, Interp.pow2In.apply(Mathf.curve(alpha, 0.1f, 0.7f)) * 0.4f);
            Draw.alpha(alpha);
            Lines.lineAngle(pos.x + Tmp.v1.x, pos.y + Tmp.v1.y, Tmp.v1.angle(), end - start);
        }

        for(int i = 0; i < 24; i++) {
            long baseId = id * 24 + 48 + i;
            float origin = Mathf.randomSeed(baseId, 360f);
            float deviate = Mathf.randomSeed(baseId + 1, 60f);
            float moveScl = Mathf.randomSeed(baseId + 2, 24f, 48f);
            float len = Mathf.randomSeed(baseId + 4, 0.2f, 0.5f) * r;
            float width = Mathf.randomSeed(baseId + 5, 0.1f, 0.18f) * r;
            float alpha = Mathf.randomSeed(baseId + 6, 0.3f, 0.6f);

            Tmp.v1.trns(origin + Mathf.sin(Time.time + i * moveScl, moveScl, deviate / 2f), r);
            Draw.color(borderColor, cellColor, 0.5f - Mathf.curve(alpha, 0.3f, 0.6f) * 0.5f);
            Draw.alpha(alpha);
            Draws.tri(pos.x + Tmp.v1.x, pos.y + Tmp.v1.y, width, len, Tmp.v1.angle() - 180f);
        }

        Lines.stroke(0.09f * r, borderColor);
        Lines.circle(pos.x, pos.y, r);

        float focus = lookRange * r;
        Tmp.v1.set(look).sub(pos).clampLength(0f, focus);
        float len = Interp.pow2Out.apply(Tmp.v1.len() / focus) * r * 3f / 4f;

        Tmp.v1.scl(1f / focus);
        float tiltLeft = Interp.smooth2.apply(
            Math.max(Tmp.v1.dot(Mathf.cosDeg(45f), Mathf.sinDeg(45f)), 0f) +
            Math.max(Tmp.v1.dot(Mathf.cosDeg(225f), Mathf.sinDeg(225f)), 0f)
        ) * 30f;
        float tiltRight = Interp.smooth2.apply(
            Math.max(Tmp.v1.dot(Mathf.cosDeg(135f), Mathf.sinDeg(135f)), 0f) +
            Math.max(Tmp.v1.dot(Mathf.cosDeg(-45f), Mathf.sinDeg(-45f)), 0f)
        ) * 30f;

        Draw.color(eyeColor, eyeColor.a * 0.8f);
        Tmp.v2.set(Tmp.v1).scl(Mathf.sqrt(len * len / Tmp.v1.len2()) * 0.75f);

        float tilt = tiltLeft - tiltRight;
        for(int sign : Mathf.signs) {
            Draws.tri(pos.x + Tmp.v2.x, pos.y + Tmp.v2.y, 0.3f * r, 0.6f * r, tilt + 90f * sign);
            Draws.tri(pos.x + Tmp.v2.x, pos.y + Tmp.v2.y, 0.6f * r, 0.25f * r, tilt + 90f + 90f * sign);
        }
    }

    public float radius() {
        return Mathf.sqrt(health * 15f / Mathf.pi);
    }
}
