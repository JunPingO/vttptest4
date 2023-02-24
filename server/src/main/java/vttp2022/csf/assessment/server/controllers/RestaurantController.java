package vttp2022.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.List;
import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping
public class RestaurantController {
    
    @Autowired
    private RestaurantService restaurantSvc;

    @GetMapping(path = "/api/cuisines")
    public ResponseEntity<List<String>> getCuisines() {

        List<String> cuisineList = restaurantSvc.getCuisines();
        // System.out.println(cuisineList);
        
        return ResponseEntity.ok().body(cuisineList);

    }

    @GetMapping(path = "/api/{cuisine}/restaurants")
    public ResponseEntity<List<Document>> getRestaurantsList(@PathVariable String cuisine) {
        
        List<Document> restaurantList = restaurantSvc.getRestaurantsByCuisine(cuisine);
        // System.out.println(cuisine);
        
        return ResponseEntity.ok().body(restaurantList);
    }

    @GetMapping(path = "/api/{restaurant}/details")
    public ResponseEntity<String> getRestaurantsDetail(@PathVariable String restaurant) {
        
        System.out.println(restaurant);

        Restaurant restaurantObj = restaurantSvc.getRestaurant(restaurant).get();

        System.out.println(">>>>" + restaurantObj.getMapURL());
        
        return ResponseEntity.ok().body(restaurantObj.toJson().toString());
    }

    @GetMapping(path = "/api/{restaurant}/mapurl")
    public ResponseEntity<String> getMapURL(@PathVariable String restaurant) {
        
        System.out.println("routed to mapurl" + restaurant);

        Restaurant restaurantObj = restaurantSvc.getRestaurant(restaurant).get();

        System.out.println(">>>>" + restaurantObj.getMapURL());
        System.out.println(">>>>" + restaurantObj.getRestaurantId());
        return ResponseEntity.ok().body(restaurantObj.toJson().toString());
    }

    @PostMapping(path = "/api/comments")
    public ResponseEntity<String> postOrder(@RequestBody String payload) {

        try (JsonReader reader = Json.createReader(new StringReader(payload))) {
            JsonObject jsonObject = reader.readObject();
            restaurantSvc.addComment(Comment.create(jsonObject));          
            System.out.println(payload);
        } catch (JsonException e) {
            e.printStackTrace();
        }

        JsonObject message = Json.createObjectBuilder()
                .add("message", "Comment posted")            
                .build();    

        return ResponseEntity.ok().body(message.toString());
    }
}
