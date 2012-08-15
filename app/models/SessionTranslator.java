package models;

import models.domain.external.IncogitoSession;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionTranslator {

    static Map<String, String> replacements = new HashMap<String, String>(){
        {
            put("Ã¥", "å");
            put("Ã¸", "ø");
            put("Ã¦", "æ");
            put("Ã©", "é");
            put("Ã¶", "ö");
            put("Ã\u0098", "Ø");
            put("Ã¤", "ä");
            put("â\u0080\u0099", "’");
            put("Ã¼", "ü");
            put("Ã\u0096", "Ö");
            //put("Ã", "Å");
            put("â", "-");

            put("Â", "");

        }
    };

    public static List<IncogitoSession> translateSessions(List<HashMap<String, Object>> sessions, int defaultYear) {
        List<IncogitoSession> sessionObjects = new ArrayList<IncogitoSession>();

        for(Map<String, Object> v : sessions) {
            sessionObjects.add(createSession(v, defaultYear));
        }

        return sessionObjects;
    }

    private static IncogitoSession createSession(Map<String, Object> v, int defaultYear) {
        IncogitoSession session = new IncogitoSession("", "", 0);
        try {
            session = new IncogitoSession(toUTF8(v, "title"),
                                          toUTF8(v, "bodyHtml"),
                                          getYear(v));
            if(session.year() == 0) {
                session.year(defaultYear);
            }

            for(Map<String, Object> values : getSpeakers(v)) {
                 session.addSpeaker(toUTF8(values, "name"),
                                    toUTF8(values, "bioHtml"),
                                    (String) values.get("photoUrl"));
            }
            return session;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return session;
    }

    private static int getYear(Map<String, Object> v) {
        Map<String, Object> end = (Map<String, Object>) v.get("end");
        if(end != null) {
            return (Integer) end.get("year");
        }
        return 0;
    }

    private static List<Map<String, Object>> getSpeakers(Map<String, Object> v) {
        return (List<Map<String, Object>>) v.get("speakers");
    }

    private static String toUTF8(Map<String, Object> v, String key) throws UnsupportedEncodingException {
        if(! v.containsKey(key)) {
            return "";
        }

        if(null == v.get(key)) {
            return "";
        }

        String iso = (String) v.get(key);
        iso = replaceCrapCharset(iso);
        return new String(iso.getBytes("UTF-8"));
    }

    //a horrible hack to fix crap charsets in input data
    private static String replaceCrapCharset(String iso) {

        String ret = iso;
        for(Map.Entry<String, String> e : replacements.entrySet()) {
            ret = ret.replaceAll(e.getKey(), e.getValue());
        }

        ret = ret.replaceAll("Ã", "Å");
        return ret;
    }
}
