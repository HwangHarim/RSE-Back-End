package com.game.rse.api.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {
    private String nickname;
    private String introduce;
    private String email;
}
