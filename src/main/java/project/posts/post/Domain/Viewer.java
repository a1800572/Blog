package project.posts.post.Domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Viewer")
@Table(name="viewer")
public class Viewer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long viewerid;
    private String ipadress;
    @CreationTimestamp
    private LocalDateTime viewdate;
    private String countryname;
    private String isocode;
    private String province;
    private String provinceisocode;
    private String cityname;
    private String postalcode;
    private Boolean vpnstatus;
    private String deviceinfo;


    @ManyToMany(mappedBy = "viewers")
    private List<Post> posts = new ArrayList<>();

    public Viewer() {}

    public Viewer(String countryname) {
        super();
        this.countryname=countryname;
    }

    public Long getViewerid() {
        return viewerid;
    }

    public void setViewerid(Long viewerid) {
        this.viewerid = viewerid;
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getIsocode() {
        return isocode;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceisocode() {
        return provinceisocode;
    }

    public void setProvinceisocode(String provinceisocode) {
        this.provinceisocode = provinceisocode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public Boolean getVpnstatus() {
        return vpnstatus;
    }

    public void setVpnstatus(Boolean vpnstatus) {
        this.vpnstatus = vpnstatus;
    }

    public String getDeviceinfo() {
        return deviceinfo;
    }

    public void setDeviceinfo(String deviceinfo) {
        this.deviceinfo = deviceinfo;
    }

    public LocalDateTime getViewdate() {
        return viewdate;
    }

    public void setViewdate(LocalDateTime viewdate) {
        this.viewdate = viewdate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
