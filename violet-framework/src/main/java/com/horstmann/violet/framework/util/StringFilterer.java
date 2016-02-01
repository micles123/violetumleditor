package com.horstmann.violet.framework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Util class for filtering string with multiple filters
 *
 * @author Michał Leśniak
 */
public class StringFilterer implements Serializable
{

    /**
     * Add filter object that will be invoked in filterString method
     *
     * @param filter
     */
    public void addFilter(Filter filter)
    {
        if (filter != null)
            filterList.add(filter);
    }

    /**
     * @return List with all added filters
     */
    public List<Filter> getFilters()
    {
        return Collections.unmodifiableList(filterList);
    }

    /**
     * Remove filter object
     *
     * @param filter  to remove
     * @return true if object was deleted, else false
     */
    public boolean removeFilter(Filter filter)
    {
        return filterList.remove(filter);
    }

    /**
     * Apply all filters on string in order that filters were added
     *
     * @param text  that will be filtered
     * @return filtered string
     */
    public String filterString(String text)
    {
        String filtered = text;

        for (Filter filter : filterList)
            filtered = filter.apply(filtered);

        return filtered;
    }

    private List<Filter> filterList = new ArrayList<Filter>();
    private static final long serialVersionUID = 248992489035859130L;

    /**
     * Interface for filter implementation
     */
    public interface Filter
    {

        /**
         * Method with implemented filter mechanism
         *
         * @param text  String that will be filtered
         * @return filtered string
         */
        String apply(String text);

    }
}
