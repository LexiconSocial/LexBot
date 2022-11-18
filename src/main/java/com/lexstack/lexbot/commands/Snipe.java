package com.lexstack.lexbot.commands;

import com.lexstack.lexbot.core.MessageEventCommand;
import com.lexstack.lexbot.model.ChatParticipant;
import com.lexstack.lexbot.repository.ChatParticipantRepository;
import com.lexstack.lexbot.util.SnipeUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class Snipe implements MessageEventCommand {

    private static final String COMMAND_NAME = "snipe";


    private final ChatParticipantRepository participantRepository;
    private final SnipeUtil snipeUtil;

    @Override
    public Mono<Void> handleMessage(MessageCreateEvent event) {
        log.info(COMMAND_NAME + " messageEvent handler invoked");
        Message message = event.getMessage();
        if (message.getContent().toLowerCase(Locale.ROOT).startsWith("!snipe")) {
            fetchFromCache(event);
        } else {
            persistToCache(message);
        }

        // Lurk in silence
        return Mono.empty();
    }

    private void persistToCache(Message message) {
        // Populate ChatParticipant Model object
        ChatParticipant chatParticipant = snipeUtil.creatChatParticipantFromEvent(message);
        // Persist to Redis
        participantRepository.save(chatParticipant);
    }

    public Mono<Void> fetchFromCache(MessageCreateEvent messageEvent) {
        Message message = messageEvent.getMessage();
        String targetId = snipeUtil.generateIdToFetchFromMessage(message);
        log.info("Target Id Determined: " + targetId);

        Optional<ChatParticipant> chatParticipant = participantRepository.findById(targetId);
        if (chatParticipant.isPresent()) {
            ChatParticipant resultParticipant = chatParticipant.get();
            String response = new StringBuilder()
                    .append("The Last Message sent by ")
                    .append("`")
                    .append(resultParticipant.getUserName())
                    .append("` was: \n")
                    .append("```")
                    .append(resultParticipant.getLastMessage())
                    .append("\n")
                    .append("```")
                    .toString();

            message.getChannel().block().createMessage(response).block();
        } else {
            log.info("Optional is empty.");
        }

        return Mono.empty();
    }

}
