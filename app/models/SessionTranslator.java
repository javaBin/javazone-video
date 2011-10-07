package models;

import models.domain.external.IncogitoSession;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-24
 */
public class SessionTranslator {

    static Map<String, String> replacements = new HashMap<String, String>(){
        {
            put("Ã¥", "å");
            put("Ã¸", "ø");
            put("Ã©", "é");
            put("Ã¶", "ö");
            put("Ã\u0098", "Ø");
        }
    };

    public static List<IncogitoSession> translateSessions(List<HashMap<String, Object>> sessions) {
        List<IncogitoSession> sessionObjects = new ArrayList<IncogitoSession>();

        for(Map<String, Object> v : sessions) {
            sessionObjects.add(createSession(v));
        }

        return sessionObjects;
    }

    private static IncogitoSession createSession(Map<String, Object> v) {
        IncogitoSession session = new IncogitoSession("", "");
        try {
            session = new IncogitoSession(toUTF8(v, "title"), toUTF8(v, "bodyHtml"));

            for(Map<String, Object> values : getSpeakers(v)) {
                 session.addSpeaker(toUTF8(values, "name"),
                                    (String) values.get("bioHtml"),
                                    (String) values.get("photoUrl"));
            }


            //;addSpeakers(session, getSpeakers(v));
            return session;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return session;
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

        String iso = new String((String)v.get(key));
        iso = replaceCrapCharset(iso);
        return new String(iso.getBytes("UTF-8"));
    }

    private static String replaceCrapCharset(String iso) {

        String ret = iso;
        for(Map.Entry<String, String> e : replacements.entrySet()) {
            ret = ret.replaceAll(e.getKey(), e.getValue());
        }

        return ret;
    }
}