package com.yxj.stratepy;

import com.yxj.dto.AccessTokenDTO;
import com.yxj.dto.GithubUser;
import com.yxj.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LYJ
 * @create 2021-07-28 19:41
 */

@Service
public class GithubUserStrategy implements UserStrategy {

    @Autowired
    private GithubProvider githubProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setAvatar_url(githubUser.getAvatar_url());
        loginUserInfo.setName(githubUser.getName());
        loginUserInfo.setId(githubUser.getId());
        loginUserInfo.setBio(githubUser.getBio());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "github";
    }
}
