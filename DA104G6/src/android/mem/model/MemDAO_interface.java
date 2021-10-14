package android.mem.model;

import java.util.List;

public interface MemDAO_interface {

	
	public void insert(MemVO vo);
	public void update(MemVO vo);
	public void delete(String pk);
	public MemVO findByPrmaryKey(String pk);
	public List<MemVO> getAll();
	//我加的
	boolean isMember(String mem_email, String mem_psw);
	boolean isUserIdExist(String mem_email);
	public MemVO findByEmailPsw(String mem_email , String mem_psw);
	byte[] getImage(String mem_no);
}
