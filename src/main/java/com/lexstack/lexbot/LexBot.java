package com.lexstack.lexbot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.intent.IntentSet;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class }) // TODO temporary exclusion
public class LexBot {

    @Value("${discord.api.key}")
    private String apiKey;

    public static void main(String[] args) {
        new SpringApplicationBuilder(LexBot.class)
                .build()
                .run(args);
        //SpringApplication.run(LexBot.class, args);
    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return DiscordClientBuilder.create("MTA0MjU4MjgzOTM4MTczNzYxNA.G-8luo.MxuaOuiOZ5Pwmkydm4JOeVC-4njdWJt-eAs9QY")
                .build()
                .gateway()
                .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.listening("to /commands")))
                //.setEnabledIntents(IntentSet.all())
                .login()
                .block();
    }

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }

}
