package pre.cg.ftp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pre.cg.ftp.pojo.Item;
import pre.cg.ftp.pojo.User;

import java.util.Date;

/**
 * @ClassName ItemUserDTO
 * @Description TODO
 * @Author QQ163
 * @Date 2020/12/8 13:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUserDTO {
    private Integer id;

    private String title;

    private Integer classifier;

    private String imagepath;

    private String videopath;

    private Date uptime;

    private User user;

    public ItemUserDTO(Item item, User user) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.classifier = item.getClassifier();
        this.imagepath = item.getImagepath();
        this.videopath = item.getVideopath();
        this.uptime = item.getUptime();
        this.user = user;
    }
}
