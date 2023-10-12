package org.dragkes.parser;

import org.apache.batik.dom.svg.SVGOMPathElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.dom.svg.SVGOMTextElement;
import org.apache.batik.parser.PathParser;
import org.dragkes.entity.Line;
import org.dragkes.entity.Text;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGTransformable;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    @SuppressWarnings("rawtypes")
    public static List<List> getAllElementsTransformed(Element svg) {
        NodeList nodes = svg.getElementsByTagName("*");
        List<Node> result = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Text> texts = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            String name = nodes.item(i).getClass().getSimpleName();
            if (name.equals("SVGOMTextElement") || name.equals("SVGOMPathElement")) {
                result.add(nodes.item(i));
            }
        }

        for (Node node : result) {
            Node parent = node.getParentNode();
            SVGMatrix transformMatrix = null;
            while (parent != null) {
                try {
                    SVGTransformable transformable = (SVGTransformable) parent;
                    if (transformable.getTransform().getBaseVal().consolidate() != null) {
                        SVGMatrix groupTransformMatrix = transformable.getTransform().getBaseVal().consolidate().getMatrix();
                        if (transformMatrix == null) {
                            transformMatrix = groupTransformMatrix;
                        } else {
                            if (groupTransformMatrix != null)
                                transformMatrix = groupTransformMatrix.multiply(transformMatrix);
                        }
                    }
                    parent = parent.getParentNode();
                } catch (ClassCastException e) {
                    parent = parent.getParentNode();
                }
            }
            switch (node.getClass().getSimpleName()) {
                case "SVGOMPathElement" -> {
                    SVGOMPathElement path = (SVGOMPathElement) node;
                    SVGPathHandler handler = new SVGPathHandler();
                    PathParser parser = new PathParser();
                    parser.setPathHandler(handler);
                    parser.parse(path.getAttribute("d"));
                    List<Line> linesFromPath = handler.getLines();
                    if (transformMatrix != null) {
                        for (Line line : linesFromPath) {
                            SVGPoint start = line.getStart();
                            SVGPoint end = line.getEnd();
                            start.matrixTransform(transformMatrix);
                            end.matrixTransform(transformMatrix);
                            lines.add(new Line(start.matrixTransform(transformMatrix), end.matrixTransform(transformMatrix)));
                        }
                    } else {
                        lines.addAll(linesFromPath);
                    }
                }
                case "SVGOMTextElement" -> {
                    SVGOMTextElement textElement = (SVGOMTextElement) node;
                    String value = textElement.getTextContent();
                    SVGOMPoint point = new SVGOMPoint(textElement.getX().getBaseVal().getItem(0).getValue(), textElement.getY().getBaseVal().getItem(0).getValue());
                    Text text;
                    if (transformMatrix != null) {
                        text = new Text(value, point.matrixTransform(transformMatrix));
                    } else {
                        text = new Text(value, point);
                    }
                    texts.add(text);
                }
            }
        }
        return new ArrayList<>(List.of(lines, texts));
    }
}
