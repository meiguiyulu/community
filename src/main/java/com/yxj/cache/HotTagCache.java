package com.yxj.cache;

import com.yxj.dto.HotTagDTO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author LYJ
 * @create 2021-07-28 11:17
 */

@Component
public class HotTagCache {
    private static List<String> hots = new ArrayList<>();

    public static List<String> getHots() {
        return hots;
    }

    public static void setHots(List<String> tags) {
        HotTagCache.hots = tags;
    }

    public void updateTags(Map<String, Integer> tags) {
        int max = 5;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);

        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max){
                priorityQueue.offer(hotTagDTO);
            } else {
                HotTagDTO minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.offer(hotTagDTO);
                }
            }
        });
        List<String> sortedTags = new ArrayList<>();
        while (priorityQueue.size() > 0) {
            sortedTags.add(0, priorityQueue.poll().getName());
        }
        hots = sortedTags;
    }
}
