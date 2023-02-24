package vttp2022.csf.assessment.server.services;

import java.util.List;
import java.util.Optional;
import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.repositories.MapCache;
import vttp2022.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepo;

	@Autowired
	private MapCache mapCache;

	@Autowired
	private S3Service s3Svc;

	// TODO Task 2 
	// Use the following method to get a list of cuisines 
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public List<String> getCuisines() {
		// Implmementation in here
		System.out.println(restaurantRepo.getCuisines());
		return restaurantRepo.getCuisines();
	}

	// TODO Task 3 
	// Use the following method to get a list of restaurants by cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public List<Document> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
        // System.out.println(cuisine);
		return restaurantRepo.getRestaurantsByCuisine(cuisine);
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	public Optional<Restaurant> getRestaurant(String restaurant) {
		// Implmementation in here
		Optional<Restaurant> opt = restaurantRepo.getRestaurant(restaurant);

		// byte[] map = mapCache.getMap(opt.get().getCoordinates());
		// System.out.println("null map is working");
		// System.out.println(">>>>>" + map.toString());

		try{
			if(opt.get().getMapURL() == null){
				byte[] map = mapCache.getMap(opt.get().getCoordinates());
				System.out.println("null map is working");
				System.out.println(map);
				String digitalOceanKey = s3Svc.upload(map);
				opt.get().setMapURL("https://trololo.sgp1.digitaloceanspaces.com/vttptest4%2F"+digitalOceanKey);
				opt = restaurantRepo.saveMap("https://trololo.sgp1.digitaloceanspaces.com/vttptest4%2F"+digitalOceanKey, opt.get().getRestaurantId());
			}
		} catch (Exception e) {
            e.printStackTrace();
        }

		// opt.get().setMapURL("https://trololo.sgp1.digitaloceanspaces.com/vttptest4%2F"+digitalOceanKey);

		return opt;
	}

	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	public void addComment(Comment comment) {
		// Implmementation in here
		
	}
	//
	// You may add other methods to this class
}
