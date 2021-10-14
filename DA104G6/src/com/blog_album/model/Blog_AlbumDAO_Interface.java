package com.blog_album.model;

import java.util.List;

public interface Blog_AlbumDAO_Interface {

    public void insert (Blog_AlbumVO blog_albumVO);
    public void update(Blog_AlbumVO blog_albumVO);
    public Blog_AlbumVO findByPrimaryKey(String pic_no);
    public List<Blog_AlbumVO> getAll();
    //get one blog album
    public List<Blog_AlbumVO> getOneAlbum(String blog_no);

}
