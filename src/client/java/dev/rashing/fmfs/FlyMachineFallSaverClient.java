package dev.rashing.fmfs;

import com.mojang.brigadier.arguments.StringArgumentType;

import dev.rashing.fmfs.command.client.*;
import dev.rashing.fmfs.config.client.ConfigManager;

import dev.rashing.fmfs.event.client.EventTick;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyMachineFallSaverClient implements ClientModInitializer {
    public static final String MOD_ID = "fmfs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Integer savedY = null;
//    public static AtomicReference<Integer> savedY = new AtomicReference<>(null);

    @Override
    public void onInitializeClient() {
        ConfigManager.loadConfig();

        // commands registration
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("f_config")
                        .then(ClientCommandManager.argument("action", StringArgumentType.word())
                                .suggests(new CommandConfigActionSuggestionProvider())
                                .executes(new CommandConfig())
                                .then(ClientCommandManager.argument("command", StringArgumentType.string())
                                        .executes(new CommandConfig())
                                )
                        )
                        .executes(commandContext -> {
                            commandContext.getSource().sendFeedback(CommandConfig.getUsageHelp());
                            return 1;
                        })
                ));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("f_start")
                        .executes(new CommandStart())));
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("f_debug")
                        .then(ClientCommandManager.argument("value", StringArgumentType.word())
                                .suggests(new CommandDebugValueSuggestionProvider())
                                .executes(CommandDebug::runWithValue)
                        )
                        .executes(new CommandDebug())
                ));

        // events registration
        ClientTickEvents.END_CLIENT_TICK.register(new EventTick());
//        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> savedY = null);
//        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> savedY = null);
    }
}
