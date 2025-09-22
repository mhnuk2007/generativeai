package com.learngenai.simplechatclientv3;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {
    private OpenAiChatModel chatModel;
    private ChatClient chatClient;

    public ChatController(OpenAiChatModel chatModel){
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping("/ask/{message}")
    public String askAi(@PathVariable String message){
        return chatClient.prompt(message).call().content();
    }
}
