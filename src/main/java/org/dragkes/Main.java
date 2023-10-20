package org.dragkes;

import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.util.XMLResourceDescriptor;
import org.dragkes.entity.Line;
import org.dragkes.util.grapher.Grapher;
import org.dragkes.util.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
            final File initialFile = new File("./test.svg");
            try (InputStream sceneFileStream = Files.asByteSource(initialFile).openStream()) {
                Document svg = f.createDocument(null, sceneFileStream);
                SVGDocument svgDocument = (SVGDocument) svg;
                Element elSVG = svgDocument.getRootElement();
                NodeList nodes = elSVG.getElementsByTagName("*");
                Map<String, List<Node>> nodeMap = new HashMap<>();
                for (int i = 0; i < nodes.getLength(); i++) {
                    String name = nodes.item(i).getClass().getSimpleName();
                    if (!nodeMap.containsKey(name)) {
                        nodeMap.put(name, new ArrayList<>());
                    } else {
                        nodeMap.get(name).add(nodes.item(i));
                    }
                }
                List<List> list = Parser.getAllElementsTransformed(elSVG);
                List<Line> lines = List.of(new Line(new SVGOMPoint(0, 0), new SVGOMPoint(0.9f, 0.9f)),
                        new Line(new SVGOMPoint(1, 1), new SVGOMPoint(2, 2)));
                @SuppressWarnings("unchecked")
                List<Line> alignedLines = Grapher.getLinesAligned(list.get(0), 10);
                new Test(list);
//                var areas = Grapher.getConnectedAreas(alignedLines);
//                int maxSize = 0;
//                int index = 0;
//                for (int i = 0; i < areas.size(); i++) {
//                    List<Line> area = areas.get(i);
//                    if (area.size() > maxSize) {
//                        maxSize = area.size();
//                        index = i;
//                    }
//                }
//                List<Line> area = areas.get(index);
                //new Test(List.of(area, new ArrayList<>()));
                var tspan = svgDocument.getElementById("tspan4624");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}