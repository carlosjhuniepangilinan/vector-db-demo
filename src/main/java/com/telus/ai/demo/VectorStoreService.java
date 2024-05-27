package com.telus.ai.demo;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class VectorStoreService {
	
	@Value("classpath:/bikes.json")
	private FileSystemResource sourceFile;

	private final VectorStore vectorStore;

	public VectorStoreService(VectorStore vectorStore) {
		this.vectorStore = vectorStore;
	}

	void load1() {
        JsonReader jsonReader = new JsonReader(sourceFile,
                "price", "name", "shortDescription", "description", "tags");
        List<Document> documents = jsonReader.get();
        this.vectorStore.add(documents);
	}
	
	void clean() {
		
		//vectorStore.delete(documents);
	}
	
	void load() {
		List <Document> documents = List.of(
			    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
			    new Document("The World is Big and Salvation Lurks Around the Corner"),
			    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")),
			    new Document("Exploring the depths of AI with Spring", Map.of("category", "AI")),
	            new Document("Navigating through large datasets efficiently"),
	            new Document("Harnessing the power of vector search", Map.of("importance", "high"))
				);

			// Add the documents to Redis
			vectorStore.add(documents);
	}
	
	List<Document> ask(String question) {
		return vectorStore.similaritySearch(
			SearchRequest
				.query(question)
				.withTopK(5));
	}
}
