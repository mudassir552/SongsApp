package com.example.demo.Controllers;

import java.util.*;

import com.example.demo.Userinfo.Userinfo;
import com.example.demo.UserinfoService.SongService;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.Requests.SongsRequest;
import com.example.demo.Roles.Roles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import  com.example.demo.JWT.Jwtservice;
import com.example.demo.RoleRepo.Rolerepo;
import com.example.demo.Roles.Role;
import com.example.demo.UserRepo.Userrepo;
import com.example.demo.Users.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private Userrepo userrepo;
    @Autowired
    private Rolerepo rolerepo;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Jwtservice jwtservice;

    @Autowired

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextRepository securityContext;

    @Value("${songs.url}")
    private  String SONGS_API_URL;
   //String api = "http://localhost:8082/songs";


   @Autowired
    private SongService SongService;

     @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/acesss")
    public List<User> getUsers() {


        List<User> users = userrepo.findAll();


        return users;

    }

    @GetMapping("/signup")
    public String singUp(Model Model) {
        Model.addAttribute("Authrequest", new Authrequest());
        return "Signup";

    }

    @PostMapping("/user/signup")
    public ResponseEntity<String> RegisterSignedUpUser(@Valid @RequestBody Authrequest user) throws Exception {


        Optional<User> existingUser = userrepo.findByName(user.getUsername());

        if (existingUser.isPresent()) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        User registerUser = new User();
        registerUser.setName(user.getUsername());


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        registerUser.setPassword(encodedPassword);


        Role role = new Role();
        role.setRole(Roles.NORMAL_USER);
        registerUser.setRoles(List.of(role));


        userrepo.save(registerUser);

        // Return success message
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @GetMapping("/{id}/getuser")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        try {
            Optional<User> user = userrepo.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with given Id");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while fetching user");
        }
    }

    @PostMapping("/addrole")
    public String addRole(@RequestBody Role role) {
        rolerepo.save(role);
        return "Role saved";

    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@ModelAttribute() Authrequest authRequest, HttpServletResponse response,HttpServletRequest request) throws InvalidCredentialsException {

        Optional<User> existingUser = userrepo.findByName(authRequest.getUsername());
        boolean hasAccessToken = false;
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("User not found! Please signup.");
        }

        User existingUserObj = existingUser.get();
       Userinfo userinfo = new Userinfo(existingUserObj);
        Cookie cookie [] = request.getCookies();

        if(cookie!=null){

            for (Cookie cook :  cookie) {

                if ("accessToken".equals(cook.getName())) {

                    if (jwtservice.validateToken(cook.getValue(), userinfo))
                        hasAccessToken = true;
                      break;
                }
            }
            }


    if(!hasAccessToken) {

        String accessToken = jwtservice.generateToken(existingUserObj.getName());
        String refreshToken = jwtservice.generateRefreshToken(existingUserObj.getName());

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);  // Prevent JavaScript access
        accessTokenCookie.setSecure(false);    // Ensure it is sent over HTTPS (only in production)
        accessTokenCookie.setMaxAge(3600);   // 1 hour expiry time
        accessTokenCookie.setPath("/");      // Make the cookie available to the entire site

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);  // Prevent JavaScript access
        refreshTokenCookie.setSecure(false);    // Ensure it is sent over HTTPS (only in production)
        refreshTokenCookie.setMaxAge(2592000); // 30 days expiry time for refresh token
        refreshTokenCookie.setPath("/");       // Make the cookie available to the entire site

        // Add the cookies to the response
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

    }



       return "redirect:/user-songs";



    }





    @GetMapping("/user-songs")
    public String getUserSongs(@CookieValue(name = "accessToken", required = true) String token, Model model) throws JsonProcessingException {
        List<SongsRequest> songs = SongService.getSongs(token);

        ObjectMapper mapper = new ObjectMapper();
        String songsJson = mapper.writeValueAsString(songs);
        model.addAttribute("songs", songsJson);

        return "Songs";
    }



    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) throws InvalidCredentialsException {
        if (jwtservice.validateRefreshToken(refreshToken)) {
            String username = jwtservice.getUsernameFromRefreshToken(refreshToken);
            String newAccessToken = jwtservice.generateToken(username);
            return ResponseEntity.ok("Bearer " + newAccessToken);
        } else {
            throw new InvalidCredentialsException("Invalid refresh token.");
        }
    }


    @GetMapping("/error")
    public String err() {

        return "error";

    }

    @GetMapping("/login")
    public String login(Model model) {
        Authrequest authRequest = new Authrequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("123");
        model.addAttribute("Authrequest", authRequest);

        return "login";
    }

    @GetMapping("/profile")
    public String userProfile(HttpServletRequest request, Model model) {
        // Ensure cookies are not null
        if (request.getCookies() == null) {
            return "redirect:/login";
        }

        // Find the accessToken cookie
        Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst();

        // If token is not present, redirect to login
        if (tokenCookie.isEmpty()) {
            return "redirect:/login";
        }

        String token = tokenCookie.get().getValue();

        // Assuming your jwtService is working correctly
        String uname = jwtservice.extractUsername(token);
        model.addAttribute("username", uname);

        return "profile";
    }
}











