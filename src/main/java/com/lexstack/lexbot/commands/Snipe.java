package com.lexstack.lexbot.commands;

import com.lexstack.lexbot.core.MessageEventCommand;
import com.lexstack.lexbot.core.SlashCommand;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class Snipe implements SlashCommand, MessageEventCommand {

    private static final String COMMAND_NAME = "snipe";

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public Mono<Void> handleMessage(MessageCreateEvent event) {
        // populate cache
        log.info("INFORMATION:");
        log.info(event.getMessage().toString());
        log.info(event.getMessage().getContent());
        return Mono.empty();
    }

    @Override
    public Mono<Void> handleSlashCommand(ChatInputInteractionEvent event) {
        // fetch from cache
        return null;
    }
}
