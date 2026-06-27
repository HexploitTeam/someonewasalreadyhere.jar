package com.someonewasalreadyhere.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.registry.ModEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

/**
 * Generic humanoid renderer with variant inner classes for each entity archetype.
 * Each variant supplies its own texture path via getTextureLocation().
 */
public abstract class HumanoidEntityRenderer<T extends Mob> extends HumanoidMobRenderer<T, HumanoidModel<T>> {

    protected HumanoidEntityRenderer(EntityRendererProvider.Context ctx, float shadowRadius) {
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM)), shadowRadius);
        this.addLayer(new HumanoidArmorLayer<>(this,
            new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
            new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)),
            ctx.getModelSet()
        ));
    }

    // ─── Variant: basic horror entity ─────────────────────────────────────────

    public static class HorrorRenderer extends HumanoidEntityRenderer<Mob> {
        private final ResourceLocation texture;
        public HorrorRenderer(EntityRendererProvider.Context ctx) {
            super(ctx, 0.5f);
            // derive texture name from the context (use the observer texture as base, overridden per-entity)
            this.texture = new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/observer.png");
        }
        // Subclass instances below override this per-entity
        protected ResourceLocation resolveTexture(Mob entity) {
            String name = entity.getType().toShortString().replace("someonewasalreadyhere:", "");
            return new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/" + name + ".png");
        }
        @Override
        public ResourceLocation getTextureLocation(Mob entity) { return resolveTexture(entity); }
    }

    public static class CustodianRenderer extends HorrorRenderer {
        public CustodianRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
    }

    public static class PhantomRenderer extends HumanoidEntityRenderer<Mob> {
        public PhantomRenderer(EntityRendererProvider.Context ctx) { super(ctx, 0.0f); }
        @Override
        public ResourceLocation getTextureLocation(Mob entity) {
            String name = entity.getType().toShortString().replace("someonewasalreadyhere.", "");
            return new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/" + name + ".png");
        }
        @Override
        protected void scale(Mob entity, PoseStack pose, float partialTick) {
            pose.scale(1.0f, 1.0f, 1.0f);
        }
    }

    public static class ArchivistRenderer extends HumanoidEntityRenderer<Mob> {
        public ArchivistRenderer(EntityRendererProvider.Context ctx) { super(ctx, 0.6f); }
        @Override
        public ResourceLocation getTextureLocation(Mob entity) {
            return new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/archivist.png");
        }
        @Override
        protected void scale(Mob entity, PoseStack pose, float partialTick) {
            pose.scale(1.0f, 1.2f, 1.0f); // Taller
        }
    }

    public static class MirrorRenderer extends HumanoidEntityRenderer<Mob> {
        public MirrorRenderer(EntityRendererProvider.Context ctx) { super(ctx, 0.5f); }
        @Override
        public ResourceLocation getTextureLocation(Mob entity) {
            return new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/mirror_walker.png");
        }
    }

    public static class IncompleteRenderer extends HumanoidEntityRenderer<Mob> {
        public IncompleteRenderer(EntityRendererProvider.Context ctx) { super(ctx, 0.3f); }
        @Override
        public ResourceLocation getTextureLocation(Mob entity) {
            return new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/incomplete.png");
        }
        @Override
        protected void scale(Mob entity, PoseStack pose, float partialTick) {
            pose.scale(0.7f, 0.7f, 0.7f); // Smaller/incomplete
        }
    }

    public static class BossRenderer extends HumanoidEntityRenderer<Mob> {
        public BossRenderer(EntityRendererProvider.Context ctx) { super(ctx, 0.8f); }
        @Override
        public ResourceLocation getTextureLocation(Mob entity) {
            String name = entity.getType().toShortString().replace("someonewasalreadyhere.", "");
            return new ResourceLocation(SomeoneWasAlreadyHere.MODID, "textures/entity/" + name + ".png");
        }
        @Override
        protected void scale(Mob entity, PoseStack pose, float partialTick) {
            pose.scale(1.3f, 1.5f, 1.3f); // Boss scale
        }
    }
}
