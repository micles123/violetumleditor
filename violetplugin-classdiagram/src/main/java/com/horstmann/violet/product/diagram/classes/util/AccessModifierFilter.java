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
 * <p>This filter convert access modifiers:
 * <ul>
 * <li>public       ->  +   </li>
 * <li>private      ->  -   </li>
 * <li>protected    ->  #   </li>
 * </ul></p>
 *
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
        StringBuffer filtered = new StringBuffer();
        Pattern pattern;
        Matcher matcher;

        String[] lines = text.split(System.lineSeparator());

        for (int line = 0; line < lines.length; line++)
        {
            String filteredLine = "";
            for (Map.Entry<String, String> entry : modifierMapping.entrySet())
            {
                final String regex = entry.getKey() + "\\s";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(lines[line]);

                if (matcher.find())
                {
                    filteredLine = matcher.replaceFirst(entry.getValue() + ' ');
                    break;
                }
            }

            if (filteredLine.isEmpty())
                filteredLine = lines[line];

            filtered.append(filteredLine);

            if (lines.length > 1 && line != lines.length - 1)
            {
                filtered.append('\n');
            }
        }

        return filtered.toString();
    }

    private static final long serialVersionUID = -6286530069059869293L;
    private Map<String, String> modifierMapping;
}
