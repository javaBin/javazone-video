package models.domain;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import models.ImageInfo;
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
    private final String name;

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
        slug = name.toLowerCase().trim().replace(" ", "");
        this.lastname = name.substring(name.lastIndexOf(" ") + 1);
    }

    public String name() {
        return name;
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
