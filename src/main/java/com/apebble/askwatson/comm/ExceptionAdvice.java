package com.apebble.askwatson.comm;

import com.apebble.askwatson.comm.exception.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(CheckNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse checkNotFoundException(){
        return responseService.getErrorResponse(404, "탈출 완료를 찾을 수 없습니다.");
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse locationNotFoundException(){
        return responseService.getErrorResponse(404, "위치를 찾을 수 없습니다.");
    }

    @ExceptionHandler(SuggestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResponse suggestionNotFoundException(){
        return responseService.getErrorResponse(404, "건의를 찾을 수 없습니다.");
    }

    /**
     * 405 : Method not allowed
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected CommonResponse dataIntegrityViolationException(){
        return responseService.getErrorResponse(405, "연관 관계 문제로 삭제할 수 없습니다.");
    }

    @ExceptionHandler(CheckUndeletableException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected CommonResponse checkUndeletableException(){
        return responseService.getErrorResponse(405, "탈출 완료를 취소할 수 없습니다. 리뷰 작성 여부를 확인해주십시오.");
    }

    /**
     * 500 : Internal Server Error
     */
    @ExceptionHandler(GoogleStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse googleStorageException(){
        return responseService.getErrorResponse(500, "구글 스토리지에 에러가 발생했습니다.");
    }


    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse ioException(){
        return responseService.getErrorResponse(500, "서버 에러입니다. 문제가 지속될 경우 고객센터로 문의해주세요!");
    }

    @ExceptionHandler(KakaoSigninException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse kakaoSigninException(){
        return responseService.getErrorResponse(500, "카카오 로그인 도중 에러가 발생했습니다.");
    }

    @ExceptionHandler(NaverSigninException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse naverSigninException(){
        return responseService.getErrorResponse(500, "네이버 로그인 도중 에러가 발생했습니다.");
    }

    @ExceptionHandler(GoogleSigninException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse googleSigninException(){
        return responseService.getErrorResponse(500, "구글 로그인 도중 에러가 발생했습니다.");
    }

    /**
     * Custom Errors
     */
    @ExceptionHandler(SignInPlatformNotEqualException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse signInPlatformNotEqualException(String msg){
        return responseService.getErrorResponse(-1000, msg);
    }

    //TODO : 런칭 시 주석제거, -> 포스트맨, 스웨거에서 에러메세지 보기위함
//    @ExceptionHandler({ Exception.class })
//    protected CommonResponse handleServerException(Exception ex) {
//        return responseService.getErrorResponse(500, "서버 에러입니다. 문제가 지속될 경우 고객센터로 문의해주세요!");
//    }

}
