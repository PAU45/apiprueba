package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_config.UserDetailServiceImpl;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthCreateUserRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthLoginRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caserito_api/authentication")
public class AuthController {
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
        return new ResponseEntity<>(this.userDetailService.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<AuthResponse>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }


}
