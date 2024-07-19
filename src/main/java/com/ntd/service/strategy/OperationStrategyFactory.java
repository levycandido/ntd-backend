package com.ntd.service.strategy;

import com.ntd.entity.Type;

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
            default:
                throw new IllegalArgumentException("Invalid operation type: " + type);
        }
    }
}
