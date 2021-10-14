package com.permission.model;

import java.util.List;
public interface PermissionDAO_interface {
	public void insert(PermissionVO PmVO);
    public void update(PermissionVO PmVO);
    public void delete(String pm_no);
    public PermissionVO findByPrimaryKey(String pm_no);
    public List<PermissionVO> getAll();
}
