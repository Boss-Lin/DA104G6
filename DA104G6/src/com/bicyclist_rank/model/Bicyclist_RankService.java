package com.bicyclist_rank.model;

import java.util.List;

public class Bicyclist_RankService {
	private Bicyclist_RankDAO_interface dao;
	
	public Bicyclist_RankService() {
//		dao = new Bicyclist_RankJDBCDAO();
		dao = new Bicyclist_RankJNDIDAO();
	}
	
	public Bicyclist_RankVO addBicyclist_Rank(String rank_name, String rank_info, Integer rank_req, byte[] rank_icon) {
		Bicyclist_RankVO vo = new Bicyclist_RankVO();
		vo.setRank_name(rank_name);
		vo.setRank_info(rank_info);
		vo.setRank_req(rank_req);
		vo.setRank_icon(rank_icon);
		
		dao.insert(vo);
		return vo;
	}
	
	public Bicyclist_RankVO updateBicyclist_Rank(String rank_no, String rank_name, String rank_info, Integer rank_req, byte[] rank_icon) {
		Bicyclist_RankVO vo = new Bicyclist_RankVO();
		vo.setRank_no(rank_no);
		vo.setRank_name(rank_name);
		vo.setRank_info(rank_info);
		vo.setRank_req(rank_req);
		vo.setRank_icon(rank_icon);
		
		dao.update(vo);
		return vo;
	}
	
	public void deleteBicyclist_Rank(String rank_no) {
		dao.delete(rank_no);
	}
	
	public Bicyclist_RankVO getOneBicyclist_Rank(String rank_no) {
		return dao.findByPrimaryKey(rank_no);
	}

	public Bicyclist_RankVO getRankWithRequirement(Integer rank_req) {
		return dao.findByRequirement(rank_req);
	}


	public List<Bicyclist_RankVO> getAllBicyclist_Rank(){
		return dao.getAll();
	}
}
