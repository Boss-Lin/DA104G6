package com.blog_comment.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Blog_CommentVO implements Serializable {

    private String com_no;
    private String blog_no;
    private String mem_no;
    private Timestamp com_time;
    private byte [] com_pic;
    private String com_cont;
    private Integer Status;

    public Blog_CommentVO() {
    }

    public String getCom_no() {
        return com_no;
    }

    public void setCom_no(String com_no) {
        this.com_no = com_no;
    }

    public String getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(String blog_no) {
        this.blog_no = blog_no;
    }

    public String getMem_no() {
        return mem_no;
    }

    public void setMem_no(String mem_no) {
        this.mem_no = mem_no;
    }

    public Timestamp getCom_time() {
        return com_time;
    }

    public void setCom_time(Timestamp com_time) {
        this.com_time = com_time;
    }

    public byte[] getCom_pic() {
        return com_pic;
    }

    public void setCom_pic(byte[] com_pic) {
        this.com_pic = com_pic;
    }

    public String getCom_cont() {
        return com_cont;
    }

    public void setCom_cont(String com_cont) {
        this.com_cont = com_cont;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
