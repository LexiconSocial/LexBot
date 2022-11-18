package com.lexstack.lexbot.commands;

import com.lexstack.lexbot.core.SlashCommand;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class Ping implements SlashCommand {

    private static final String COMMAND_NAME = "ping";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public Mono<Void> handleSlashCommand(ChatInputInteractionEvent event) {
        //We reply to the command with "Pong!" and make sure it is ephemeral (only the command user can see it)
        return event.reply()
                .withEphemeral(true)
                .withContent("Pong!");
    }
}