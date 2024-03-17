package com.madroakos.socially.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

@Getter
@ResponseBody
public class JwtResponse {
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}

