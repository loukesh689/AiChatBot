package com.mypack.Controller;

import com.mypack.service.OllamaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {

    private final com.mypack.service.OllamaService ollamaService;

    public OllamaController(com.mypack.service.OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }


    @GetMapping("/api/chat")
    public String chat(@RequestParam(defaultValue = "Hello") String prompt) {
        return ollamaService.askOllama(prompt);
    }
}