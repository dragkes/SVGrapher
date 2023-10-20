package org.dragkes.util.grapher;

import lombok.extern.slf4j.Slf4j;
import org.dragkes.entity.Line;
import org.w3c.dom.svg.SVGPoint;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Grapher {

    private static List<SVGPoint> getPointsAligned(List<SVGPoint> input, double epsilon) {
        List<SVGPoint> uniquePoints = new ArrayList<>();
        for (SVGPoint point : input) {
            boolean isUnique = true;
            for (SVGPoint uniquePoint : uniquePoints) {
                if (areClose(point, uniquePoint, epsilon)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                uniquePoints.add(point);
            }
        }
        return uniquePoints;
    }

    private static boolean areClose(SVGPoint p1, SVGPoint p2, double epsilon) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)) <= epsilon;
    }

    public static List<Line> getLinesAligned(List<Line> input, double epsilon) {
        List<SVGPoint> points = getPoints(input);
        List<SVGPoint> uniquePoints = getPointsAligned(points, epsilon);
        List<Line> lines = new ArrayList<>();
        for (Line line : input) {
            SVGPoint start = line.getStart();
            SVGPoint end = line.getEnd();
            for (SVGPoint uniquePoint : uniquePoints) {
                if (areClose(start, uniquePoint, epsilon)) {
                    start = uniquePoint;
                }
                if (areClose(end, uniquePoint, epsilon)) {
                    end = uniquePoint;
                }
            }
            lines.add(new Line(start, end));
        }
        return lines;
    }

    private static List<SVGPoint> getPoints(List<Line> lines) {
        List<SVGPoint> points = new ArrayList<>();
        for (Line line : lines) {
            points.add(line.getStart());
            points.add(line.getEnd());
        }
        return points;
    }

    public static List<List<Line>> getConnectedAreas(List<Line> input) {
        List<Line> lines = new ArrayList<>(input);
        List<List<Line>> areas = new ArrayList<>();
        while (!lines.isEmpty()) {
            Line line = lines.get(0);

            SVGPoint start = line.getStart();
            SVGPoint end = line.getEnd();
            lines.remove(0);
            if (areas.isEmpty()) {
                areas.add(new ArrayList<>(List.of(line)));
            } else {
                boolean found = false;
                for (List<Line> area : areas) {
                    List<SVGPoint> points = getPoints(area);
                    for (SVGPoint point : points) {
                        if (point.getX() == start.getX() && point.getY() == start.getY() ||
                                point.getX() == end.getX() && point.getY() == end.getY()) {
                            found = true;
                            area.add(line);
                            break;
                        }
                    }
                }
                if (!found) {
                    areas.add(new ArrayList<>(List.of(line)));
                }
            }
        }
        return areas;
    }
}
