package pre.cg.huya.pojo;

import java.io.Serializable;

public class PlayInfo implements Serializable {
    private static final long serialVersionUID = -3647048000767337538L;

    private String uid;

    private String name;

    private String phone;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}