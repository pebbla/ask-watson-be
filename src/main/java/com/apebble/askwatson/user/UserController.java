package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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

    // 회원 등록
    @PostMapping(value = "/users")
    public SingleResponse<User> createUser(@ModelAttribute UserParams params) {
        return responseService.getSingleResponse(userService.createUser(params));
    }

    // 회원 전체 조회
    @GetMapping(value = "/admin/users")
    public PageResponse<User> getAllUsers(@PageableDefault(size=20) Pageable pageable) {
        return responseService.getPageResponse(userService.getAllUsers(pageable));
    }

    // 회원정보 수정
    @PutMapping(value = "/users/{userId}")
    public SingleResponse<User> modifyUser(@PathVariable Long userId, @ModelAttribute UserParams params) {
        return responseService.getSingleResponse(userService.modifyUser(userId, params));
    }

    // 회원 삭제
    @DeleteMapping(value = "/users/{userId}")
    public CommonResponse deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return responseService.getSuccessResponse();
    }
}
