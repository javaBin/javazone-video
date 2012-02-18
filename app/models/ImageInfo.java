package models;

/**
 * Data class for information about an image used in the app.
 *
 * @author Knut Haugen <knuthaug@gmail.com>
 * 2012-02-06
 */
public class ImageInfo {

    private int width;
    private int height;
    private int year;
    private String url;

    public ImageInfo(String path, int width, int height, int year) {
        this.url = path;
        this.width = width;
        this.height = height;
        this.year = year;
    }

    public int width() {
        return width;
    }

    public void width(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void height(int height) {
        this.height = height;
    }

    public String url() {
        return url;
    }

    public void url(String url) {
        this.url = url;
    }

    public int year() {
        return year;
    }
}
