package com.horstmann.violet.framework.util;

import com.horstmann.violet.framework.util.StringFilterer.Filter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Michał Leśniak
 */
public class StringFiltererTest
{

    private StringFilterer filterer;
    private Filter filter1;
    private Filter filter2;
    private Filter filter3;

    private boolean filter1WasRun;
    private boolean filter2WasRun;
    private boolean filter3WasRun;


    @Before
    public void setUp() throws Exception
    {
        filterer = new StringFilterer();

        filter1 = new Filter()
        {
            public String apply(String text)
            {
                filter1WasRun = true;
                return text;
            }
        };
        filter2 = new Filter()
        {
            @Override
            public String apply(String text)
            {
                filter2WasRun = true;
                return text;
            }
        };
        filter3 = new Filter()
        {
            @Override
            public String apply(String text)
            {
                filter3WasRun = true;
                return text;
            }
        };

        filter1WasRun = false;
        filter2WasRun = false;
        filter3WasRun = false;
    }

    @Test
    public void testAddFilter() throws Exception
    {
        filterer.addFilter(filter1);
        List<Filter> list = filterer.getFilters();
        assertEquals("Should return list with 1 element", 1, list.size());
        assertEquals("Should return right filter1 object", filter1, list.get(0));
        filterer.addFilter(filter1);
        list = filterer.getFilters();
        assertEquals("Should return list with 2 elements", 2, list.size());
        assertEquals("Should return right filter1 object", filter1, list.get(1));
    }

    @Test
    public void testAddNullFilter() throws Exception
    {
        filterer.addFilter(null);
        assertEquals("Should return empty list", 0, filterer.getFilters().size());
    }

    @Test
    public void testRemoveFilter() throws Exception
    {
        filterer.addFilter(filter1);
        assertTrue("Should return true", filterer.removeFilter(filter1));
        assertEquals("Should return empty list", 0, filterer.getFilters().size());
    }

    @Test
    public void testRemoveNullFilter() throws Exception
    {
        filterer.removeFilter(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAndChangeListObject() throws Exception
    {
        filterer.addFilter(filter1);
        filterer.addFilter(filter2);
        List list = filterer.getFilters();

        assertEquals("Should return right filter object", filter1, list.get(list.indexOf(filter1)));
        list.remove(filter2);
    }

    @Test
    public void testFilterStringWithoutFilters() throws Exception
    {
        String text = "string";
        assertEquals("Should return the same string", text, filterer.filterString(text));
    }

    @Test
    public void testFilterString() throws Exception
    {
        filterer.addFilter(filter1);
        assertEquals("Should return empty string", "", filterer.filterString(""));
        assertTrue("Should be true", filter1WasRun);

        filterer.addFilter(filter2);
        filterer.addFilter(filter3);
        assertEquals("Should return empty string", "", filterer.filterString(""));
        assertTrue("Should be true", filter2WasRun);
        assertTrue("Should be true", filter3WasRun);
    }

    @Test
    public void testExecuteFilterMethod() throws Exception
    {
        assertEquals("Should return empty string", "", filter1.apply(""));
        assertTrue("Should be true", filter1WasRun);
    }
}