package org.dragkes;

import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.dragkes.parser.Parser;
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
            final File initialFile = new File("./etazh.svg");
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
                for (Map.Entry<String, List<Node>> entry : nodeMap.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue().size());
                }
                List<List> list = Parser.getAllElementsTransformed(elSVG);
                new Test(list);
                System.out.println(list.size());
                var tspan = svgDocument.getElementById("tspan4624");
                System.out.println(tspan);
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}