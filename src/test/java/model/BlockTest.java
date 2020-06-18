package model;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;
import java.util.stream.IntStream;

public class BlockTest {

    private AbstractBlockFabric blockFabric;

    @Before
    public void init() {
        blockFabric = new BlockFabricV1();
    }

    @Test
    public void createBlocks() {
        Stack<Block> blockStack = new Stack<>();
        Block.setLeadingZeros(6);
        Block firstBlock = blockFabric.createFirstBlock();
        blockStack.push(firstBlock);

        IntStream.range(0, 4)
                .forEach(number -> {
                    blockStack.push(blockFabric.createNextBlock(blockStack.lastElement()));
                });

        blockStack.forEach(System.out::println);
    }
}
