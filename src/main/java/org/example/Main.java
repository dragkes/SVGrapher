package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.parser.PathParser;
import org.example.parser.SVGHandler;
import org.example.util.ElementsParser;
import org.plutext.jaxb.svg11.*;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        File file = new File("./SVG_Test_TextAlign.svg");
        if (file.canRead()) log.info("File can be read");
        else log.warn("File can't be read");
        Svg svg = ElementsParser.fromXmlToObject(file);
        @SuppressWarnings("rawtypes")
        Map<String, List<JAXBElement>> elementsSVG = ElementsParser.getElementsSVG(svg);
        log.info("Number of Lines: 92");
        for (String key : elementsSVG.keySet()) {
            log.info("Number of {}: {}", key, elementsSVG.get(key).size());
        }
        if (elementsSVG.containsKey("G")) {
            elementsSVG.get("G").forEach(g -> {
                log.info(ElementsParser.getAllNestedLinesAndTexts((G) g.getValue()).toString());
            });
        }
    }

}