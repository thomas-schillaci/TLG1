package com.twolazyguys.entities;

import com.twolazyguys.Main;
import com.twolazyguys.events.SpriteChangedEvent;
import com.twolazyguys.gamestates.Game;
import com.twolazyguys.sprites.Sprite;
import net.colozz.engine2.entities.Entity;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;
import net.colozz.engine2.util.vectors.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Colormap extends Entity implements Listener {

    private int sizeX, sizeY;

    private ArrayList<Sprite> sprites;

    private int intensityLocation, brightLocation, darkLocation;

    public Colormap(int sizeX, int sizeY) {
        super(2, genData(sizeX, sizeY), genIndices(sizeX, sizeY), GL_TRIANGLES);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        sprites = new ArrayList<>();
        intensityLocation = glGetAttribLocation(Main.shaderPrograms[2], "a_intensity");
        brightLocation = glGetUniformLocation(Main.shaderPrograms[2], "u_bright");
        darkLocation = glGetUniformLocation(Main.shaderPrograms[2], "u_dark");
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        enableDraw();
        glEnableVertexAttribArray(intensityLocation);

        glVertexPointer(2, GL_FLOAT, 2 * 4 + 1 * 4, 0);
        glVertexAttribPointer(intensityLocation, 1, GL_FLOAT, false, 2 * 4 + 1 * 4, 2 * 4);
        glColorPointer(1, GL_FLOAT, 2 * 4 + 1 * 4, 2 * 4);
        glUniform3f(brightLocation, Game.BRIGHT.r, Game.BRIGHT.g, Game.BRIGHT.b);
        glUniform3f(darkLocation, Game.DARK.r, Game.DARK.g, Game.DARK.b);
        passMainBuffers(viewMatrix, projectionMatrix);

        glDrawElements(drawingMode, indicesCount, GL_UNSIGNED_INT, 0);

        disableDraw();
        glDisableVertexAttribArray(intensityLocation);
    }

    @EventHandler
    public void onSpriteChangedEvent(SpriteChangedEvent event) {
        if (sprites.contains(event.getSprite())) updateData();
    }

    private void updateData() {
        float[][] colors = new float[sizeX][sizeY];

        for (Sprite sprite : sprites) Sprite.storeColors(sprite, colors);

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
        if (sprites.size() == 0) return;
        sprites.clear();
        updateData();
    }

    private static float[] genData(int sizeX, int sizeY) {
        return genData(sizeX, sizeY, new float[sizeX][sizeY]);
    }

    private static float[] genData(int sizeX, int sizeY, float[][] colors) {
        float dr = (float) Main.width / sizeX;
        int vertices = 4 * sizeX * sizeY;

        float[] data = new float[vertices * (2 + 1)];

        int index = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                data[index++] = i * dr;
                data[index++] = j * dr;
                data[index++] = colors[i][j];
                data[index++] = (i + 1) * dr;
                data[index++] = j * dr;
                data[index++] = colors[i][j];
                data[index++] = i * dr;
                data[index++] = (j + 1) * dr;
                data[index++] = colors[i][j];
                data[index++] = (i + 1) * dr;
                data[index++] = (j + 1) * dr;
                data[index++] = colors[i][j];
            }
        }

        return data;
    }

    private static int[] genIndices(int sizeX, int sizeY) {
        int[] indices = new int[6 * sizeX * sizeY];

        int index = 0;
        int value = 0;
        for (int k = 0; k < sizeX * sizeY; k++) {
            indices[index++] = value;
            indices[index++] = value + 1;
            indices[index++] = value + 2;
            indices[index++] = value + 1;
            indices[index++] = value + 3;
            indices[index++] = value + 2;
            value += 4;
        }

        return indices;
    }

}
