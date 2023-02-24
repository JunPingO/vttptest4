package vttp2022.csf.assessment.server.repositories;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {

	@Autowired
	private MongoTemplate mongotemplate;


	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  db.restaurants.distinct('cuisine')
	public List<String> getCuisines() {
		// Implmementation in here
		List<String> resultList = new LinkedList<>();
		List<String> cuisineList = new LinkedList<>();
		resultList = mongotemplate.findDistinct(new Query(), "cuisine", "restaurants", String.class);
		for (String c : resultList){
			cuisineList.add(c.replace("/", "_"));
		}
		// System.out.print("\n" + cuisineList);

		return cuisineList;
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  db.restaurants.find(
	// 	{cuisine: 'African'},
	// 	{_id:0, name: 1}
	// )
	public List<Document> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		
		// List<Document> restaurantList = new LinkedList<>();
		Criteria criteria = Criteria.where("cuisine").is(cuisine.replace("_", "/"));
		Query query = Query.query(criteria);
		query.fields().exclude("_id").include("name");

		List<Document> restaurantList = mongotemplate.find(query, Document.class, "restaurants");
		// System.out.print(restaurantList);
		// System.out.print(mongotemplate.find(query, String.class, "restaurants"));

		return restaurantList;

	}	

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  db.restaurants.find(
	// 	{name: 'African'},
	// 	{_id:0, restaurant_id:1, name: 1, cuisine: 1, address: 1, borough: 1}
	// )
	public Optional<Restaurant> getRestaurant(String restaurant) {
		// Implmementation in here
		Criteria criteria = Criteria.where("name").is(restaurant);
		Query query = Query.query(criteria);
		query.fields().exclude("_id").include("restaurant_id", "name", "cuisine", "address", "borough", "map");
		// Restaurant restaurantDetail = Restaurant.create((Document) mongotemplate.find(query, Document.class, "restaurants"));

		Restaurant restaurantDetail = Restaurant.create(
			mongotemplate.find(query, Document.class, "restaurants").get(0)
		);

		// System.out.println("restDetail>>>>" + restaurantDetail.getAddress());
		return Optional.of(restaurantDetail);
		
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {
		// Implmementation in here
		mongotemplate.insert(comment, "comments");
		
	}
	
	// You may add other methods to this class

	public Optional<Restaurant> saveMap(String mapurl, String restaurant_id) {
		// Implmementation in here
		
		//update
		Criteria criteria = Criteria.where("restaurant_id").is(restaurant_id);
		Query query = Query.query(criteria);
		Update updateOps = new Update()
			.set("map", mapurl);
		mongotemplate.updateMulti(query, updateOps, Document.class, "restaurants");

		//retrieve updated document to cast
		Criteria criteria2 = Criteria.where("restaurant_id").is(restaurant_id);
		Query query2 = Query.query(criteria);
		query.fields().exclude("_id").include("restaurant_id", "name", "cuisine", "address", "borough", "map");
		Restaurant restaurantDetail = Restaurant.createWithMap(
			mongotemplate.find(query, Document.class, "restaurants").get(0)
		);



		// System.out.println("restDetail>>>>" + restaurantDetail.getAddress());
		return Optional.of(restaurantDetail);
		
	}

}
