package com.bicyclist_rank.model;

import java.util.List;

public interface Bicyclist_RankDAO_interface {
	public void insert(Bicyclist_RankVO vo);
	public void update(Bicyclist_RankVO vo);
	public void delete(String pk);
	public Bicyclist_RankVO findByPrimaryKey(String pk);
	public Bicyclist_RankVO findByRequirement (Integer rank_req);
	public List<Bicyclist_RankVO> getAll();
}
