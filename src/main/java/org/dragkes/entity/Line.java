package org.dragkes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.svg.SVGPoint;

@Data
@AllArgsConstructor
public class Line {
    private SVGPoint start;
    private SVGPoint end;

    public void setCoordinates(SVGPoint start, SVGPoint end) {
        this.start = start;
        this.end = end;
    }

    public Pair<SVGPoint, SVGPoint> getCoordinates() {
        return Pair.of(start, end);
    }
}
