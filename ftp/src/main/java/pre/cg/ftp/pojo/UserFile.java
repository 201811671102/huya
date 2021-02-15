package pre.cg.ftp.pojo;

import java.util.Date;

public class UserFile {
    private Integer fid;

    private String filepath;

    private Date filetime;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public Date getFiletime() {
        return filetime;
    }

    public void setFiletime(Date filetime) {
        this.filetime = filetime;
    }
}