package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-07
 */
public class Embed {
    private String embedCode;

    public Embed(String code) {
        embedCode = code;
    }

    public Embed() {

    }

    public String code() {
        return embedCode;
    }
}
