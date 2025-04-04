package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.rashing.fmfs.FlyMachineFallSaverClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandStart implements Command<FabricClientCommandSource> {
    @Override
    public int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        if (FlyMachineFallSaverClient.savedY != null) {
            Text message = Text.translatable("fmfs.message.error")
                        .append(": ")
                        .formatted(Formatting.RED)
                    .append(Text.translatable("fmfs.error.fly_already_started")
                            .append(". ")
                            .formatted(Formatting.GOLD))
                    .append(Text.translatable("fmfs.message.error_use")
                            .append(" ")
                            .formatted(Formatting.RED))
                    .append(Text.literal("/f_stop").formatted(Formatting.GOLD));
            client.player.sendMessage(message, true);
            return 1;
        }
        int playerY = (int) client.player.getY();
        FlyMachineFallSaverClient.savedY = playerY;

        FlyMachineFallSaverClient.LOGGER.info("Begin fly on level %s".formatted(playerY));

        Text message = Text.translatable("fmfs.message.fly_start")
                    .append(": ")
                    .formatted(Formatting.RED)
                .append(Text.literal("%s".formatted(playerY)).formatted(Formatting.GOLD));
        client.player.sendMessage(message, true);
//        client.inGameHud.setOverlayMessage(message, false);
        return 1;
    }

    public static Text getUsageHelp() {
        Text message = Text.translatable("fmfs.message.command_using")
                    .append(": ")
                    .formatted(Formatting.RED)
                .append(Text.literal("/f_start").formatted(Formatting.GOLD));
        return message;
    }
}
