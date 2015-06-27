package net.timeless.unilib.client.model.tabula;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * TAKEN FROM LLIBRARY
 *
 * @author iChun
 */
public class CubeGroup
{
    public ArrayList<CubeInfo> cubes = Lists.newArrayList();
    public ArrayList<CubeGroup> cubeGroups = Lists.newArrayList();

    public String name;

    public boolean txMirror = false;

    public boolean hidden = false;

    public String identifier;
}
