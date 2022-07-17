package com.apebble.askwatson.comm.exception;

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
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(FaqNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse faqNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse noticeNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse reviewNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse reportNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(CafeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse cafeNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse themeNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(HeartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse heartNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse userNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse categoryNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(EscapeCompleteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse escapeCompleteNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }


    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse locationNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse companyNotFoundException(){
        return responseService.getErrorResponse(404, "자주 묻는질문을 찾을 수 없습니다.");
    }

}
