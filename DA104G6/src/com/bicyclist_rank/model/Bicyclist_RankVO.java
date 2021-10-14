package com.bicyclist_rank.model;

import java.io.Serializable;

public class Bicyclist_RankVO implements Serializable, Comparable<Bicyclist_RankVO>{
	private String rank_no;
	private String rank_name;
	private String rank_info;
	private Integer rank_req;
	private byte[] rank_icon;
	
	public Bicyclist_RankVO() {
		
	}

	public Bicyclist_RankVO(String rank_no, String rank_name, String rank_info, Integer rank_req, byte[] rank_icon) {
		super();
		this.rank_no = rank_no;
		this.rank_name = rank_name;
		this.rank_info = rank_info;
		this.rank_req = rank_req;
		this.rank_icon = rank_icon;
	}

	public String getRank_no() {
		return rank_no;
	}

	public void setRank_no(String rank_no) {
		this.rank_no = rank_no;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

	public String getRank_info() {
		return rank_info;
	}

	public void setRank_info(String rank_info) {
		this.rank_info = rank_info;
	}

	public Integer getRank_req() {
		return rank_req;
	}

	public void setRank_req(Integer rank_req) {
		this.rank_req = rank_req;
	}

	public byte[] getRank_icon() {
		return rank_icon;
	}

	public void setRank_icon(byte[] rank_icon) {
		this.rank_icon = rank_icon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank_no == null) ? 0 : rank_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bicyclist_RankVO other = (Bicyclist_RankVO) obj;
		if (rank_no == null) {
			if (other.rank_no != null)
				return false;
		} else if (!rank_no.equals(other.rank_no))
			return false;
		return true;
	}

	@Override
	public int compareTo(Bicyclist_RankVO o) {
		if(Integer.parseInt(rank_no.substring(5)) < Integer.parseInt(o.rank_no.substring(5)))
			return 1;
		else if(Integer.parseInt(rank_no.substring(5)) == Integer.parseInt(o.rank_no.substring(5)))
			return 0;
		else
			return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(rank_no).append("\n").append(rank_name).append("\n").append(getRank_info()).append("\n").append(String.valueOf(rank_req)).append("\n");
		return sb.toString();
	}
	
}
