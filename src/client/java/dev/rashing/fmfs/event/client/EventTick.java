package dev.rashing.fmfs.event.client;

import dev.rashing.fmfs.FlyMachineFallSaverClient;
import dev.rashing.fmfs.config.client.ConfigManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;

public class EventTick implements ClientTickEvents.EndTick {

    @Override
    public void onEndTick(MinecraftClient client) {
        if (FlyMachineFallSaverClient.savedY == null) {
            return;
        }
        assert client.player != null;
        try {
            client.player.getY();
        } catch (NullPointerException error) {
            return;
        }

        int playerY = (int) client.player.getY();
        if (playerY < FlyMachineFallSaverClient.savedY) {
            FlyMachineFallSaverClient.savedY = null;
            String action_type = ConfigManager.getConfig().action_type;
            switch (action_type) {
                case "leave" -> {

                    if (client.isInSingleplayer()) {
                        assert client.world != null;
                        client.world.disconnect();
                        Screen screen = new DisconnectedScreen(
                                new SelectWorldScreen(null),
                                Text.literal(""),
                                Text.literal("Вы упали с флаймашинки!").formatted(Formatting.RED),
                                Text.translatable("gui.back")
                        );
                        client.disconnect();
//                        client.setScreen(new SelectWorldScreen(null));
                        client.setScreen(screen);
                    } else {
                        Screen screen = new DisconnectedScreen(
                                new MultiplayerScreen(null),
                                Text.literal(""),
                                Text.literal("Вы упали с флаймашинки!").formatted(Formatting.RED)
                        );
                        client.disconnect(screen);
                    }

                }
                case "command" -> {
                    String command = ConfigManager.getConfig().command;
                    client.player.networkHandler.sendChatCommand(command);
                }
            }
        }
    }
}
