package org.dragkes;

import org.dragkes.entity.Line;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Test extends JFrame {
    public Test(List<List> elements) {
        super("Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel myPanel = new LinePaintDemo(elements);
        getContentPane().add(myPanel);
        setSize(300, 300);
        setVisible(true);
    }

    public static class LinePaintDemo extends JPanel {
        List<List> elements;
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
                for (Object l : elements.get(0)) {
                    Line line = (Line) l;
                    g.drawLine((int) Math.floor(line.getStart().getX()), (int) Math.floor(line.getStart().getY()), (int) Math.floor(line.getEnd().getX()), (int) Math.floor(line.getEnd().getY()));
                }
        }

        public LinePaintDemo(List<List> elements) {
            super();
            this.elements = elements;
        }
    }
}
