package com.ntd.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
    ADDITION("addition"),
    SUBTRACTION("subtraction"),
    MULTIPLICATION("multiplication"),
    DIVISION("division"),
    SQUARE_ROOT("square_root"),
    RANDOM_STRING("random_string"),
    DEFAULT("default");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static Type fromString(String type) {
        for (Type t : Type.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No enum constant for type: " + type);
    }

}
