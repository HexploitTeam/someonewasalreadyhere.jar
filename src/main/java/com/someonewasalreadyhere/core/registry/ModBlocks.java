package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, SomeoneWasAlreadyHere.MODID);

    // ─── Blocks ────────────────────────────────────────────────────────────────

    public static final RegistryObject<Block> CORRUPTED_STONE = BLOCKS.register("corrupted_stone",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(3.0f, 6.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ARCHIVE_BRICKS = BLOCKS.register("archive_bricks",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).strength(2.5f, 6.0f)));

    public static final RegistryObject<Block> QUIET_GRASS_BLOCK = BLOCKS.register("quiet_grass_block",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(0.6f)));

    public static final RegistryObject<Block> DISTORTED_ORE = BLOCKS.register("distorted_ore",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).strength(3.0f, 3.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> REINFORCED_ARCHIVE = BLOCKS.register("reinforced_archive",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).strength(50.0f, 1200.0f)));

    public static final RegistryObject<Block> STATIC_STONE = BLOCKS.register("static_stone",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f, 6.0f)));

    // ─── Block Items ──────────────────────────────────────────────────────────

    public static void registerBlockItems() {
        ModItems.ITEMS.register("corrupted_stone",   () -> new BlockItem(CORRUPTED_STONE.get(),   new Item.Properties()));
        ModItems.ITEMS.register("archive_bricks",    () -> new BlockItem(ARCHIVE_BRICKS.get(),    new Item.Properties()));
        ModItems.ITEMS.register("quiet_grass_block", () -> new BlockItem(QUIET_GRASS_BLOCK.get(), new Item.Properties()));
        ModItems.ITEMS.register("distorted_ore",     () -> new BlockItem(DISTORTED_ORE.get(),     new Item.Properties()));
        ModItems.ITEMS.register("reinforced_archive",() -> new BlockItem(REINFORCED_ARCHIVE.get(),new Item.Properties()));
        ModItems.ITEMS.register("static_stone",      () -> new BlockItem(STATIC_STONE.get(),      new Item.Properties()));
    }
}
