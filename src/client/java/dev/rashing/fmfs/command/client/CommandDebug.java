package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.rashing.fmfs.FlyMachineFallSaverClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandDebug implements Command<FabricClientCommandSource> {
    @Override
    public int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        return 1;
    }

    public static int runWithValue(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        String value = StringArgumentType.getString(commandContext, "value");
        assert value != null;
        Text message = Text.literal("Значение ").setStyle(Style.EMPTY.withColor(Formatting.RED))
                .append(Text.literal(value + " ").setStyle(Style.EMPTY.withColor(Formatting.GOLD)))
                .append(Text.literal("= ").setStyle(Style.EMPTY.withColor(Formatting.RED)));
        switch (value) {
            case "savedY" -> {
                if (FlyMachineFallSaverClient.savedY != null) message = message.copy().append(
                        Text.literal(FlyMachineFallSaverClient.savedY.toString()).setStyle(Style.EMPTY.withColor(Formatting.GOLD))
                );
                else message = message.copy().append(
                        Text.literal("null").setStyle(Style.EMPTY.withColor(Formatting.GOLD))
                );
            }
            default -> {
                message = Text.literal("Неизвестное значение: ").setStyle(Style.EMPTY.withColor(Formatting.RED))
                        .append(Text.literal(value).setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
            }
        }
        client.player.sendMessage(message, false);
        return 1;
    }

}
