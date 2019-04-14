package com.twolazyguys.gamestates;

import com.twolazyguys.Main;
import net.colozz.engine2.MainClass;
import net.colozz.engine2.entities.ColoredQuad;
import net.colozz.engine2.entities.Entity;
import net.colozz.engine2.gamestates.GameState;
import net.colozz.engine2.util.Color;
import net.colozz.engine2.util.texture.SpriteSheet;
import net.colozz.engine2.util.ui.Text;
import net.colozz.engine2.util.vectors.Vector2f;

import java.util.Arrays;

public class Game extends GameState {

    private SpriteSheet spriteSheet = new SpriteSheet(MainClass.getTextureManager().getTexture("spritesheet"), 1, 2, Entity.TEXTURE);

    private ColoredQuad background, inputOutline;
    private ColoredQuad[] barriers;
    private Entity[] dwarfSprites;
    private Text input;

    private float count = 0;

    private final Color bright = new Color(92, 92, 48);
    private final Color dark = new Color(53, 53, 28);

    public Game() {
        background = new ColoredQuad(new Vector2f(0, 0), new Vector2f(Main.width, Main.height), bright);

        inputOutline = new ColoredQuad(new Vector2f(Main.width / 20, Main.width / 20), new Vector2f(Main.width * 0.9f, Main.width / 20), dark, false);

        barriers = new ColoredQuad[4];
        Color[] barriersColors = new Color[]{Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
        for (int i = 0; i < 4; i++)
            barriers[i] = new ColoredQuad(new Vector2f(Main.width / 20 + 10 * i, Main.height * 0.65f + 10 * i), new Vector2f(Main.height * 0.3f - 20 * i, Main.height * 0.3f - 20 * i), barriersColors[i]);

        dwarfSprites = new Entity[]{spriteSheet.getSprite(0, 0), spriteSheet.getSprite(0, 1)};
        dwarfSprites[1].setColor(Color.TRANSPARENT);
        for (Entity dwarfSprite : dwarfSprites) {
            dwarfSprite.modelMatrix.translate(new Vector2f(Main.width / 2, Main.height * 0.65f));
            dwarfSprite.modelMatrix.scale(new Vector2f(60, 60));
        }

        input = new Text(new Vector2f(Main.width / 20, Main.width / 20), "hello world", 24);
        input.setColor(dark);

        entities.add(background);
        entities.add(inputOutline);
        entities.addAll(Arrays.asList(barriers));
        entities.addAll(Arrays.asList(dwarfSprites));
        entities.add(input);
    }

    @Override
    public void update() {
        count += Main.delta;
        if (count > 0.75f) {
            count = 0;
            boolean b = dwarfSprites[0].getColor().a == 0.0f;
            dwarfSprites[0].setColor(b ? Color.WHITE : Color.TRANSPARENT);
            dwarfSprites[1].setColor(b ? Color.TRANSPARENT : Color.WHITE);
        }
    }

}
