package com.example.demo;

import com.example.demo.Controllers.YoutubeController;
import com.example.demo.Requests.YouTubeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserServiceApplicationTests {



	private static final Logger log = LoggerFactory.getLogger(UserServiceApplicationTests.class);

	@Value("${youtube.api.key}")
	private String youtubeApiKey;

	private  TestRestTemplate TestRestTemplate = new TestRestTemplate();

	@Test
	void testYouTubeApiCall() {
		String video = "bollywood music";
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&q="
				+ video + "&videoCategoryId=10&regionCode=IN&maxResults=3&key=" + youtubeApiKey;

		ResponseEntity<YouTubeResponse> response = TestRestTemplate.getForEntity(url, YouTubeResponse.class);

		//Assertions.assertEquals(200, response.getStatusCodeValue());

		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getItems());
		assertFalse(response.getBody().getItems().isEmpty());



		System.out.println("Response body: " + response.getBody().getItems());

	}



}
