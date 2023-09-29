package omnihilux;

import arc.*;
import arc.func.*;
import arc.struct.*;
import arc.util.*;
import omnihilux.EventType.*;

import static arc.Core.*;
import static omnihilux.Omnihilux.*;

public class Entities implements ApplicationListener, Eachable<Entity> {
    protected Seq<Entity> all = new Seq<>();

    public Entities() {
        Events.on(LoadEvent.class, e -> all.add(player = new Blob()));
    }

    @Override
    public void update() {
        Time.updateGlobal();
        Time.update();

        player.look.set(input.mouseWorld());
    }

    @Override
    public void each(Cons<? super Entity> cons) {
        all.each(cons);
    }
}
