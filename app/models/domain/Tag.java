package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-25
 */
public final class Tag {
    private final String name;
    private final String url;
    private final int id;

    public Tag(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

}
