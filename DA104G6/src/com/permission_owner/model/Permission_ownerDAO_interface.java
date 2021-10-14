package com.permission_owner.model;

import java.util.List;

public interface Permission_ownerDAO_interface {
    public void insert(Permission_ownerVO permission_ownerVO);
    public void update(Permission_ownerVO permission_ownerVO);
    public void delete(String pm_no , String mg_no);
    public Permission_ownerVO findByPrimaryKey(String pm_no);
    public List<Permission_ownerVO> getAll();
    public List<String> getMgPermissions(String mg_no);
}
