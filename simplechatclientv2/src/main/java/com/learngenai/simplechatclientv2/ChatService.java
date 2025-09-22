package com.learngenai.simplechatclientv2;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatClient chatClient;

    public String askAi(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();

    }
}
