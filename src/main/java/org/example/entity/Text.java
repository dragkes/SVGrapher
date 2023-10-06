package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
@AllArgsConstructor
public class Text
        implements TransformableEntity {
    private String value;
    private Pair<Float, Float> coordinates;
}
