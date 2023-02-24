package vttp2022.csf.assessment.server.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonArrayBuilder;

// Do not modify this class
public class Restaurant {
	
	private String restaurantId;
	private String name;
	private String cuisine;
	private String address;
	private LatLng coordinates;
	private String mapUrl;

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantId() {
		return this.restaurantId;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public String getCuisine() {
		return this.cuisine;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
	public LatLng getCoordinates() {
		return this.coordinates;
	}

	public void setMapURL(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getMapURL() {
		return this.mapUrl;
	}

	public static Restaurant create (Document doc) {
		Restaurant res = new Restaurant();
		res.setName(doc.getString("name"));
		res.setCuisine(doc.getString("cuisine"));
		res.setRestaurantId(doc.getString("restaurant_id"));
		// res.setRestaurantId(doc.getString("address"));
		Document address = (Document) doc.get("address");
		String addressString = (
			address.get("building")+", "
			+address.get("street")+", "
			+address.get("zipcode")+", "
			+doc.getString("borough"));
		res.setAddress(addressString);
		// res.setCoordinates(address.get("coord"));

		ArrayList coordinate = (ArrayList) address.get("coord");
		LatLng latlng = new LatLng();
		latlng.setLatitude(Float.parseFloat(coordinate.get(0).toString()));
		latlng.setLongitude(Float.parseFloat(coordinate.get(1).toString()));

		res.setCoordinates(latlng);

		// System.out.println(coordinate.get(0));

		// System.out.println(address.get("building"));
		// System.out.println(address.get("street"));
		// System.out.println(address.get("zipcode"));
		// System.out.println(address.get("coord"));
		// System.out.println(addressString);
		return res;
	}

	public JsonObject toJson() {
		JsonObjectBuilder json=  Json.createObjectBuilder()
						.add("restaurant_id", restaurantId)
						.add("name", name)
						.add("cuisine", cuisine)
						.add("map", mapUrl)
						.add("address", address);
						// .add("coordinates", coordinates.get);
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		arrayBuilder.add(coordinates.getLatitude());
		arrayBuilder.add(coordinates.getLongitude());
		json.add("coordinates", arrayBuilder);
		return json.build();
	}


	public static Restaurant createWithMap (Document doc) {
		Restaurant res = new Restaurant();
		res.setName(doc.getString("name"));
		res.setCuisine(doc.getString("cuisine"));
		res.setRestaurantId(doc.getString("restaurant_id"));
		// res.setRestaurantId(doc.getString("address"));
		Document address = (Document) doc.get("address");
		String addressString = (
			address.get("building")+", "
			+address.get("street")+", "
			+address.get("zipcode")+", "
			+doc.getString("borough"));
		res.setAddress(addressString);
		// res.setCoordinates(address.get("coord"));

		ArrayList coordinate = (ArrayList) address.get("coord");
		LatLng latlng = new LatLng();
		latlng.setLatitude(Float.parseFloat(coordinate.get(0).toString()));
		latlng.setLongitude(Float.parseFloat(coordinate.get(1).toString()));

		res.setCoordinates(latlng);

		res.setMapURL(doc.getString("map"));


		// System.out.println(coordinate.get(0));

		// System.out.println(address.get("building"));
		// System.out.println(address.get("street"));
		// System.out.println(address.get("zipcode"));
		// System.out.println(address.get("coord"));
		// System.out.println(addressString);
		return res;
	}

}

