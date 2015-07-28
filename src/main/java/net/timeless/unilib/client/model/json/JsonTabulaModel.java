package net.timeless.unilib.client.model.json;

import java.util.ArrayList;

/**
 * @author Gegy1000
 * TAKEN FROM LLIBRARY
 */
public class JsonTabulaModel
{
    private int textureWidth = 64;
    private int textureHeight = 32;

    private double[] scale = new double[] { 1d, 1d, 1d };

    private ArrayList<CubeGroup> cubeGroups;
    private ArrayList<CubeInfo> cubes;
    private ArrayList<Animation> anims;

    private int cubeCount;

    public int getTextureWidth()
    {
        return textureWidth;
    }

    public int getTextureHeight()
    {
        return textureHeight;
    }

    public double[] getScale()
    {
        return scale;
    }

    public ArrayList<CubeGroup> getCubeGroups()
    {
        return cubeGroups;
    }

    public ArrayList<CubeInfo> getCubes()
    {
        return cubes;
    }

    public ArrayList<Animation> getAnimations()
    {
        return anims;
    }

    public int getCubeCount()
    {
        return cubeCount;
    }
}