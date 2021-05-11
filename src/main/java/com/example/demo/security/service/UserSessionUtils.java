//package com.example.demo.security.service;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.SecurityUser;
//
//import java.nio.file.AccessDeniedException;
//
//public class UserSessionUtils {
//
//    private UserSessionUtils(){
//
//    }
//
//    public static SecurityUser getSessionUser() throws AccessDeniedException {
//        Object detais = SecurityContextHolder.getContext().getAuthentication().getDetails();
//
//        if (!(detais instanceof SecurityUser)){
//            throw new AccessDeniedException("유저 정보가 없습니다.");
//        }
//        return (SecurityUser) detais;
//    }
//}
