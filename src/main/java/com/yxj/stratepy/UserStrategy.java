package com.yxj.stratepy;

/**
 * @author LYJ
 * @create 2021-07-28 19:37
 */
public interface UserStrategy {

    LoginUserInfo getUser(String code, String state);

    String getSupportedType();

}
