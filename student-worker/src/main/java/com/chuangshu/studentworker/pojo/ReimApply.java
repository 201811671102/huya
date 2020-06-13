package com.chuangshu.studentworker.pojo;


import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 12:35
 */
@Table(name="t_reim_apply")
    public class ReimApply {
    @Id
    private Integer id;
    private String hospital;
    private String referral_prove;
    private String invoice;//发票
    private Date see_doctor_time;
    private Integer student_id;//外键

    public ReimApply(Integer id, String hospital, String referral_prove, String invoice, Date see_doctor_time, Integer student_id) {
        this.id = id;
        this.hospital = hospital;
        this.referral_prove = referral_prove;
        this.invoice = invoice;
        this.see_doctor_time = see_doctor_time;
        this.student_id = student_id;
    }

    public ReimApply() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getReferral_prove() {
        return referral_prove;
    }

    public void setReferral_prove(String referral_prove) {
        this.referral_prove = referral_prove;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Date getSee_doctor_time() {
        return see_doctor_time;
    }

    public void setSee_doctor_time(Date see_doctor_time) {
        this.see_doctor_time = see_doctor_time;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "ReimApply{" +
                "id=" + id +
                ", hospital='" + hospital + '\'' +
                ", referral_prove='" + referral_prove + '\'' +
                ", invoice='" + invoice + '\'' +
                ", see_doctor_time=" + see_doctor_time +
                ", student_id=" + student_id +
                '}';
    }
}
