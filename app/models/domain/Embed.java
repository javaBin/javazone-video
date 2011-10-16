package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-07
 */
public final class Embed {
    private final String embedCode;

    public Embed(String code) {
        embedCode = code;
    }

    public String code() {
        return embedCode;
    }
}
