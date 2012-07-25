package helpers;

import models.CollectionTools;
import models.domain.Talk;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static models.CollectionTools.collect;

/**
 *
 */
public class TagHelper {

    public static List<String> findTagsForTalks(List<Talk> talks) {
        List<String> tags = CollectionTools.findMostPopularElements(collect(talks, Talk.findTags()), 100);
        List<String> filteredTags = new ArrayList<String>();

        for(String tag : tags) {
            if(StringUtils.trim(tag).indexOf(" ") == -1){
                filteredTags.add(tag);
            }
        }
        return filteredTags;
    }

}
