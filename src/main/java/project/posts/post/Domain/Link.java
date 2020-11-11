package project.posts.post.Domain;

import javax.persistence.*;

@Entity(name = "Link")
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long linkid;
    private String linkname;
    private String url;
    private String thumbnailimg;

    public Link() {}

    public Link(String linkname, String url, String thumbnailimg){
        super();
        this.linkname=linkname;
        this.url=url;
        this.thumbnailimg=thumbnailimg;
    }

    public Long getLinkid() {
        return linkid;
    }

    public void setLinkid(Long linkid) {
        this.linkid = linkid;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailimg() {
        return thumbnailimg;
    }

    public void setThumbnailimg(String thumbnailimg) {
        this.thumbnailimg = thumbnailimg;
    }
}
