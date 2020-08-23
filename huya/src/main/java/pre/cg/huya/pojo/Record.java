package pre.cg.huya.pojo;

import java.io.Serializable;

public class Record implements Serializable {
    private static final long serialVersionUID = 2716187161729464931L;

    private Integer rid;

    private Integer huyaid;

    private String phone;

    private String name;

    private Integer score;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getHuyaid() {
        return huyaid;
    }

    public void setHuyaid(Integer huyaid) {
        this.huyaid = huyaid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}