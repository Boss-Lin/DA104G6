package android.group.model;

import java.util.List;

public interface GroupDAO_interface {
	public void insert(GroupVO vo);
	public void update(GroupVO vo);
	public void delete(String pk);
	public GroupVO findByPrmaryKey(String pk);
	public List<GroupVO> getAll();
	byte[] getImage(String gro_no);
}