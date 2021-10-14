package com.manager.model;

import java.util.List;

public interface ManagerDAO_interface {
    public String insert(ManagerVO ManagerVO);
    public void update(ManagerVO ManagerVO);
    public void delete(String mg_no);
    public void pswUpdate(ManagerVO managerVO);
    public ManagerVO findByPrimaryKey(String mg_no);
    public ManagerVO findByAccount(String mg_account);
    public List<ManagerVO> getAll();
}
