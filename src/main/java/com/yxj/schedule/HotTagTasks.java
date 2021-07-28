package com.yxj.schedule;

import com.yxj.cache.HotTagCache;
import com.yxj.entity.Question;
import com.yxj.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author LYJ
 * @create 2021-07-28 10:12
 */

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void hotTagSchedule(){
        int offset = 0;
        int limit = 20;

        List<Question> list = new ArrayList<>();
        log.info("The time is now {}", new Date());

        Map<String, Integer> priorities = new HashMap<>();

        while (offset==0 || list.size()==limit){
            list = questionMapper.queryAll(offset, limit);
            for(Question question: list){

                String[] tags = question.getTag().split(",");
                for (String tag : tags){
                    Integer priority = priorities.get(tag);
                    if (priority != null){
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(priorities);
        log.info("The time is now {}", new Date());
    }
}
