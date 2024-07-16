package com.ntd.factory;

import com.ntd.entity.Type;
import com.ntd.service.strategy.*;

public class OperationStrategyFactory {
    public static OperationStrategy getStrategy(Type type) {
        switch (type) {
            case ADDITION:
                return new AdditionStrategy();
            case SUBTRACTION:
                return new SubtractionStrategy();
            case MULTIPLICATION:
                return new MultiplicationStrategy();
            case DIVISION:
                return new DivisionStrategy();
            case SQUARE_ROOT:
                return new SquareRootStrategy();
            case RANDOM_STRING:
                return new RandomStringStrategy();
            default:
                throw new IllegalArgumentException("Invalid operation type: " + type);
        }
    }
}
