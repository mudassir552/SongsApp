package com.example.demo.Controllers;

import com.example.demo.Requests.YouTubeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
public class YoutubeController{

    private static final Logger log = LoggerFactory.getLogger(YoutubeController.class);



    @Value("${youtube.api.key}")
    private String YOUTUBE_API_KEY;



    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/api/videos/trending")
    @ResponseBody
    public ResponseEntity<?> getTrendingVideos() {
        String video = "bollywood music";
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&q="
                + video + "&videoCategoryId=10&regionCode=IN&maxResults=3&key=" + YOUTUBE_API_KEY;
        log.info("hittting   youtube Api");

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
     log.info("respone"+response);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/api/youtube/videos/{searchVideo}")
    @ResponseBody
    public YouTubeResponse getYoutubeVideos(@PathVariable ("searchVideo") String video){

        String searchUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&q="
                + video + "&videoCategoryId=10&regionCode=IN&maxResults=6&key=" + YOUTUBE_API_KEY;

        YouTubeResponse response = restTemplate.getForObject(searchUrl, YouTubeResponse.class);
        return response;


    }


    @GetMapping("/youtube/trending")
    public String fetchTrendingYoutubeVideos(){

        return "youtube";

    }



}