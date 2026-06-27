package com.someonewasalreadyhere.client.renderer;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

/**
 * Renders MaintenanceDrone using a small cube model (repurposed Slime outer model)
 * with a custom dark-metallic texture.
 */
public class DroneEntityRenderer extends MobRenderer<Mob, SlimeModel<Mob>> {

    private static final ResourceLocation TEXTURE =
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/maintenance_drone.png");

    public DroneEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SlimeModel<>(ctx.bakeLayer(ModelLayers.SLIME)), 0.25f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob entity) {
        return TEXTURE;
    }
}
