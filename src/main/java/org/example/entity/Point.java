package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
@AllArgsConstructor
public class Point
        implements TransformableEntity {
    private float x;
    private float y;
}
