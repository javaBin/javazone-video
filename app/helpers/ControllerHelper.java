package helpers;

import com.google.common.base.Splitter;
import play.Play;

/**
 * Shared stuff between controllers, and because of Plays inheritance-based
 * controllers not easy to share with a super class.
 */
public class ControllerHelper {

    private static final String YEARS = "years";
    private static final String SPEAKERS = "speakers";
    private static final String SPEAKERS_DEFAULT = "A-D,E-H,I-L,M-P,Q-T,U-X,Y-Å";
    private static final String YEARS_DEFAULT = "2009,2010,2011";

    public static Iterable<String> getYearsMenuValue() {
        return Splitter.on(",").split(Play.configuration.getProperty(YEARS, YEARS_DEFAULT));
    }

    public static Iterable<String> getSpeakersMenuValue() {
        return Splitter.on(",").split(Play.configuration.getProperty(SPEAKERS, SPEAKERS_DEFAULT));
    }

}

