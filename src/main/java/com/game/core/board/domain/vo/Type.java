package com.game.core.board.domain.vo;

public enum Type {
    NOTICE, EVENT, UPDATE, DEVELOPER_NOTES, BUG, GUIDE, FAQ;

    public static Type getType(String type){
        return Type.valueOf(type);
    }
}