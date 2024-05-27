package com.telus.ai.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillAnalyzerController {
	
	@Value("classpath:/prompts/isBillHigherInd.st")
	private Resource isBillHigerPrompt;

	private final ChatClient chatClient;

	public BillAnalyzerController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	@GetMapping("/")
	Map<String, String> isBillHigher() {
		var system = new SystemMessage("Your primary function is is to analyze two TELUS bills and compare the total due amount.");
		var user = new UserMessage("Is the total due of {month1} higher than {month2}?");
		return new HashMap<>();
	}
}
