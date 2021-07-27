package com.yxj.controller;

import com.yxj.dto.AccessTokenDTO;
import com.yxj.dto.GithubUser;
import com.yxj.entity.User;
import com.yxj.provider.GithubProvider;
import com.yxj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    private GithubProvider githubProvider;


    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null && githubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));;
            user.setAvatarUrl(githubUser.getAvatar_url());
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
                         HttpServletResponse response){
        // 清除Session
        request.getSession().removeAttribute("user");
        // 清除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
