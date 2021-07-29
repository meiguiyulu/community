package com.yxj.stratepy;

import com.yxj.dto.AccessTokenDTO;
import com.yxj.dto.GiteeUser;
import com.yxj.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LYJ
 * @create 2021-07-28 19:48
 */

@Service
public class GiteeUserStrategy implements UserStrategy{

    @Autowired
    private GiteeProvider giteeProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);

        accessTokenDTO.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);

        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setAvatar_url(giteeUser.getAvatar_url());
        loginUserInfo.setName(giteeUser.getName());
        loginUserInfo.setId(giteeUser.getId());
        loginUserInfo.setBio(giteeUser.getBio());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "gitee";
    }
}
