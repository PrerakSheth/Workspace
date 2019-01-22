package com.konkr.Models;

public class SelectDeselectExpression {


    /**
     * message : You gave your expression on this feed successfully.
     * expressionCount : 0
     * commentCount : 11
     * status : 2
     * is_goals : 0
     * is_inspiring : 2
     * is_admiring : 0
     */

    private String message;
    private int expressionCount;
    private int commentCount;
    private int status;
    private int is_goals;
    private int is_inspiring;
    private int is_admiring;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getExpressionCount() {
        return expressionCount;
    }

    public void setExpressionCount(int expressionCount) {
        this.expressionCount = expressionCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_goals() {
        return is_goals;
    }

    public void setIs_goals(int is_goals) {
        this.is_goals = is_goals;
    }

    public int getIs_inspiring() {
        return is_inspiring;
    }

    public void setIs_inspiring(int is_inspiring) {
        this.is_inspiring = is_inspiring;
    }

    public int getIs_admiring() {
        return is_admiring;
    }

    public void setIs_admiring(int is_admiring) {
        this.is_admiring = is_admiring;
    }
}
