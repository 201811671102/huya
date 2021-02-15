package pre.cg.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class Prod {
    @Field("prod_id")
    private Integer prodId;
    @Field("prod_pname")
    private String prodPname;
    @Field("prod_price")
    private Double prodPrice;
    @Field("prod_description")
    private String prodDescription;
    @Field("update_time")
    private Date updateTime;

    public Prod(Integer prodId, String prodPname, Double prodPrice, String prodDescription, Date updateTime) {
        this.prodId = prodId;
        this.prodPname = prodPname;
        this.prodPrice = prodPrice;
        this.prodDescription = prodDescription;
        this.updateTime = updateTime;
    }

    public Prod() {
        super();
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdPname() {
        return prodPname;
    }

    public void setProdPname(String prodPname) {
        this.prodPname = prodPname == null ? null : prodPname.trim();
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription == null ? null : prodDescription.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}