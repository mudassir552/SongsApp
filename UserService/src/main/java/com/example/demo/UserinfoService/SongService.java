package com.example.demo.UserinfoService;

import com.example.demo.Controllers.UserController;
import com.example.demo.Requests.SongsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SongService {

    @Value("${songs.url}")
    private String SONGS_API_URL;

    @Autowired
    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(SongService.class);
    @Cacheable(value = "usersongs", key = "'songsofuser'")
    public List<SongsRequest> getSongs(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        long start = System.currentTimeMillis();
        ResponseEntity<List<SongsRequest>> response = restTemplate.exchange(
                SONGS_API_URL,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<SongsRequest>>() {}
        );
        log.info("res99"+response.getBody());
        long end = System.currentTimeMillis();
        System.out.println("Time taken to fetch songs: " + (end - start) + "ms");
        return response.getBody();
    }
}
