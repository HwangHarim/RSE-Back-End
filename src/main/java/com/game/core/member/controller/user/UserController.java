package com.game.core.member.controller.user;

import com.game.core.common.ApiResponse.ApiResponse;
import com.game.core.member.application.UserService;
import com.game.core.member.domain.User;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.annotation.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse getUser(@AuthMember LoggedInMember loggedInMember){
        System.out.println(loggedInMember.getId());
        return ApiResponse.success("user", loggedInMember.getId());
    }

    @GetMapping
    public ApiResponse getUser() {

        User user = userService.getUser(userService.getUserId());

        return ApiResponse.success("user", user);
    }
}
