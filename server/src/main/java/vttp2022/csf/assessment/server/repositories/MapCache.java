package vttp2022.csf.assessment.server.repositories;

import java.io.StringReader;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.csf.assessment.server.models.LatLng;

@Repository
public class MapCache {

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public byte[] getMap(LatLng latlng) {
		// Implmementation in here

		String map_url = UriComponentsBuilder.fromUriString("http://map.chuklee.com/map")
                        .queryParam("lat", latlng.getLatitude())
                        .queryParam("lng", latlng.getLongitude())
                        .toUriString();

		RequestEntity<Void> req = RequestEntity
				.get(map_url)
				.accept(MediaType.IMAGE_PNG)
				.build();

        RestTemplate template = new RestTemplate();
        // ResponseEntity<byte[]> resp = template.getForEntity(map_url, byte[].class);
		ResponseEntity<byte[]> resp = template.exchange(req, byte[].class);
        
		byte[] payload = resp.getBody();

		System.out.println(payload);

		return payload;

	}

	// You may add other methods to this class

}
