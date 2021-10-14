package com.blog_album.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Blog_AlbumService {

    private Blog_AlbumDAO_Interface dao;

    public Blog_AlbumService() {
//        dao = new Blog_AlbumDAO();
        dao = new Blog_AlbumJNDIDAO();
    }

    public Blog_AlbumVO addPic (String blog_no , byte[] pic) {

        Blog_AlbumVO blog_albumVO = new Blog_AlbumVO();

        blog_albumVO.setBlog_no(blog_no);
        blog_albumVO.setPic(pic);

        dao.insert(blog_albumVO);

        return blog_albumVO;

    }

    public Blog_AlbumVO updatePic (String pic_no , String blog_no , byte[] pic) {

        Blog_AlbumVO blog_albumVO = new Blog_AlbumVO();

        blog_albumVO.setPic_no(pic_no);
        blog_albumVO.setBlog_no(blog_no);
        blog_albumVO.setPic(pic);


        dao.update(blog_albumVO);

        return dao.findByPrimaryKey(pic_no);

    }

    public Blog_AlbumVO getOnePic(String pic_no) {
        return dao.findByPrimaryKey(pic_no);
    }

    public List<Blog_AlbumVO> getAll() {
        return dao.getAll();
    }
    
    public List<String> getOneAlbum(String blog_no) {
    	List<Blog_AlbumVO> list =  dao.getOneAlbum(blog_no);
    	List<String> listPic = new ArrayList();
        for(Blog_AlbumVO aBlogPic :list ) {        	
            byte[] blogPic = aBlogPic.getPic();
            Base64.Encoder encoder = Base64.getEncoder();
            String blogPicBase64 = encoder.encodeToString(blogPic);
            listPic.add(blogPicBase64);
           
        }
        return listPic;
    }
}
