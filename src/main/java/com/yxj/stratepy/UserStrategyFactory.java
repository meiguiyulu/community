package com.yxj.stratepy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-28 20:09
 */

@Service
public class UserStrategyFactory {
    @Autowired
    private List<UserStrategy> strategies;

    public UserStrategy getStrategy(String type) {
        for (UserStrategy userStrategy : strategies){
            if (userStrategy.getSupportedType().equals(type)){
                return userStrategy;
            }
        }
        return null;
    }

}
