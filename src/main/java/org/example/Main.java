package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.parser.PathParser;
import org.example.parser.SVGHandler;
import org.plutext.jaxb.svg11.*;
import org.w3c.dom.DOMImplementation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        SVGHandler handler = new SVGHandler();
        PathParser parser = new PathParser();
        parser.setPathHandler(handler);
        //parser.parse("M6013 3057v0M6013 3031v0M6013 3009v0M6013 2987v0M6013 2965v0M6013 2942v0M6013 2917v0M5793 3057v0M5825 3057v0M5848 3057v0M5870 3057v0M5892 3057v0M5914 3057v0M5937 3057v0M5959 3057v0M5981 3057v0M6013 3057v0M5793 4248v0M5793 4220v0M5793 4197v0M5793 4175v0M5793 4153v0M5793 4131v0M5793 4108v0M5793 4086v0M5793 4064v0M5793 4042v0M5793 4020v0M5793 3997v0M5793 3975v0M5793 3953v0M5793 3931v0M5793 3908v0M5793 3886v0M5793 3864v0M5793 3842v0M5793 3819v0M5793 3797v0M5793 3775v0M5793 3753v0M5793 3730v0M5793 3708v0M5793 3686v0M5793 3664v0M5793 3641v0M5793 3619v0M5793 3597v0M5793 3575v0M5793 3552v0M5793 3530v0M5793 3508v0M5793 3486v0M5793 3463v0M5793 3441v0M5793 3419v0M5793 3397v0M5793 3374v0M5793 3352v0M5793 3330v0M5793 3308v0M5793 3285v0M5793 3263v0M5793 3241v0M5793 3219v0M5793 3197v0M5793 3174v0M5793 3152v0M5793 3130v0M5793 3108v0M5793 3085v0M5793 3057v0M6017 4248v0M5994 4248v0M5972 4248v0M5950 4248v0M5927 4248v0M5905 4248v0M5883 4248v0M5861 4248v0M5839 4248v0M5816 4248v0M5793 4248v0M6017 4392v0M6017 4365v0M6017 4342v0M6017 4320v0M6017 4298v0M6017 4276v0M6017 4248v0");

        File file = new File("./white card.svg");
        if (file.canRead()) log.info("File can be read");
        else log.warn("File can't be read");
        Svg svg = fromXmlToObject(file);

        Map<String, List> elementsSVG = getElementsSVG(svg);


    }

    private static Svg fromXmlToObject(File file) throws JAXBException {
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
    private static Map<String, List> getElementsSVGContent(SVGGContent svggContent) {
        return getElements(Objects.requireNonNull(svggContent).getSVGDescriptionClassOrSVGAnimationClassOrSVGStructureClass());
    }

    @SuppressWarnings("rawtypes")
    private static Map<String, List> getElementsSVG(SVGSvgContent svggContent) {
        return getElements(Objects.requireNonNull(svggContent).getSVGDescriptionClassOrSVGAnimationClassOrSVGStructureClass());
    }

    @SuppressWarnings("rawtypes")
    private static Map<String, List> getElements(List<Object> svgDescriptionClassOrSVGAnimationClassOrSVGStructureClass) {
        List<G> listOfGroups = new ArrayList<>();
        List<Path> listOfPaths = new ArrayList<>();
        List<Text> listOfTexts = new ArrayList<>();

        for (Object element : svgDescriptionClassOrSVGAnimationClassOrSVGStructureClass) {
            @SuppressWarnings("rawtypes")
            JAXBElement data = (JAXBElement) element;
            switch (data.getDeclaredType().getSimpleName()) {
                case "G" -> listOfGroups.add((G) data.getValue());
                case "Path" -> listOfPaths.add((Path) data.getValue());
                case "Text" -> listOfTexts.add((Text) data.getValue());
            }
        }
        return Map.of("groups", listOfGroups, "paths", listOfPaths, "texts", listOfTexts);
    }
}