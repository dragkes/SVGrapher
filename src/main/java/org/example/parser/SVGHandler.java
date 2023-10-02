package org.example.parser;

import lombok.Data;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.example.entity.Line;

import java.util.LinkedList;
import java.util.List;

@Data
public class SVGHandler implements PathHandler {
    private Pair<Float, Float> currentOrigin = Pair.of(0F,0F);
    private List<Line> lines = new LinkedList<>();
    @Override
    public void startPath() throws ParseException {

    }

    @Override
    public void endPath() throws ParseException {

    }

    @Override
    public void movetoRel(float x, float y) throws ParseException {
        currentOrigin = Pair.of(currentOrigin.getLeft() + x, currentOrigin.getRight() + y);
    }

    @Override
    public void movetoAbs(float x, float y) throws ParseException {
        currentOrigin = Pair.of(x, y);
    }

    @Override
    public void closePath() throws ParseException {

    }

    @Override
    public void linetoRel(float x, float y) throws ParseException {
        Pair<Float, Float> newOrigin = Pair.of(currentOrigin.getLeft() + x, currentOrigin.getRight() + y);
        lines.add(new Line(currentOrigin, newOrigin));
        currentOrigin = newOrigin;
    }

    @Override
    public void linetoAbs(float x, float y) throws ParseException {
        Pair<Float, Float> newOrigin = Pair.of(x, y);
        lines.add(new Line(currentOrigin, newOrigin));
        currentOrigin = newOrigin;
    }

    @Override
    public void linetoHorizontalRel(float x) throws ParseException {
        Pair<Float, Float> newOrigin = Pair.of(currentOrigin.getLeft() + x, currentOrigin.getRight());
        lines.add(new Line(currentOrigin, newOrigin));
        currentOrigin = newOrigin;
    }

    @Override
    public void linetoHorizontalAbs(float x) throws ParseException {
        Pair<Float, Float> newOrigin = Pair.of(x, currentOrigin.getRight());
        lines.add(new Line(currentOrigin, newOrigin));
        currentOrigin = newOrigin;
    }

    @Override
    public void linetoVerticalRel(float y) throws ParseException {
        Pair<Float, Float> newOrigin = Pair.of(currentOrigin.getLeft(), currentOrigin.getRight() + y);
        lines.add(new Line(currentOrigin, newOrigin));
        currentOrigin = newOrigin;
    }

    @Override
    public void linetoVerticalAbs(float y) throws ParseException {
        Pair<Float, Float> newOrigin = Pair.of(currentOrigin.getLeft(), y);
        lines.add(new Line(currentOrigin, newOrigin));
        currentOrigin = newOrigin;
    }

    @Override
    public void curvetoCubicRel(float x1, float y1, float x2, float y2, float x, float y) throws ParseException {

    }

    @Override
    public void curvetoCubicAbs(float x1, float y1, float x2, float y2, float x, float y) throws ParseException {

    }

    @Override
    public void curvetoCubicSmoothRel(float x2, float y2, float x, float y) throws ParseException {

    }

    @Override
    public void curvetoCubicSmoothAbs(float x2, float y2, float x, float y) throws ParseException {

    }

    @Override
    public void curvetoQuadraticRel(float x1, float y1, float x, float y) throws ParseException {

    }

    @Override
    public void curvetoQuadraticAbs(float x1, float y1, float x, float y) throws ParseException {

    }

    @Override
    public void curvetoQuadraticSmoothRel(float x, float y) throws ParseException {

    }

    @Override
    public void curvetoQuadraticSmoothAbs(float x, float y) throws ParseException {

    }

    @Override
    public void arcRel(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) throws ParseException {

    }

    @Override
    public void arcAbs(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) throws ParseException {

    }
}
