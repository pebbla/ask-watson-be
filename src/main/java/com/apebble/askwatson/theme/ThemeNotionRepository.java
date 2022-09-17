package com.apebble.askwatson.theme;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.apebble.askwatson.comm.webClient.NotionMangerService;

@Repository
public class ThemeNotionRepository  {

    private final NotionMangerService notionMangerService = new NotionMangerService();
    private final String databaseID = "9af5d65fc2344b6dafe57ff685f6c57e";

    // 테마 추가
    public void createTheme(Theme theme){
        Map<String, Object> map = new HashMap<>();
        map.put("properties",
                "{\"parent\": { \"database_id\": \"9af5d65fc2344b6dafe57ff685f6c57e\" }, \"properties\": {\"image_url\": {\"id\": \"NvyB\",\"type\": \"url\",\"url\": \"할로할로\"},\"explanation\": {\"id\": \"T%5B~c\",\"type\": \"rich_text\",\"rich_text\": [{\"type\": \"text\",\"text\": {\"content\": \"설명설명\",\"link\": null},\"annotations\": {\"bold\": false,\"italic\": false,\"strikethrough\": false,\"underline\": false,\"code\": false,\"color\": \"default\"},\"plain_text\": \"설명설명\",\"href\": null}]},\"difficulty\": {\"id\": \"Vwaa\",\"type\": \"number\",\"number\": 0.6},\"category_id\": {\"id\": \"cKJv\",\"type\": \"number\",\"number\": 1},\"\": {\"id\": \"c~%3F%7D\",\"type\": \"url\",\"url\": null},\"device_ratio\": {\"id\": \"g_Q%40\",\"type\": \"number\",\"number\": 1},\"time_limit\": {\"id\": \"gcKj\",\"type\": \"number\",\"number\": 70},\"rating\": {\"id\": \"hg%40v\",\"type\": \"number\",\"number\": 4},\"id\": {\"id\": \"xxig\",\"type\": \"number\",\"number\": null},\"cafe_id\": {\"id\": \"x%7CRS\",\"type\": \"number\",\"number\": 1},\"name\": {\"id\": \"title\",\"type\": \"title\",\"title\": [{\"type\": \"text\",\"text\": {\"content\": \"Yurts in Big Sur, California\",\"link\": null},\"annotations\": {\"bold\": false,\"italic\": false,\"strikethrough\": false,\"underline\": false,\"code\": false,\"color\": \"default\"},\"plain_text\": \"Yurts in Big Sur, California\",\"href\": null}]}}}");
        notionMangerService.postRequest(map);
    }


    // 테마 단건 조회
    public void getTheme(long themeId){
        // TODO: convert json
        String themeNotion = notionMangerService.getRequest("a24aadc7596e45e4a00aa25b72a18e93").getData();
    }


    // 테마 수정
    public void modifyTheme(long themeId){
        Map<String, Object> map = new HashMap<>();
        map.put("properties", "{\"parent\": { \"database_id\": \"9af5d65fc2344b6dafe57ff685f6c57e\" }, \"properties\": {\"title\": {\"title\": [{\"text\": {\"content\": \"Yurts in Big Sur, California\"}}]}}}");
        notionMangerService.postRequest(map);
    }


    // 테마 삭제
    void deleteThemeByBlockId(String blockId) {
        notionMangerService.deleteRequest(blockId);
    }
}