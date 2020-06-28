package model;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

public class BlockFabricV1 implements BlockFabric {
    private static BlockFabric instance;

    @Getter
    private static int leadingZeros = 0;
    private static final double nanoSecondsDivider = 1_000_000_000.0;
    private static Pattern regex;

    private final static int min = 1000_0000;
    private final static int max = 9999_9990;

    private BlockFabricV1() {
    }

    ;

    @Override
    public Block createFirstBlock() throws NotImplementedException {
        return new Block();
    }

    @Override
    public Block createNextBlock(Block previous) throws NotImplementedException {
        if (previous == null) {
            return new Block();
        }
        return new Block(previous);
    }

    public static BlockFabric getInstance() {
        if (instance == null) {
            instance = new BlockFabricV1();
        }
        return instance;
    }
}
