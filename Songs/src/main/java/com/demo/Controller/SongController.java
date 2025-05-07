package com.demo.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.demo.SongsRepo.SongsRepo;
import com.demo.SongserviceImpl.*;
import com.demo.songs.Songs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;


@Controller
@CrossOrigin(origins="http://localhost:8081")
public class SongController  {
      
	/*
	 * private final RestTemplate restTemplate;
	 * 
	 * @Autowired public SongController (RestTemplate restTemplate) {
	 * this.restTemplate = restTemplate; }
	 */
@Autowired
private SongServiceImpl SongserviceImpl;

	private static final Logger log = LoggerFactory.getLogger(SongController.class);
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "Hello from SongService";
	}



	@Autowired
     private SongServiceImpl SongServiceImpl ;
	
    
	@Autowired
	private  SongsRepo songsRepo;
	
	
	  @Autowired 
	  private MongoOperations mongoOperations;
	 
    
    
	  @PostMapping("/AddSong")
	  public String addSong(@RequestParam("song") String song,
	                        @RequestParam("artist") String artist,
	                        @RequestParam("image") MultipartFile image,
	                        @RequestParam("SongFile") MultipartFile SongFile ,
	                        Model model)  {
	      try {
	          Songs s = SongServiceImpl.addSongs(song, artist, image,SongFile);
	          if (s != null) {
	              // Song added successfully - add the song object to the model and return the view name
	              model.addAttribute("song", s);
	              return "/songDetailsView"; // Replace "songDetailsView" with your actual view name
	          } else {
	              // Some error occurred during song addition - handle it appropriately
	              return "/errorView"; // Replace "errorView" with your error view name
	          }
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	          // Handle IO Exception if necessary
	          return "/errorView"; // Replace "errorView" with your error view name
	      }
	  

		
	
	
	@GetMapping("/getsong")
	public String Song(Model model) {
		 model.addAttribute("So", new Songs());
		return "Songs" ;
		
	}





	@GetMapping("/song")
	@ResponseBody
	@Cacheable(value = "songsCache", key = "'allSongs'")
	public List<Songs> song() {

		  log.info("himed55");
		List<Songs> songs = mongoOperations.findAll(Songs.class);

      return songs;
	}



	@GetMapping("/songs")
        public ResponseEntity<List<Songs>> songs(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "6") int size) { 
		 //List<Songs> List<Songs>


			Pageable pageable = PageRequest.of(page, size);
			Query query = new Query()
		            .with(pageable);
		  List<Songs> songs = mongoOperations.find(query, Songs.class);

	        if (songs.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	        }

	        return ResponseEntity.status(HttpStatus.OK).body(songs);
	  }
	 
	 
		
	@PostMapping("/songslo")  
	public String songs(@ModelAttribute("So")Songs So,Model model,@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
		
	
		List<Songs>song=songsRepo.findBySongStartsWith(So.getSong().toLowerCase());
	
	
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(song);
		//System.out.println( jsonString);
	model.addAttribute("Songs",jsonString);
	return "Songs";
	
	
		
	}
}
