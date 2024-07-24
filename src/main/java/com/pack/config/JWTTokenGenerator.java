package com.pack.config;

import java.util.Map;

import com.pack.model.User;

public interface JWTTokenGenerator {

    Map<String, String> generateToken(User user);
}
