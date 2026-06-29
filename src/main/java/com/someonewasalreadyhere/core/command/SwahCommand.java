package com.someonewasalreadyhere.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class SwahCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("swah")
                .then(Commands.literal("test")
                    .then(Commands.literal("all")
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> {
                            runTestAll(ctx.getSource());
                            return 1;
                        })
                    )
                )
        );
    }

    private static void runTestAll(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        String[] systems = {
            "Awareness       — global 0-1000 horror counter & decay",
            "CLM Chat        — intent classifier & in-character responses",
            "Custodian       — world modification near players",
            "Echo            — player action recorder & EchoHunter spawner",
            "Silence         — chunk-level silence field & audio muffle",
            "Events          — 26 scheduled awareness-driven events",
            "Phantom         — fog + render-distance reducer",
            "Loot Conditions — awareness-gated loot drops",
            "Network         — client/server packet channel",
        };

        send(source, "§8§l[SWAH] §7Running system call test...");
        send(source, "§8────────────────────────────────");

        for (String system : systems) {
            send(source, "§a[CALL] §f" + system);
        }

        send(source, "§8────────────────────────────────");
        send(source, "§a§l[SWAH] §7All systems pinged. No actual execution performed.");

        SomeoneWasAlreadyHere.LOGGER.info("[SWAH /test all] System call test triggered by {}",
            player != null ? player.getName().getString() : "server");
    }

    private static void send(CommandSourceStack source, String text) {
        source.sendSuccess(() -> Component.literal(text), false);
    }
}
