package com.example.demo.login.service;


//만약 아이디와 비밀번호가 제대로 전달되지 않았을 경우 예외 처리 해주는 것
public class InputNotFoundException extends RuntimeException{
    public InputNotFoundException(){

    }
}
