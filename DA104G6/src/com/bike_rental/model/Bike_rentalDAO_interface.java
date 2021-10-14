package com.bike_rental.model;

import java.util.List;
import java.util.Map;

public interface Bike_rentalDAO_interface {
    public String insert(Bike_rentalVO bike_rentalVO);
    public void update(Bike_rentalVO bike_rentalVO);
    public void delete(String bk_rt_no);
    
    public Bike_rentalVO findByPrimaryKey(String bk_rt_no);
    public Bike_rentalVO findByrentalNAME(String bk_rt_name);
    
    public List<Bike_rentalVO> getAll();
    public List<Bike_rentalVO> getAll(Map<String, String[]> map);
}