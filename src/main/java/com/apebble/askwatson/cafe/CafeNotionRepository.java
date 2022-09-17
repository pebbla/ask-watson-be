package com.apebble.askwatson.cafe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.apebble.askwatson.comm.webClient.NotionMangerService;

@Repository
public class CafeNotionRepository {

    private final NotionMangerService notionMangerService = new NotionMangerService();

    // 카페 추가
    public void createTheme(Cafe cafe){
        Map<String, Object> map = new HashMap<>();
        map.put("properties", "{\"parent\": { \"database_id\": \"9af5d65fc2344b6dafe57ff685f6c57e\" }, \"properties\": {\"title\": {\"title\": [{\"text\": {\"content\": \"Yurts in Big Sur, California\"}}]}}}");
        notionMangerService.postRequest(map);
    }


    // 카페 단건 조회
    public void getCafe(long cafeId){
        // TODO: convert json
        String responseStr = notionMangerService.getRequest("a24aadc7596e45e4a00aa25b72a18e93").getData();
    }


    // 카페 수정
    public void modifyTheme(long cafeId){
        Map<String, Object> map = new HashMap<>();
        map.put("properties", "{\"parent\": { \"database_id\": \"9af5d65fc2344b6dafe57ff685f6c57e\" }, \"properties\": {\"title\": {\"title\": [{\"text\": {\"content\": \"Yurts in Big Sur, California\"}}]}}}");
        notionMangerService.postRequest(map);
    }


    // 카페 삭제
    void deleteThemeByBlockId(String blockId) {
        notionMangerService.deleteRequest(blockId);
    }
    
}
