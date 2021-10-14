package com.blog_album.model;

import java.io.Serializable;

public class Blog_AlbumVO implements Serializable {

    private String pic_no;
    private String blog_no;
    private byte[] pic;

    public Blog_AlbumVO() {
    }

    public String getPic_no() {
        return pic_no;
    }

    public void setPic_no(String pic_no) {
        this.pic_no = pic_no;
    }

    public String getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(String blog_no) {
        this.blog_no = blog_no;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
