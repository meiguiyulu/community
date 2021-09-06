package com.yxj.controller;

import com.yxj.dto.AccessTokenDTO;
import com.yxj.dto.GithubUser;
import com.yxj.entity.User;
import com.yxj.provider.GithubProvider;
import com.yxj.service.UserService;
import com.yxj.stratepy.LoginUserInfo;
import com.yxj.stratepy.UserStrategy;
import com.yxj.stratepy.UserStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author LYJ
 * @create 2021-07-19 20:48
 */

@Controller
public class AuthorizeController {

    @Autowired
    private UserStrategyFactory userStrategyFactory;

    @Autowired
    private UserService userService;

    @GetMapping("/callback/{type}")
    public String callback(@PathVariable(name = "type") String type,
                           @RequestParam(name = "code") String code,
                           @RequestParam(name = "state", required = false) String state,
                           HttpServletResponse response) {
        UserStrategy strategy = userStrategyFactory.getStrategy(type);
        LoginUserInfo loginUserInfo = strategy.getUser(code, state);

        if (loginUserInfo != null && loginUserInfo.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(loginUserInfo.getName());
            user.setAccountId(String.valueOf(loginUserInfo.getId()));
            user.setAvatarUrl(loginUserInfo.getAvatar_url());
            user.setType(type);
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            System.out.println("登陆成功");
            System.out.println(user.getName());
        } else {
            // 登录失败, 重新登陆
            System.out.println("登陆失败");
        }
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        // 清除Session
        request.getSession().removeAttribute("user");
        // 清除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
