package ca.toropov.games.tank.entities;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EntityRegister {
    private Set<Entity> entities = new HashSet<>();

    public void add(Entity entity) {
        entities.add(entity);
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public Set<Entity> get() {
        return Collections.unmodifiableSet(entities);
    }
}
