# LexBot

LEX's discord bot. Mostly hangs out in the Falcon Paladin discord.

## About
This bot was built using the [Spring Boot](https://spring.io/projects/spring-boot) framework and the [Discord4J](https://docs.discord4j.com)
Java discord client library.

## Getting Started

**Requirements**
* Redis
* Java
* Bot Token (Add value for `discord.api.key` in `application.properties`)

### Redis
The snipe command is utilizing Redis as a cache to store a users last sent message.

1. Install Docker
2. Run `docker run --name=redis-devel --publish=6379:6379 --hostname=redis --restart=on-failure --detach redis:latest` to get a redis container running with port 6379 exposed.

## Contributing

### Design Overview

Currently, LexBot only has two listeners which bind specific `GatewayDiscordClient` events to event handlers which will
distribute the event appropriately.

#### MessageEventListener

This listener will pass all `MessageCreateEvent` events to any autowired component that implements the `MessageEventCommand` interface.

**Note:** This listener _does not_ filter messages sent by the bot.

#### SlashCommandListener
The `SlashCommandListener` will pass any `ChatInputInteractionEvent` with an `Event.commandName` that matches the command's
name and implements the `SlashCommand` interface.

Slash commands will require additional json (Ping example in the `resources` dir) to configure the command. The `GlobalCommandRegistrar` 
will use the corresponding json to define a slash command in a given chat server at startup.

**Note:** The `GlobalCommandRegistrar` implements `ApplicationRunner` and the `run` method is only invoked once at startup. If slash commands
are created or modified, the bot will need to restart to re-register them.