package com.apropos.curl;


import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class readAPI {

    private String BEARER;
    private String ENDPOINT;
    private String API_URL;


    public readAPI(String api_url, String bearer, String endpoint){
        BEARER = bearer;
        ENDPOINT = endpoint;
        API_URL = api_url+endpoint;
    }

    public JSONObject getcallAPI(){
        RestTemplate restTemplate = new RestTemplate();

        //setting the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + BEARER);

        HttpEntity entity = new HttpEntity(headers);

        //executing the GET call
        System.out.println("CALLING: "+API_URL);
        HttpEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);
        /*HttpEntity<String> response = restTemplate.getForEntity(Url, String.class);*/
        //retrieving the response
        System.out.println("Response"+ response.getBody());

        return new JSONObject(response.getBody());
    }

    public String postcallAPI(){
        RestTemplate restTemplate = new RestTemplate();

        //setting the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + BEARER);
        HttpEntity entity = new HttpEntity(headers);

        //executing the GET call
        HttpEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        /*HttpEntity<String> response = restTemplate.getForEntity(Url, String.class);*/
        //retrieving the response
        System.out.println("Response"+ response.getBody());
        return response.getBody();
    }
}
