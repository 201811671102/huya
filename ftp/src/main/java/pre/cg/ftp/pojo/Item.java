package pre.cg.ftp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Item {
    private Integer id;

    private String title;

    private Integer classifier;

    private String imagepath;

    private String videopath;

    @JsonFormat(
            pattern = "yyyy-MM-dd hh:mm:ss",
            timezone = "GMT+8"
    )
    private Date uptime;

    private Integer uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getClassifier() {
        return classifier;
    }

    public void setClassifier(Integer classifier) {
        this.classifier = classifier;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath == null ? null : imagepath.trim();
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath == null ? null : videopath.trim();
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}