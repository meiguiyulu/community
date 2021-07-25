package com.yxj.enums;

/**
 * @author LYJ
 * @create 2021-07-23 9:21
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2),
    ;
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum: CommentTypeEnum.values()){
            if (commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type){
        this.type = type;
    }

}
