package com.someonewasalreadyhere.client.renderer;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

/**
 * Renders ArchiveHound using the Wolf model with a custom texture.
 */
public class QuadrupedEntityRenderer extends MobRenderer<Mob, WolfModel<Mob>> {

    private static final ResourceLocation TEXTURE =
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/archive_hound.png");

    public QuadrupedEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WolfModel<>(ctx.bakeLayer(ModelLayers.WOLF)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob entity) {
        return TEXTURE;
    }
}
