package org.dragkes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.w3c.dom.svg.SVGPoint;

@Data
@AllArgsConstructor
public class Text {
    private String text;
    private SVGPoint position;
}
