package models;

import com.google.common.base.Function;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import models.domain.Talk;
import play.libs.F;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

public class CollectionTools {


    /*
    http://philippeadjiman.com/blog/2010/02/20/a-generic-method-for-sorting-google-collections-multiset-per-entry-count/
     */
    public static <T> List<Multiset.Entry<T>> sortMultisetPerEntryCount(final Multiset<T> multiset) {
        Comparator<Multiset.Entry<T>> comparator = new Comparator<Multiset.Entry<T>>() {
            public int compare(Multiset.Entry<T> e1, Multiset.Entry<T> e2) {
                return e2.getCount() - e1.getCount();
            }
        };
        List<Multiset.Entry<T>> sortedByCount = new ArrayList<Multiset.Entry<T>>(multiset.entrySet());
        Collections.sort(sortedByCount, comparator);
        return sortedByCount;

    }

    /*
    * Apply a function to each element that will return a collection and then flatten.
    */
    public static <F, T> java.lang.Iterable<T> collect(final java.lang.Iterable<F> fromIterable, final Function<? super F, Iterable<T>> function) {
        return Iterables.concat(Iterables.transform(fromIterable, function));
    }

    /*
     * Create a multiset of the collection, sort it, convert to elements, slice and return.
     */
    public static <T> List<T> findMostPopularElements(final Iterable<T> collection, final int number) {
        List<Multiset.Entry<T>> set = sortMultisetPerEntryCount(HashMultiset.create(collection));

        List<T> list = newArrayList(transform(set, new Function<Multiset.Entry<T>, T>() {
            public T apply(Multiset.Entry<T> tEntry) {
                return tEntry.getElement();
            }
        }));

        if(list.size() < number){
            return list.subList(0, list.size());
        }

        return list.subList(0, number);
    }

    public static List<String> extractTags(List<Talk> talks, int max) {
        Iterable<String> allTags = collect(talks, Talk.findTags());
        return CollectionTools.findMostPopularElements(allTags, max);
    }

    public static List<F.Tuple<String, Integer>> extractTagsWithCount(List<Talk> talks) {
        Iterable<String> allTags = collect(talks, Talk.findTags());
        List<Multiset.Entry<String>> set = new ArrayList<Multiset.Entry<String>>(HashMultiset.create(allTags).entrySet());

        List<F.Tuple<String, Integer>> tags = newArrayList();

        for(Multiset.Entry<String> element : set) {
            if(element.getElement().contains(" ")){
                continue;
            }
            tags.add(new F.Tuple(element.getElement(), element.getCount()));
        }

        return tags;
    }
}
