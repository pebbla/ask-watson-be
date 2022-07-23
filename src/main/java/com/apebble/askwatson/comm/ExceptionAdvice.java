package com.apebble.askwatson.comm;

import com.apebble.askwatson.comm.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@ControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    /**
     * 400 :
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResponse handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return responseService.getErrorResponse(400, name + "파라미터를 확인해주세요");
    }

    /**
     * 404 : Not Found
     */
    @ExceptionHandler(FaqNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse faqNotFoundException(){
        return responseService.getErrorResponse(404, "자주묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse noticeNotFoundException(){
        return responseService.getErrorResponse(404, "공지사항을 찾을 수 없습니다.");
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse reviewNotFoundException(){
        return responseService.getErrorResponse(404, "리뷰를 찾을 수 없습니다.");
    }

    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse reportNotFoundException(){
        return responseService.getErrorResponse(404, "신고 내용을 찾을 수 없습니다.");
    }

    @ExceptionHandler(CafeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse cafeNotFoundException(){
        return responseService.getErrorResponse(404, "카페를 찾을 수 없습니다.");
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse themeNotFoundException(){
        return responseService.getErrorResponse(404, "테마를 찾을 수 없습니다.");
    }

    @ExceptionHandler(HeartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse heartNotFoundException(){
        return responseService.getErrorResponse(404, "좋아요를 찾을 수 없습니다.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse userNotFoundException(){
        return responseService.getErrorResponse(404, "사용자를 찾을 수 없습니다.");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse categoryNotFoundException(){
        return responseService.getErrorResponse(404, "카테고리를 찾을 수 없습니다.");
    }

    @ExceptionHandler(EscapeCompleteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse escapeCompleteNotFoundException(){
        return responseService.getErrorResponse(404, "탈출 완료를 찾을 수 없습니다.");
    }


    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse locationNotFoundException(){
        return responseService.getErrorResponse(404, "위치를 찾을 수 없습니다.");
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse companyNotFoundException(){
        return responseService.getErrorResponse(404, "회사를 찾을 수 없습니다.");
    }

    //TODO : 런칭 시 주석제거, -> 포스트맨, 스웨거에서 에러메세지 보기위함
//    @ExceptionHandler({ Exception.class })
//    protected CommonResponse handleServerException(Exception ex) {
//        return responseService.getErrorResponse(500, "서버 에러입니다. 문제가 지속될 경우 고객센터로 문의해주세요!");
//    }

}
