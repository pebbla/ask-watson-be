package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회원"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    // 카카오 토큰으로 로그인
    @PostMapping(value = "/kakao/signin")
    public SingleResponse<Map<String, Object>> signInByKakaoToken(String accessToken){
        //TODO : 회원가입 된 경우 access token, refresh token 전달
        // 403리턴 값 전달
        return responseService.getSingleResponse(userService.signInByKakaoToken(accessToken));
    }

    // 네이버 토큰으로 로그인

    // 구글 토큰으로 로그인


    // 회원 등록
    @PostMapping(value = "/users")
    public SingleResponse<User> createUser(@RequestBody UserParams params) {
        return responseService.getSingleResponse(userService.createUser(params));
    }

    // 회원 전체 조회
    @GetMapping(value = "/admin/users")
    public PageResponse<UserDto.Response> getAllUsers(@PageableDefault(size=20) Pageable pageable) {
        return responseService.getPageResponse(userService.getAllUsers(pageable));
    }

    // 회원정보 수정
    @PutMapping(value = "/users/{userId}")
    public SingleResponse<User> modifyUser(@PathVariable Long userId, @RequestBody UserParams params) {
        return responseService.getSingleResponse(userService.modifyUser(userId, params));
    }

    // 회원 삭제
    @DeleteMapping(value = "/users/{userId}")
    public CommonResponse deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return responseService.getSuccessResponse();
    }
}
