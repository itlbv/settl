package com.itlbv.settl.mob.movement.util;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Destination extends Target {
    private Vector2 position;
}
