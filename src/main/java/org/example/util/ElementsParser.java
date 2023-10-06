package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.batik.parser.PathParser;
import org.example.entity.TransformableEntity;
import org.example.parser.SVGHandler;
import org.plutext.jaxb.svg11.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;

@Slf4j
public final class ElementsParser {

    private ElementsParser() {}
    @SuppressWarnings("rawtypes")
    public static Map<String, List<JAXBElement>> getElements(List<Object> svgDescriptionClassOrSVGAnimationClassOrSVGStructureClass) {
        Map<String, List<JAXBElement>> elements = new HashMap<>();

        for (Object data : svgDescriptionClassOrSVGAnimationClassOrSVGStructureClass) {
            @SuppressWarnings("rawtypes")
            JAXBElement element = (JAXBElement) data;
            if (!elements.containsKey(element.getDeclaredType().getSimpleName())) {
                elements.put(element.getDeclaredType().getSimpleName(), new ArrayList<>());
                elements.get(element.getDeclaredType().getSimpleName()).add(element);
            }
            else {
                elements.get(element.getDeclaredType().getSimpleName()).add(element);
            }
        }
        return elements;
    }

    @SuppressWarnings("rawtypes")
    private static Map<String, List<JAXBElement>> getGroupElements(G g) {
        return getElements(Objects.requireNonNull(g).getSVGDescriptionClassOrSVGAnimationClassOrSVGStructureClass());
    }

    @SuppressWarnings("rawtypes")
    public static Map<String, List<JAXBElement>> getElementsSVG(SVGSvgContent svggContent) {
        return getElements(Objects.requireNonNull(svggContent).getSVGDescriptionClassOrSVGAnimationClassOrSVGStructureClass());
    }

    public static Svg fromXmlToObject(File file) throws JAXBException {
            JAXBContext jaxbContext = JAXBContext.newInstance(Svg.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            Object output = un.unmarshal(file);
            if (output instanceof JAXBElement) {
                @SuppressWarnings("unchecked")
                JAXBElement<Svg> obj = (JAXBElement<Svg>) output;
                return obj.getValue();
            }
            return null;
    }

    @SuppressWarnings("rawtypes")
    public static List<TransformableEntity> getAllNestedLinesAndTexts(G g) {
        List<TransformableEntity> entities = new ArrayList<>();
        if (!getGroupElements(g).containsKey("G")) {
            String transform = g.getTransform();
            if (true ) { //transform == null || transform.isEmpty()
                for (Map.Entry<String, List<JAXBElement>> element : getGroupElements(g).entrySet()) {
                    switch (element.getKey()) {
                        case "Path" -> {
                            SVGHandler handler = new SVGHandler();
                            PathParser parser = new PathParser();
                            parser.setPathHandler(handler);
                            element.getValue().forEach(obj -> {
                                @SuppressWarnings("unchecked")
                                JAXBElement<Path> path = (JAXBElement<Path>) obj;
                                parser.parse(path.getValue().getD());
                            });
                            entities.addAll(handler.getLines());
                        }
                        case "Text" -> {
                            element.getValue().forEach(obj -> {
                                @SuppressWarnings("unchecked")
                                JAXBElement<Text> text = (JAXBElement<Text>) obj;
                                //TODO add text parsing
                            });
                        }
                    }
                }
                return entities;
            } else {
                //TODO transform
            }
        } else {
            for (JAXBElement jaxbElement : getGroupElements(g).get("G")) {
                @SuppressWarnings("unchecked")
                G group = ((JAXBElement<G>) jaxbElement).getValue();
                entities.addAll(getAllNestedLinesAndTexts(group));
            }
            return entities;
        }
        return entities;
    }
}
