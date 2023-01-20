package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회원"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class UserController {

    private final UserService userService;
    private final UserQueryRepository userQueryRepository;
    private final ResponseService responseService;

    // [관리자웹] 회원 전체 조회
    @GetMapping(value = "/admin/users")
    public PageResponse<UserQueryDto.Response> getAllUsers(@RequestParam(required = false) String searchWord, Pageable pageable) {
        return responseService.getPageResponse(userQueryRepository.findUsersBySearchWord(searchWord, pageable));
    }

    // 회원정보 수정
    @PutMapping(value = "/users/{userId}")
    public CommonResponse modifyUser(@PathVariable Long userId, @RequestBody UserDto.Request params) {
        userService.modifyUser(userId, params);
        return responseService.getSuccessResponse();
    }

    // 회원 삭제
    @DeleteMapping(value = "/users/{userId}")
    public CommonResponse deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return responseService.getSuccessResponse();
    }

}
