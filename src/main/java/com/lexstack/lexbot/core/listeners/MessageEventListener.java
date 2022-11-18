package com.lexstack.lexbot.core.listeners;

import com.lexstack.lexbot.core.MessageEventCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Component
public class MessageEventListener {

    private final Collection<MessageEventCommand> commands;

    public MessageEventListener(List<MessageEventCommand> commands, GatewayDiscordClient client) {
        this.commands = commands;

        client.on(MessageCreateEvent.class, this::handleMessageEvent).subscribe();
    }

    public Mono<Object> handleMessageEvent(MessageCreateEvent messageCreateEvent) {
        // Distribute event to all MessageEventCommands
        return Flux.fromIterable(commands)
                .next()
                // TODO Filter messages not sent  by self?
                .flatMap(command -> command.handleMessage(messageCreateEvent));
    }
}
