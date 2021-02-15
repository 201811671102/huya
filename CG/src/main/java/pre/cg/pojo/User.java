package pre.cg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@ToString
public class User implements Serializable {
    @Id
    @Column(name = "uid")
    private Integer uid;
    @Column(name = "uname")
    private String uname;
    @Column(name = "account")
    private String account;
    @Column(name = "password")
    private String password;
    @Column(name = "photo")
    private String photo;
}