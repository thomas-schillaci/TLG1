package com.twolazyguys.entities;

import com.twolazyguys.Main;
import com.twolazyguys.events.SpriteChangedEvent;
import com.twolazyguys.util.Sprite;
import net.colozz.engine2.entities.Entity;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.util.Color;
import net.colozz.engine2.util.vectors.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Colormap extends Entity implements Listener {

    private int sizeX, sizeY;
    private Color bright;

    private ArrayList<Sprite> sprites;

    public Colormap(int sizeX, int sizeY, Color bright) {
        super(2, genData(sizeX, sizeY, bright), genIndices(sizeX, sizeY), GL_TRIANGLES);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.bright = bright;
        sprites = new ArrayList<>();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        enableDraw();
        glEnableClientState(GL_COLOR_ARRAY);

        glVertexPointer(2, GL_FLOAT, 2 * 4 + 4 * 4, 0);
        glColorPointer(4, GL_FLOAT, 2 * 4 + 4 * 4, 2 * 4);
        passMainBuffers(viewMatrix, projectionMatrix);

        glDrawElements(drawingMode, indicesCount, GL_UNSIGNED_SHORT, 0);

        disableDraw();
        glDisableClientState(GL_COLOR_ARRAY);
    }

    @EventHandler
    public void onSpriteChangedEvent(SpriteChangedEvent event) {
        if (sprites.contains(event.getSprite())) updateData();
    }

    private void updateData() {
        Color[][] colors = new Color[sizeX][sizeY];
        for (int i = 0; i < colors.length; i++) for (int j = 0; j < colors[0].length; j++) colors[i][j] = bright;

        for (Sprite sprite : sprites) {
            Color[][] spriteColors = sprite.getColors();
            for (int i = 0; i < spriteColors.length; i++)
                for (int j = 0; j < spriteColors[0].length; j++)
                    colors[sprite.getX() + i][sprite.getY() + j] = spriteColors[i][j];
        }

        float[] data = genData(sizeX, sizeY, colors);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, ids[0]);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        updateData();
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        updateData();
    }

    public void clearSprites() {
        sprites.clear();
        updateData();
    }

    private static float[] genData(int sizeX, int sizeY, Color bright) {
        Color[][] colors = new Color[sizeX][sizeY];
        for (int i = 0; i < colors.length; i++) for (int j = 0; j < colors[0].length; j++) colors[i][j] = bright;
        return genData(sizeX, sizeY, colors);
    }

    private static float[] genData(int sizeX, int sizeY, Color[][] colors) {
        int dr = Main.width / sizeX;
        int vertices = (sizeX - 1) * (sizeY - 1) + 2 * (sizeX + sizeY);

        float[] data = new float[vertices * (2 + 4)];

        int index = 0;
        for (int i = 0; i <= sizeX; i++) {
            for (int j = 0; j <= sizeY; j++) {
                data[index++] = i * dr;
                data[index++] = j * dr;
                Color color = colors[i - (i == sizeX ? 1 : 0)][j - (j == sizeY ? 1 : 0)];
                data[index++] = color.r;
                data[index++] = color.g;
                data[index++] = color.b;
                data[index++] = color.a;
            }
        }

        return data;
    }

    private static short[] genIndices(int sizeX, int sizeY) {
        short[] indices = new short[6 * sizeX * sizeY];

        int index = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                indices[index++] = (short) (i * (sizeY + 1) + j);
                indices[index++] = (short) (i * (sizeY + 1) + j + 1);
                indices[index++] = (short) ((i + 1) * (sizeY + 1) + j);
                indices[index++] = (short) (i * (sizeY + 1) + j + 1);
                indices[index++] = (short) ((i + 1) * (sizeY + 1) + j + 1);
                indices[index++] = (short) ((i + 1) * (sizeY + 1) + j);
            }
        }

        return indices;
    }

}
