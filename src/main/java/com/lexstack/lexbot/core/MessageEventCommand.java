package com.lexstack.lexbot.core;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface MessageEventCommand {

    Mono<Void> handleMessage(MessageCreateEvent event);
}

