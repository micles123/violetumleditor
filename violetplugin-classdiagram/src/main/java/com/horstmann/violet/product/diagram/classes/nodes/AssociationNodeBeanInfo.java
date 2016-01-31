package com.horstmann.violet.product.diagram.classes.nodes;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The bean info for the AssociationNode type
 * @author Michał Leśniak
 */
public class AssociationNodeBeanInfo extends SimpleBeanInfo
{

    @Override
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try
        {
            PropertyDescriptor nameDescriptor = new PropertyDescriptor("name", AssociationNode.class);
            nameDescriptor.setValue("priority", new Integer(1));
            return new PropertyDescriptor[]
                    {
                            nameDescriptor
                    };
        }
        catch (IntrospectionException exception)
        {
            return null;
        }
    }

}
