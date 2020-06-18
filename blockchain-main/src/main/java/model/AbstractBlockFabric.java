package model;

public interface AbstractBlockFabric {
    default Block createFirstBlock() {
        throw new NotImplementedException("Method is not implemented!");
    }

    default Block createNextBlock(Block previous) {
        throw new NotImplementedException("Method is not implemented");
    }

    class NotImplementedException extends RuntimeException {
        NotImplementedException(String msg) {
            super(msg);
        }
    }
}
