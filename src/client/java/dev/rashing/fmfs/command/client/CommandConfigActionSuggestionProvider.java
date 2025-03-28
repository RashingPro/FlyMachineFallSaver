package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientCommandSource;

import java.util.concurrent.CompletableFuture;

public class CommandConfigActionSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> commandContext, SuggestionsBuilder builder) throws CommandSyntaxException {
        builder.suggest("leave");
        builder.suggest("command");

        return builder.buildFuture();
    }
}
