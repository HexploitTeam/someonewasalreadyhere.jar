package com.someonewasalreadyhere.core.event;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.registry.ModEntities;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SomeoneWasAlreadyHere.MODID)
public class EntityAttributes {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {

        event.put(ModEntities.OBSERVER.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .build());

        event.put(ModEntities.ECHO_HUNTER.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .build());

        event.put(ModEntities.CARETAKER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .build());

        event.put(ModEntities.ARCHIVE_HOUND.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.38)
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .build());

        event.put(ModEntities.PHANTOM_PLAYER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26)
                .add(Attributes.FOLLOW_RANGE, 128.0)
                .build());

        event.put(ModEntities.ARCHIVIST.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.FOLLOW_RANGE, 80.0)
                .add(Attributes.ATTACK_DAMAGE, 12.0)
                .build());

        event.put(ModEntities.HOLLOW_MIMIC.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .build());

        event.put(ModEntities.WINDOW_WATCHER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.10)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .build());

        event.put(ModEntities.SURVEYOR.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .build());

        event.put(ModEntities.STATIC_SHEPHERD.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0)
                .add(Attributes.MOVEMENT_SPEED, 0.16)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .build());

        event.put(ModEntities.SLEEPER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.05)
                .add(Attributes.FOLLOW_RANGE, 8.0)
                .build());

        event.put(ModEntities.TUNNEL_LISTENER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .build());

        event.put(ModEntities.MAINTENANCE_DRONE.get(),
            net.minecraft.world.entity.FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .build());

        event.put(ModEntities.INCOMPLETE.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.12)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .build());

        event.put(ModEntities.FORGOTTEN_MINER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.21)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .build());

        event.put(ModEntities.MIRROR_WALKER.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .build());

        event.put(ModEntities.QUIET_HOST.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 55.0)
                .add(Attributes.MOVEMENT_SPEED, 0.14)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .build());

        event.put(ModEntities.OBSERVER_PRIME.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.FOLLOW_RANGE, 128.0)
                .add(Attributes.ATTACK_DAMAGE, 18.0)
                .build());

        event.put(ModEntities.RESIDUAL_ECHO.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .build());

        event.put(ModEntities.CARTOGRAPHER.get(),
            net.minecraft.world.entity.PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .build());

        event.put(ModEntities.CURATOR.get(),
            net.minecraft.world.entity.monster.Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 120.0)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .build());
    }
}
