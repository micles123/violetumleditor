package com.horstmann.violet.product.diagram.classes.util;

import com.horstmann.violet.framework.util.StringFilterer.Filter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Filter implementation.
 * </p>
 * <p>This filter convert access modifiers from word representation to sign:
 * <ul>
 * <li>public       ->  +   </li>
 * <li>private      ->  -   </li>
 * <li>protected    ->  #   </li>
 * </ul></p>
 * <p>
 * <p>Use it with
 * {@link com.horstmann.violet.framework.util.StringFilterer StringFilterer class}</p>
 *
 * @author Michał Leśniak
 */
public class AccessModifierFilter implements Filter, Serializable
{

    /**
     * Default constructor
     */
    public AccessModifierFilter()
    {
        modifierMapping = new HashMap<String, String>();

        modifierMapping.put("public", "+");
        modifierMapping.put("private", "-");
        modifierMapping.put("protected", "#");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String apply(String text)
    {
        if (text == null) {
            return "";
        }

        String filteredText = "";
        String[] lines = text.split("(?<=" + System.lineSeparator() + ")");

        for (int line = 0; line < lines.length; line++)
        {
            String filteredLine = filterLine(lines[line]);

            if (filteredLine.isEmpty())
            {
                filteredLine = lines[line];
            }

            filteredText += filteredLine;
        }

        return filteredText;
    }

    private String filterLine(String line)
    {
        String filteredLine = "";
        for (Map.Entry<String, String> entry : modifierMapping.entrySet())
        {
            final String regex = entry.getKey();
            filteredLine = matchAndReplace(regex, line, entry.getValue());

            if (!filteredLine.isEmpty())
            {
                break;
            }
        }

        return filteredLine;
    }

    private String matchAndReplace(String regex, String text, String replaceValue)
    {
        Matcher matcher = Pattern.compile(regex).matcher(text);

        if (matcher.find())
        {
            return matcher.replaceFirst(replaceValue);
        }
        else
        {
            return "";
        }
    }

    private static final long serialVersionUID = -6286530069059869293L;
    private Map<String, String> modifierMapping;
}
