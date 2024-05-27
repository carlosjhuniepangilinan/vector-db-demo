package com.telus.ai.demo;

import java.util.List;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AiController {
	
	private final ChatClient chatClient;
	private final VectorStoreService vectorStoreService;
	
	public AiController(ChatClient chatClient,
			VectorStoreService vectorStore
			) {
		this.chatClient = chatClient;
		this.vectorStoreService = vectorStore;
	}
	
	@GetMapping("/")
	String hello() {
		return "Hello";
	}
	
	@GetMapping("/simple")
	public String simple() {
		return chatClient.call(
				new Prompt("How long has The Java Programming language been arount?"))
				.getResult()
				.getOutput()
				.getContent();
	}
	
	@GetMapping("/load")
	String load() {
		vectorStoreService.load();
		return "Data loaded";
	}
	
	@GetMapping("/ask")
	List<Document> ask(@RequestParam("q") String question) {
		return vectorStoreService.ask(question);
	}
}
