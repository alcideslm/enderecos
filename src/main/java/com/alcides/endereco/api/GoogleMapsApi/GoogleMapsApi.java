package com.alcides.endereco.api.GoogleMapsApi;

import java.time.Duration;
import java.util.List;

import com.alcides.endereco.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import lombok.Getter;
import lombok.Setter;

@Service
public class GoogleMapsApi {
    @Value("${google.maps.api-code}")
    private String apiKey;

    private final String API_ROOT = "https://maps.googleapis.com/maps/api/geocode";

    @Autowired private RestTemplateBuilder builder;

    private RestTemplate addressRestTemplate = null;

    private RestTemplate prepareRestTemplate() {
		builder.setConnectTimeout(Duration.ofSeconds(5));
        builder.setReadTimeout(Duration.ofSeconds(5)); 
        
        builder.uriTemplateHandler(new RootUriTemplateHandler(API_ROOT));

		return builder.build();
	}

    private RestTemplate getRestTemplate() {
        RestTemplate rest = addressRestTemplate;
        if(rest == null){
            rest = prepareRestTemplate();

        }
        return rest;
    }   

    public Address getAddressCoordinates(Address address){
        RestTemplate template = getRestTemplate();

        String query = "address=${number}+${street},+${neighbourhood},+${city},+${state}";
        query = query.replace("${number}", address.getNumber());
        query = query.replace("${neighbourhood}", address.getCity());
        query = query.replace("${city}", address.getCity());
        query = query.replace("${state}", address.getState());
        query = query.replace("${street}", address.getStreetName());
        query = query.replace(" ", "+");

        String uri = API_ROOT+"/json?"+query+"&key="+apiKey;

        GoogleMapsResponse res = template.getForObject(uri, GoogleMapsResponse.class);
        
        if (res == null || !"OK".equals(res.getStatus()))
            throw new NotFoundException("Não foi possível acessar API de Google Maps");

        if (res.getResults().isEmpty()) 
            throw new NotFoundException("Não Nenhum endereço localizado");

        GoogleMapsResult firstResult = res.getResults().get(0);    

        address.setLatitude(firstResult.getGeometry().getLocation().getLat().toString());
        address.setLongitude(firstResult.getGeometry().getLocation().getLng().toString());
        // ResponseEntity<String> res = template.exchange( "/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key="+apiKey, HttpMethod.GET);
        return address;
    }
}

@Getter @Setter
class GoogleMapsResponse {
    private String status;
    private List<GoogleMapsResult> results;
}

@Getter @Setter
class GoogleMapsResult {
	@JsonProperty("place_id")
    private String placeId;

    private GoogleMapsResultGeometry geometry;
}

@Getter @Setter
class GoogleMapsResultGeometry {
    private GoogleMapsResultLocation location;
}

@Getter @Setter
class GoogleMapsResultLocation {
    private Double lat;
    private Double lng;
}