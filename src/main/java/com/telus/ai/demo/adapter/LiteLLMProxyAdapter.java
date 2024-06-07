package com.telus.ai.demo.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LiteLLMProxyAdapter {
	
	@Value("classpath:/prompts/isBillHigherInd.st")
	private Resource isBillHigerPrompt;

	ChatClient chatClient;

	public LiteLLMProxyAdapter(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	public String isBillHigher(String accountNum, String bills, List<String> billMonths) {
		//MapOutputParser outputParser = new MapOutputParser();
		//String format = outputParser.getFormat();
		
		PromptTemplate promptTemplate = new PromptTemplate(isBillHigerPrompt);
		Map<String, Object> variables = new HashMap<>();
		variables.put("documents", bills);
		variables.put("month1", billMonths.get(1));
		variables.put("month0", billMonths.get(0));
		variables.put("accountNum", accountNum);
		
		Prompt prompt = promptTemplate.create(variables);
		Generation generation = chatClient.call(prompt).getResult();
		
		String response = generation.getOutput().getContent();
		log.info("Prompt: " + prompt);
		
		log.info("Response: " + response);
		return response;
	}
}