package com.lexstack.lexbot.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("ChatParticipant")
@Data
public class ChatParticipant implements Serializable {

    // id format: server_identifier.chatroom_identifier.user_identifier
    private String id;
    private String userName;
    private String userDiscordId;
    private String lastMessage;

}
