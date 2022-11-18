package com.lexstack.lexbot.util;

import com.lexstack.lexbot.model.ChatParticipant;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.MessageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class SnipeUtil {

    private static final String ID_DELIMETER = ".";

    public String generateIdFromMessageData(MessageData messageData) {
        return new StringBuilder()
                .append(messageData.guildId().get())
                .append(ID_DELIMETER)
                .append(messageData.channelId())
                .append(ID_DELIMETER)
                .append(messageData.author().id())
                .toString();
    }

    public String generateIdToFetchFromMessage(Message message) {
        MessageData messageData = message.getData();
        String targetId = extractTargetUserId(message);
        return new StringBuilder()
                .append(messageData.guildId().get())
                .append(ID_DELIMETER)
                .append(messageData.channelId())
                .append(ID_DELIMETER)
                .append(targetId)
                .toString();

    }

    public ChatParticipant creatChatParticipantFromEvent(Message eventMessage) {
        MessageData messageData = eventMessage.getData();
        String generatedId = generateIdFromMessageData(messageData);
        log.info("Generated ID: " + generatedId);

        // TODO Figure out issues with lombok builder
        ChatParticipant chatParticipant = new ChatParticipant();
        chatParticipant.setLastMessage(eventMessage.getContent());
        chatParticipant.setUserName(messageData.author().username());
        chatParticipant.setUserDiscordId(messageData.author().id().toString());
        chatParticipant.setId(generatedId);

        return chatParticipant;
    }

    public String extractTargetUserId(Message message) {
        String content = message.getContent();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        String result = "";
        if (matcher.find()) {
            result = matcher.group(0);
        }

        return result;
    }

}
