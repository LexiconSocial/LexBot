package com.lexstack.lexbot.repository;

import com.lexstack.lexbot.model.ChatParticipant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatParticipantRepository extends CrudRepository<ChatParticipant, String> {

}
