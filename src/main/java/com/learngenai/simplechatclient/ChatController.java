package com.learngenai.simplechatclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/ask")
    public String askAI(@RequestParam String message){
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
