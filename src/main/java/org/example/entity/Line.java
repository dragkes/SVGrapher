package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
@AllArgsConstructor
public class Line {
    private Pair<Float, Float> start;
    private Pair<Float, Float> end;


    public float getLength() {
        return (float) Math.sqrt(Math.pow(end.getLeft() - start.getLeft(), 2) + Math.pow(end.getRight() - start.getRight(), 2));
    }
}
