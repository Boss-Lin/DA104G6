package com.blog_report.model;

import java.io.Serializable;
import java.sql.Date;

public class Blog_ReportVO implements Serializable {

    private String rep_no;
    private String blog_no;
    private String mem_no;
    private String reason;
    private byte[] proof;
    private Date rep_date;
    private Integer status;

    public Blog_ReportVO() {
    }

    public String getRep_no() {
        return rep_no;
    }

    public void setRep_no(String rep_no) {
        this.rep_no = rep_no;
    }

    public String getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(String blog_no) {
        this.blog_no = blog_no;
    }

    public String getMem_no() {
        return mem_no;
    }

    public void setMem_no(String mem_no) {
        this.mem_no = mem_no;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public byte[] getProof() {
        return proof;
    }

    public void setProof(byte[] proof) {
        this.proof = proof;
    }

    public Date getRep_date() {
        return rep_date;
    }

    public void setRep_date(Date rep_date) {
        this.rep_date = rep_date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
