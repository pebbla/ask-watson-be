package com.apebble.askwatson.comm.webClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;

import io.netty.handler.codec.http.HttpHeaders;
import reactor.core.publisher.Mono;


public class NotionMangerService {

    

    
    private final ResponseService responseService = new ResponseService();
    public final static String baseURL = "https://api.notion.com/v1";
    private final String apiKey = "secret_onRp7lJwrbHBTxzspXCR9zzpo2CxdyVuYwwMrxK426M";


    public WebClient createWebClient(String baseUrl) {
        WebClient webClient = WebClient.create();
        webClient.options().headers(httpHeaders -> {
            httpHeaders.add("Authorization", "Bearer " + apiKey);
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Notion-Version", "2021-08-16");
        });
        return webClient;
    }


    // get
    public SingleResponse<String> getRequest(String blockId) {
        WebClient webClient = createWebClient(baseURL + "/pages/" + blockId);
        Mono<String> response = webClient.get()
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
        System.out.println(response.block());
        return responseService.getSingleResponse(response.block());
    }



    // post
    public SingleResponse<String> postRequest(Map<String, Object> map) {
        WebClient webClient = createWebClient(baseURL);
        Mono<String> response = webClient.post()
                .body(BodyInserters.fromValue(map.get("properties")))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
        return responseService.getSingleResponse(response.block());
    }
    

    // post
    public SingleResponse<String>  putRequest(Map<String, Object> map) {
        WebClient webClient = createWebClient(baseURL);
        Mono<String> response = webClient.post()
                .body(BodyInserters.fromValue(map.get("properties")))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
        return responseService.getSingleResponse(response.block());

    }
    

    // delete
    public void deleteRequest(String blockId) {
        WebClient webClient = createWebClient(baseURL + "/blocks/" + blockId);
        Mono<String> response = webClient.delete()
            .exchange()
            .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
    }







}
