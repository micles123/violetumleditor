package com.horstmann.violet.product.diagram.classes.nodes;

import com.horstmann.violet.product.diagram.abstracts.Direction;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * An N-ary association class node in a class diagram.
 *
 * @author Michał Leśniak
 */
public class AssociationNode extends RectangularNode
{

    /**
     * Construct an N-ary association node with a default size and empty name
     */
    public AssociationNode()
    {
        name = new MultiLineString();
        name.setSize(MultiLineString.NORMAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssociationNode clone()
    {
        AssociationNode cloned = (AssociationNode) super.clone();
        cloned.name = name.clone();
        return cloned;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addConnection(IEdge e)
    {
        //FIXME should be able only add binary association edge without arrow ending
        return super.addConnection(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getConnectionPoint(IEdge e)
    {
        Rectangle2D bounds = getDiamondShape().getBounds2D();

        double x = bounds.getCenterX();
        double y = bounds.getCenterY();

        Direction d = e.getDirection(this);
        if (d == null) {
            throw new NullPointerException("Edge not connected with node");
        }

        Direction nearestCardinalDirection = d.getNearestCardinalDirection();
        if (Direction.NORTH.equals(nearestCardinalDirection))
        {
            x = bounds.getMaxX() - (bounds.getWidth() / 2);
            y = bounds.getMaxY();
        }
        if (Direction.SOUTH.equals(nearestCardinalDirection))
        {
            x = bounds.getMaxX() - (bounds.getWidth() / 2);
            y = bounds.getMinY();
        }
        if (Direction.EAST.equals(nearestCardinalDirection))
        {
            x = bounds.getMinX();
            y = bounds.getMaxY() - (bounds.getHeight() / 2);
        }
        if (Direction.WEST.equals(nearestCardinalDirection))
        {
            x = bounds.getMaxX();
            y = bounds.getMaxY() - (bounds.getHeight() / 2);
        }
        return new Point2D.Double(x, y);
    }

    /**
     * Get the bounding rectangle of the name label and the shape
     */
    @Override
    public Rectangle2D getBounds()
    {
        Point2D location = getLocation();
        Rectangle2D bounds = new Rectangle2D.Double(location.getX(), location.getY(), 0, 0);
        Rectangle2D nameBounds = getNameLabelBounds();
        Rectangle2D shapeBounds = getShapeBounds();

        bounds.add(nameBounds);
        bounds.add(shapeBounds);

        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(bounds);
        return snappedBounds;
    }

    private Rectangle2D getNameLabelBounds()
    {
        Point2D location = getLocation();

        Rectangle2D nameBounds = name.getBounds();

        double x = location.getX();
        double y = location.getY();
        double w = nameBounds.getWidth();
        double h = name.getText().isEmpty() ? 0 : nameBounds.getHeight();

        nameBounds.setFrame(x, y, w, h);

        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(nameBounds);
        return snappedBounds;
    }

    private Rectangle2D getShapeBounds()
    {
        Rectangle2D nameBounds = getNameLabelBounds();

        double x = nameBounds.getX();
        double y = nameBounds.getMaxY();
        double w = Math.max(DEFAULT_WIDTH, nameBounds.getWidth());
        double h = DEFAULT_HEIGHT;

        Rectangle2D shape = new Rectangle2D.Double(x, y, w, h);

        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(shape);
        return snappedBounds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        super.draw(g2);
        Color oldColor = g2.getColor();

        Rectangle2D nameBounds = getNameLabelBounds();
        g2.setColor(getTextColor());
        name.draw(g2, nameBounds);

        Shape shape = getDiamondShape();
        g2.setColor(getBackgroundColor());
        g2.fill(shape);
        g2.setColor(getBorderColor());
        g2.draw(shape);

        g2.setColor(oldColor);
    }

    private Shape getDiamondShape()
    {
        Rectangle2D shapeRect = getShapeBounds();
        GeneralPath shape = new GeneralPath();

        double x1 = shapeRect.getCenterX() - (double) (DEFAULT_WIDTH / 2);
        double y1 = shapeRect.getCenterY();

        double x2 = shapeRect.getCenterX();
        double y2 = shapeRect.getY();

        double x3 = shapeRect.getCenterX() + (double) (DEFAULT_WIDTH / 2);
        double y3 = shapeRect.getCenterY();

        double x4 = shapeRect.getCenterX();
        double y4 = shapeRect.getMaxY();

        shape.moveTo(x1, y1);
        shape.lineTo(x2, y2);
        shape.lineTo(x3, y3);
        shape.lineTo(x4, y4);
        shape.lineTo(x1, y1);

        return shape;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape getShape()
    {
        return getDiamondShape();
    }

    /**
     * Sets the name property value.
     *
     * @param newValue the association name
     */
    public void setName(MultiLineString newValue)
    {
        name = newValue;
    }

    /**
     * Gets the name property value.
     *
     * @return the association name
     */
    public MultiLineString getName()
    {
        return name;
    }

    private MultiLineString name;

    private static final int DEFAULT_WIDTH = 60;
    private static final int DEFAULT_HEIGHT = 30;
}
