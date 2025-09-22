# Spring AI Generative Projects

This repository contains multiple Spring AI projects demonstrating different architectural approaches for integrating OpenAI Chat models with Spring Boot.  

We explore three versions of a simple chat client:

1. **Project 1** – Direct Controller Injection  
2. **Project 2** – Service Layer Architecture  
3. **Project 3** – All-in-One Controller (Quick Prototype)

---

## Project Structure Comparison

| Aspect | Project 1 | Project 2 | Project 3 |
|--------|-----------|-----------|-----------|
| **Classes** | 2 classes (`AiConfig`, `ChatController`) | 3 classes (`AiConfig`, `ChatController`, `ChatService`) | 1 class (`ChatController`) |
| **Architecture** | Direct injection | Service layer | All-in-one |
| **Dependency Injection** | Constructor injection | `@Autowired` fields (recommended: constructor) | Constructor injection inside controller |
| **ChatClient Config** | Built in `@Configuration` | Uses Spring auto-configured `ChatClient.Builder` | Built directly in controller constructor |
| **Business Logic** | In controller | In service | In controller |
| **URL Pattern** | Query param: `/api/ask?message=hello` | Query param: `/api/ask?message=hello` | Path variable: `/api/ask/hello` |
| **Testability** | Moderate | High | Low |
| **Scalability** | Low | High | Very low |

---

## Code Differences

### Project 1 – Direct Injection

```java
@Configuration
public class AiConfig {
    private final OpenAiChatModel chatModel;

    public AiConfig(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(chatModel).build();
    }
}
```

- Controller directly uses `ChatClient`.
- Business logic is mixed in the controller.

---

### Project 2 – Service Layer

```java
@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String askAi(String message) {
        return chatClient.prompt().user(message).call().content();
    }
}
```

- Controller only handles HTTP requests.
- Business logic encapsulated in service.
- Scales well and is easier to test.

---

### Project 3 – All-in-One Controller

```java
@RestController
@RequestMapping("/api")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping("/ask/{message}")
    public String askAi(@PathVariable String message) {
        return chatClient.prompt(message).call().content();
    }
}
```

- Single class, minimal setup.
- Best for demos and rapid prototyping.
- Harder to test, and URL encoding can be an issue.

---

## Pros & Cons

### Project 1
**Pros:**
- Simple structure  
- Constructor injection ensures explicit dependencies  

**Cons:**
- Business logic in controller  
- Harder to scale  

### Project 2
**Pros:**
- Service layer separation  
- Easier testing and maintenance  
- Better for multiple endpoints  

**Cons:**
- Slightly more boilerplate  
- Field injection should be replaced with constructor injection  

### Project 3
**Pros:**
- Minimal setup, fast prototyping  
- RESTful path-based URLs  

**Cons:**
- Business logic mixed with web layer  
- Difficult to unit test  
- Not reusable  

---

## Recommendations

- **Quick prototypes or demos** → Project 3  
- **Small apps with simple AI endpoints** → Project 1  
- **Production apps with multiple endpoints and complex logic** → Project 2 (with constructor injection)  

---

## Notes

- All projects require Spring Boot 3.x and Spring AI dependency.
- URL patterns:  
  - Project 1 & 2: `GET /api/ask?message=hello`  
  - Project 3: `GET /api/ask/hello` (path variable)
- Consider using `POST` requests for Project 3 if message content contains spaces or special characters.

---

## License

MIT License

