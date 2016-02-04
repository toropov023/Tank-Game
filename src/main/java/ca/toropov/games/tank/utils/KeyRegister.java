package ca.toropov.games.tank.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Author: toropov
 * Date: 2016-02-03
 */
public class KeyRegister implements KeyListener {
    private final Set<Key> pressed = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(new Key(e.getKeyCode(), e.getKeyLocation()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Key key = find(e.getKeyCode(), e.getKeyLocation());
        if (key != null)
            pressed.remove(key);
    }

    public boolean isPressed(int code, int location) {
        return find(code, location) != null;
    }

    public boolean isPressed(int code) {
        return isPressed(code, -1);
    }

    private Key find(int code, int location) {
        Optional<Key> optional = pressed.stream().filter(k -> k.code == code && (location == -1 || k.location == location)).findAny();
        if (optional.isPresent())
            return optional.get();

        return null;
    }

    @Data
    @AllArgsConstructor
    private class Key {
        int code;
        int location;
    }
}
