package models.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import play.data.validation.Required;
import play.modules.morphia.Model;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-01
 */

@Entity
public class Speaker extends Model {

    @Required
    private final String name;

    @Id
    private final String slug;

    private final String bio;
    private final String photoURL;

    public Speaker(String name, String bio, String url) {
        this.name = name;
        this.bio = bio.trim();
        this.photoURL = url;
        slug = name.toLowerCase().trim().replace(" ", "");
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
}
