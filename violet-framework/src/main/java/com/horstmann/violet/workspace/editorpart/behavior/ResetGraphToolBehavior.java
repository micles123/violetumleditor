package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

/**
 * Created by michal on 06.01.16.
 */
public class ResetGraphToolBehavior extends AbstractEditorPartBehavior
{

    private int disablingHotkey = KeyEvent.CTRL_DOWN_MASK;
    private boolean isEnable = true;
    private GraphTool defaultTool = GraphTool.SELECTION_TOOL;

    private IGraphToolsBar graphToolsBar;

    /**
     * Default constructor
     *
     * @param graphToolsBar
     */
    public ResetGraphToolBehavior(IGraphToolsBar graphToolsBar)
    {
        this.graphToolsBar = graphToolsBar;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher()
        {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e)
            {
                switch (e.getID())
                {
                case KeyEvent.KEY_PRESSED:
                    if (isHotkeyPressed(e))
                    {
                        isEnable = false;
                    }
                    break;

                case KeyEvent.KEY_RELEASED:
                    if (isHotkeyPressed(e))
                    {
                        isEnable = true;
                    }
                    break;
                }
                return true;
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
        if (defaultTool.equals(this.graphToolsBar.getSelectedTool()))
        {
            return;
        }
        if (isEnable)
        {
            graphToolsBar.setSelectedTool(defaultTool);
        }
    }

    private boolean isHotkeyPressed(KeyEvent e)
    {
        return ((e.getKeyCode() | e.getModifiersEx()) & disablingHotkey) == disablingHotkey;
    }

}
