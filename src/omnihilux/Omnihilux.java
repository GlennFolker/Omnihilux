package omnihilux;

import arc.*;
import arc.assets.*;
import arc.backend.sdl.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import omnihilux.EventType.*;

import static arc.Core.*;

public class Omnihilux extends ApplicationCore {
    public static Entities entities;
    public static Renderer renderer;
    public static Blob player;

    private static boolean done = false;

    public static void main(String[] args) {
        new SdlApplication(new Omnihilux(), new SdlConfig() {{
            width = 1920;
            height = 1080;
            samples = 12;
            maximized = true;
            fullscreen = true;

            title = "Omnihilux";
            initialBackgroundColor = Color.clear;
        }});
    }

    @Override
    public void setup() {
        camera = new Camera();
        batch = new SortedSpriteBatch();
        input.addProcessor(scene = new Scene());
        assets = new AssetManager();
        atlas = TextureAtlas.blankAtlas();

        add(entities = new Entities());
        add(renderer = new Renderer());

        Lines.setCirclePrecision(1f);
    }

    @Override
    public void update() {
        if(done) {
            super.update();
            scene.act();
        } else if(assets.update()) {
            done = true;
            Events.fire(new LoadEvent());
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        scene.resize(width, height);
    }
}
