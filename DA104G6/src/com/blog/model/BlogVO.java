package com.blog.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BlogVO implements Serializable {

    private String blog_no;
    private String mem_no;
    private String blog_name;
    private Timestamp blog_time;
    private String blog_cont;
    private Integer watch_count;
    private Integer com_count;
    private Integer like_count;
    private Integer status;
    private byte[] Blog_cover_pic;

    public BlogVO() {
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

    public String getBlog_name() {
        return blog_name;
    }

    public void setBlog_name(String blog_name) {
        this.blog_name = blog_name;
    }

    public Timestamp getBlog_time() {
        return blog_time;
    }

    public void setBlog_time(Timestamp blog_time) {
        this.blog_time = blog_time;
    }

    public String getBlog_cont() {
        return blog_cont;
    }

    public void setBlog_cont(String blog_cont) {
        this.blog_cont = blog_cont;
    }

    public Integer getWatch_count() {
        return watch_count;
    }

    public void setWatch_count(Integer watch_count) {
        this.watch_count = watch_count;
    }

    public Integer getCom_count() {
        return com_count;
    }

    public void setCom_count(Integer com_count) {
        this.com_count = com_count;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public byte[] getBlog_cover_pic() {
		return Blog_cover_pic;
	}

	public void setBlog_cover_pic(byte[] blog_cover_pic) {
		Blog_cover_pic = blog_cover_pic;
	}
}
