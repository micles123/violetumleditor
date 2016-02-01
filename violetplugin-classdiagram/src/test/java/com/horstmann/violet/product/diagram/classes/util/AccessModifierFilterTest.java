package com.horstmann.violet.product.diagram.classes.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michal on 01.02.16.
 */
public class AccessModifierFilterTest
{

    private AccessModifierFilter filter;

    @Before
    public void setUp() throws Exception
    {
        filter = new AccessModifierFilter();
    }

    @Test
    public void testNullTextFiltering() throws Exception
    {
        assertEquals("Should return empty string", "", filter.apply(null));
    }

    @Test
    public void testPublicModifierFiltering() throws Exception
    {
        String text = "public void myPublicMethod(PublicClass publicObject)";
        String expected = "+ void myPublicMethod(PublicClass publicObject)";

        assertEquals("Should return filtered string", expected, filter.apply(text));
    }

    @Test
    public void testPrivateModifierFiltering() throws Exception
    {
        String text = "private void myPrivateMethod(PrivateClass privateObject)";
        String expected = "- void myPrivateMethod(PrivateClass privateObject)";

        assertEquals("Should return filtered string", expected, filter.apply(text));
    }

    @Test
    public void testProtectedModifierFiltering() throws Exception
    {
        String text = "protected void myProtectedMethod(ProtectedClass protectedObject)";
        String expected = "# void myProtectedMethod(ProtectedClass protectedObject)";

        assertEquals("Should return filtered string", expected, filter.apply(text));
    }

    @Test
    public void testMultiLineTextFiltering() throws Exception
    {
        String text = "protected void myProtectedMethod(ProtectedClass protectedObject)\n" +
                "private void myPrivateMethod(PrivateClass privateObject)\n" +
                "public void myPublicMethod(PublicClass publicObject)\n" +
                "private void myPrivateMethod(PrivateClass privateObject)\n" +
                "\n" +
                "public void myPublicMethod(PublicClass publicObject)";

        String expected = "# void myProtectedMethod(ProtectedClass protectedObject)\n" +
                "- void myPrivateMethod(PrivateClass privateObject)\n" +
                "+ void myPublicMethod(PublicClass publicObject)\n" +
                "- void myPrivateMethod(PrivateClass privateObject)\n" +
                "\n" +
                "+ void myPublicMethod(PublicClass publicObject)";

        assertEquals("Should return filtered string", expected, filter.apply(text));
    }

    @Test
    public void testTextWithoutMatchingFiltering() throws Exception
    {
        String text = "\n\n\nLorem ipsum dolor sit amet,\n" +
                " consectetur adipiscing elit." +
                " Nullam mattis ullamcorper lacinia.\n" +
                " Ut euismod iaculis ipsum, sit amet tristique lacus fringilla vel.\n" +
                " Duis neque sem, rhoncus id venenatis eget, molestie a ex." +
                " Vivamus maximus enim justo, non facilisis lorem consectetur sed." +
                " Sed suscipit urna sem, sit amet dictum nisi laoreet.\n\n\n";

        assertEquals("Should return the same string", text, filter.apply(text));
    }
}