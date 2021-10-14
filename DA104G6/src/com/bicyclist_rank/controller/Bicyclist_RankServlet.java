package com.bicyclist_rank.controller;

import com.bicyclist_rank.model.Bicyclist_RankService;
import com.bicyclist_rank.model.Bicyclist_RankVO;
import com.util.MyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Bicyclist_RankServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
//        PrintWriter out = res.getWriter();
        String action = req.getParameter("action");


        //查下一階的階級名稱


        //查下一階的階級圖片
        if ("get_Next_Rank_Img".equals(action)) {

            Integer rank_req = parseInt(req.getParameter("rank_req"));

            Bicyclist_RankService rankService = new Bicyclist_RankService();
            Bicyclist_RankVO vo = rankService.getRankWithRequirement(rank_req);
            byte[] rank_icon = vo.getRank_icon();
            MyUtil.outputImg(getServletContext() , res , rank_icon);

        }

        //網頁秀圖
        if ("showImg".equals(action)) {

            String rank_no = req.getParameter("rank_no");

            Bicyclist_RankService rankSvc = new Bicyclist_RankService();

            Bicyclist_RankVO bicyclist_rankVO = rankSvc.getOneBicyclist_Rank(rank_no);
            byte[] rank_icon = bicyclist_rankVO.getRank_icon();
            MyUtil.outputImg(getServletContext() , res , rank_icon);

        }


    }
}
