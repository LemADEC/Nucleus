/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.kit.commands.kit;

import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.api.nucleusdata.Kit;
import io.github.nucleuspowered.nucleus.internal.annotations.RunAsync;
import io.github.nucleuspowered.nucleus.internal.annotations.command.NoModifiers;
import io.github.nucleuspowered.nucleus.internal.annotations.command.Permissions;
import io.github.nucleuspowered.nucleus.internal.annotations.command.RegisterCommand;
import io.github.nucleuspowered.nucleus.internal.command.NucleusParameters;
import io.github.nucleuspowered.nucleus.internal.permissions.SuggestedLevel;
import io.github.nucleuspowered.nucleus.modules.kit.commands.KitFallbackBase;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.annotation.NonnullByDefault;

@Permissions(prefix = "kit", suggestedLevel = SuggestedLevel.ADMIN)
@RegisterCommand(value = {"toggleredeemmessage", "togglemessage"}, subcommandOf = KitCommand.class)
@RunAsync
@NoModifiers
@NonnullByDefault
public class KitRedeemMessageCommand extends KitFallbackBase<CommandSource> {

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                KitFallbackBase.KIT_PARAMETER_NO_PERM_CHECK,
                NucleusParameters.ONE_TRUE_FALSE
        };
    }

    @Override
    public CommandResult executeCommand(final CommandSource player, CommandContext args, Cause cause) {
        Kit kitInfo = args.<Kit>getOne(KIT_PARAMETER_KEY).get();
        boolean b = args.<Boolean>getOne(NucleusParameters.Keys.BOOL).get();

        // This Kit is a reference back to the version in list, so we don't need
        // to update it explicitly
        kitInfo.setDisplayMessageOnRedeem(b);
        KIT_HANDLER.saveKit(kitInfo);
        player.sendMessage(Nucleus.getNucleus().getMessageProvider()
                .getTextMessageWithFormat(b ? "command.kit.displaymessage.on" : "command.kit.displaymessage.off", kitInfo.getName()));

        return CommandResult.success();
    }
}
