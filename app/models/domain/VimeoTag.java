package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-21
 */
public class VimeoTag {
    private int id;
    private String name;
    private String url;

    public VimeoTag(int id, String content, String url) {
        this.id = id;
        this.name = content;
        this.url = url;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String url() {
        return url;
    }
}
