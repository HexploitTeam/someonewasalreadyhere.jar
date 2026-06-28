package com.someonewasalreadyhere.client.renderer;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.registry.ModEntities;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders ArchiveHound using the Wolf model with a custom texture.
 */
@SuppressWarnings("unchecked")
public class QuadrupedEntityRenderer extends MobRenderer<ModEntities.ArchiveHoundEntity, WolfModel<ModEntities.ArchiveHoundEntity>> {

    private static final ResourceLocation TEXTURE =
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/archive_hound.png");

    public QuadrupedEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WolfModel(ctx.bakeLayer(ModelLayers.WOLF)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(ModEntities.ArchiveHoundEntity entity) {
        return TEXTURE;
    }
}
