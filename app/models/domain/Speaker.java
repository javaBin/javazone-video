package models.domain;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import models.img.ImageInfo;
import play.data.validation.Required;
import play.modules.morphia.Model;

import java.util.HashMap;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-01
 */

@Index("lastname")
@Model.AutoTimestamp
@Entity
public final class Speaker extends Model {

    @Required
    private String name;

    @Id
    private String slug;

    private String lastname;
    private final String bio;
    private final String photoURL;
    private String twitterName;

    @Embedded
    private HashMap<String, ImageInfo> images = new HashMap<String, ImageInfo>();

    public Speaker(String name, String bio, String url) {
        this.name = name;
        this.bio = bio.trim();
        this.photoURL = url;
        slug = name.toLowerCase().trim().replaceAll(" ", "")
                .replaceAll("\\)", "").replaceAll("å", "a").replaceAll("ø", "o")
                .replaceAll("æ", "a").replaceAll("ö", "o").replaceAll("ä", "a")
                .replaceAll("\\(", "").replaceAll("-", "").replaceAll("é", "e");
        this.lastname = name.substring(name.lastIndexOf(" ") + 1);
    }

    public String name() {
        return name;
    }

    public void name(String newName) {
        name = newName;
    }
    public String bio() {
        return bio;
    }

    public String photoURL() {
        return photoURL;
    }

    public String slug() {
        return slug;
    }

    public void images(HashMap<String, ImageInfo> sizes) {
        images = sizes;
    }

    public HashMap<String, ImageInfo> images() {
        return images;
    }

    public String twitterName() {
        return twitterName;
    }

    public void twitterName(String twitterName) {
        this.twitterName = twitterName;
    }
}
