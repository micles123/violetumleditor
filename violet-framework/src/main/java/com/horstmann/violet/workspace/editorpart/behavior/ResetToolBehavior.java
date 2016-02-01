package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

/**
 * Behavior class which provides reset selected tool to default after successful adding node or edge.
 * When hotkey is pressed, behavior is turned off until key release
 *
 * @author
 */
public class ResetToolBehavior extends AbstractEditorPartBehavior
{
    /**
     * Default constructor
     *
     * @param graphToolsBar
     */
    public ResetToolBehavior(IGraphToolsBar graphToolsBar)
    {
        this.graphToolsBar = graphToolsBar;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
        {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e)
            {
                if (isHotkeyPressed(e))
                {
                    switch (e.getID())
                    {
                    case KeyEvent.KEY_PRESSED:
                        isEnable = false;
                        break;

                    case KeyEvent.KEY_RELEASED:
                        isEnable = true;
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void afterAddingNodeAtPoint(INode node, Point2D location)
    {
        if (node != null)
        {
            resetGraphTool();
        }
    }

    @Override
    public void afterAddingEdgeAtPoints(IEdge edge, Point2D startPoint, Point2D endPoint)
    {
        if (edge != null)
        {
            resetGraphTool();
        }
    }

    private void resetGraphTool()
    {
        if (!defaultTool.equals(this.graphToolsBar.getSelectedTool()))
        {
            if (isEnable)
            {
                graphToolsBar.setSelectedTool(defaultTool);
            }
        }
    }

    private boolean isHotkeyPressed(KeyEvent e)
    {
        return (e.getKeyCode() & disablingHotkey) == disablingHotkey;
    }

    private int disablingHotkey = KeyEvent.VK_CONTROL;
    private boolean isEnable = true;
    private GraphTool defaultTool = GraphTool.SELECTION_TOOL;

    private IGraphToolsBar graphToolsBar;

}
