package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.rashing.fmfs.config.client.ConfigManager;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class CommandConfig implements Command<FabricClientCommandSource> {
    @Override
    public int run(CommandContext context) throws CommandSyntaxException {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;

        String arg_action = StringArgumentType.getString(context, "action");
        if (!(Objects.equals(arg_action, "leave") || Objects.equals(arg_action, "command"))) {
            client.player.sendMessage(getUsageHelp(), false);
            return 1;
        }

        String arg_command;
        try {
            arg_command = StringArgumentType.getString(context, "command");
        } catch (IllegalArgumentException e) {
            arg_command = "";
        }
        if (Objects.equals(arg_action, "command") && Objects.equals(arg_command, "")) {
            client.player.sendMessage(getUsageHelp(), false);
            return 1;
        }

        ConfigManager.getConfig().action_type = arg_action;
        ConfigManager.getConfig().command = arg_command;
        ConfigManager.saveConfig();

        Text message = Text.translatable("fmfs.messages.settings_updated").append(": ").formatted(Formatting.RED)
                .append(Text.literal("\n  – ")
                        .append(Text.translatable("fmfs.settings.on_fall_action"))
                        .append(": ")
                        .formatted(Formatting.GOLD))
                .append(Text.translatable(
                        Objects.equals(arg_action, "leave") ? "fmfs.settings.action_leave" : "fmfs.settings.action_command")
                        .formatted(Formatting.YELLOW));
        if (Objects.equals(arg_action, "command")) {
            message = message.copy()
                    .append(Text.literal("\n  – ")
                            .append(Text.translatable("fmfs.settings.on_fall_command"))
                            .append(": ")
                            .formatted(Formatting.GOLD))
                    .append(Text.literal("/" + arg_command).formatted(Formatting.YELLOW));
        }
        client.player.sendMessage(message, false);

        return 1;
    }

    public static Text getUsageHelp() {
        Text message = Text.translatable("fmfs.messages.command_using")
                    .append(": ")
                    .formatted(Formatting.RED)
                .append(Text.literal("/f_config leave ").formatted(Formatting.GOLD))
                .append(Text.translatable("fmfs.messages.or").append(" ").formatted(Formatting.RED))
                .append(Text.literal("/f_config command ").formatted(Formatting.GOLD))
                .append(Text.literal("<command>").formatted(Formatting.YELLOW));
        return message;
    }
}
