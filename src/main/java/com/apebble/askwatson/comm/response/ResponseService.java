package com.apebble.askwatson.comm.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResponseService{


    // success 단건 응답
    public<T> SingleResponse<T> getSingleResponse(T data){
        SingleResponse<T> singleResponse=new SingleResponse();
        singleResponse.data=data;
        setSuccessResponse(singleResponse);
        return singleResponse;
    }

    // success 리스트 응답
    public<T> ListResponse<T> getListResponse(List<T> dataList){
        ListResponse<T> listResponse = new ListResponse();
        listResponse.dataList=dataList;
        setSuccessResponse(listResponse);
        return listResponse;
    }

    // error bool 응답
    public CommonResponse getErrorResponse(int code, String message){
        CommonResponse response= new CommonResponse();
        response.success=false;
        response.code=code;
        response.message=message;
        return response;
    }

    // success bool 응답
    public CommonResponse getSuccessResponse(){
        CommonResponse response=new CommonResponse();
        setSuccessResponse(response);
        return response;
    }

   private void setSuccessResponse(CommonResponse response){
        response.code=0;
        response.success=true;
        response.message="SUCCESS";
    }
}
