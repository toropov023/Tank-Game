package ca.toropov.games.tank.utils;

import ca.toropov.games.tank.TankGame;
import ca.toropov.games.tank.graphics.Sprite;
import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SpriteLoader {

    public static Sprite load(@NonNull String path) {
        BufferedImage bufferedImage = null;

        try {
            //Check if the image is in the jar resources
            URL url = TankGame.class.getClassLoader().getResource("/resources/" + path);

            if (url != null)
                bufferedImage = ImageIO.read(url);
            else
                bufferedImage = ImageIO.read(new File(path));

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            Image image = gc.createCompatibleImage(bufferedImage.getWidth(), bufferedImage.getHeight(), Transparency.BITMASK);

            image.getGraphics().drawImage(bufferedImage, 0, 0, null);

            return new Sprite(image);
        } catch (IOException e) {
            e.printStackTrace();
            TankGame.getInstance().getLogger().severe("Can't load image from " + path);
        }

        return null;
    }
}
