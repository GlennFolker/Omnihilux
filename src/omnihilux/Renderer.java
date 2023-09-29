package omnihilux;

import arc.*;
import arc.assets.loaders.TextureLoader.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;

import static arc.Core.*;
import static omnihilux.Omnihilux.*;

public class Renderer implements ApplicationListener {
    public Texture backgroundBack, backgroundFront;
    public Shader background;
    public ScreenQuad quad;

    private static final float scale = 1f;

    @Override
    public void init() {
        Gl.enable(Gl.blend);

        assets.load("backgrounds/back.png", Texture.class, new TextureParameter() {{
            minFilter = magFilter = TextureFilter.linear;
            wrapU = wrapV = TextureWrap.repeat;
        }}).loaded = t -> backgroundBack = t;

        assets.load("backgrounds/front.png", Texture.class, new TextureParameter() {{
            minFilter = magFilter = TextureFilter.linear;
            wrapU = wrapV = TextureWrap.repeat;
        }}).loaded = t -> backgroundFront = t;

        assets.load("shaders/parallax.vert", Shader.class).loaded = s -> background = s;
        quad = new ScreenQuad();
    }

    @Override
    public void dispose() {
        backgroundBack.dispose();
        backgroundFront.dispose();
        background.dispose();
        quad.dispose();
    }

    @Override
    public void update() {
        int sw = graphics.getWidth(), sh = graphics.getHeight();
        float scl = Math.min(sw / 640f * scale, sh / 400f * scale);

        graphics.clear(Color.clear);
        camera.position.set(player);
        camera.width = sw / scl;
        camera.height = sh / scl;
        camera.update();
        Draw.proj(camera);

        background.bind();
        background.setUniformf("u_resolution", camera.width, camera.height);
        background.setUniformf("u_position", camera.position);

        backgroundFront.bind(1);
        background.setUniformi("u_texture1", 1);
        background.setUniformf("u_dimension1", backgroundFront.width, backgroundFront.height);
        background.setUniformf("u_intensity1", 0.27f);

        backgroundBack.bind(0);
        background.setUniformi("u_texture2", 0);
        background.setUniformf("u_dimension2", backgroundBack.width, backgroundBack.height);
        background.setUniformf("u_intensity2", 0.1f);

        quad.render(background);
        entities.each(Entity::draw);

        Draw.flush();
        Draw.reset();
    }
}
