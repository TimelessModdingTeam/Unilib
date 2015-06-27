package net.timeless.unilib.client.model.tabula;

import net.minecraft.entity.Entity;
import net.timeless.unilib.client.model.tabula.ModelJson;

/**
 * TAKEN FROM LLIBRARY
 *
 * Interface for animating Tabula models.
 * <p>
 * This can be used for Living animations.
 * 
 * @author gegy1000
 */
public interface IModelAnimator
{
    /**
     * Set the rotation angles for the shapes. Called every tick.
     * 
     * @see net.timeless.unilib.client.model.tabula.ModelJson
     */
    void setRotationAngles(ModelJson model, float limbSwing, float limbSwingAmount, float rotation, float rotationYaw, float rotationPitch, float partialTicks, Entity entity);
}
