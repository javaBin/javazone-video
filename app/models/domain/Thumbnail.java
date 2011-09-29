package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-29
 */
public final class Thumbnail {

    private final int height;
    private final int width;
    private final String url;
    private static final Thumbnail MISSING = new Thumbnail("Image is missing", 0, 0);

    public Thumbnail(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public static Thumbnail missing() {
        return MISSING;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public String url() {
        return url;
    }
}
