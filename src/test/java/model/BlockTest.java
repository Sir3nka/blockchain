package model;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;
import java.util.regex.Pattern;
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
        Block.setLeadingZeros(1);
        Block firstBlock = blockFabric.createFirstBlock();
        blockStack.push(firstBlock);

        IntStream.range(0, 4)
                .forEach(number -> {
                    blockStack.push(blockFabric.createNextBlock(blockStack.lastElement()));
                });

        blockStack.forEach(System.out::println);
    }

    @Test
    public void shouldContainLeadingZeros() {
        Block.setLeadingZeros(1);
        Block firstBlock = blockFabric.createFirstBlock();

        Block.setLeadingZeros(1);
        Pattern pattern = Pattern.compile("^0");
        assert(pattern.matcher(firstBlock.getCurrentBlock()).find());

        Block.setLeadingZeros(2);
        firstBlock = blockFabric.createFirstBlock();
        pattern = Pattern.compile("^00");
        assert(pattern.matcher(firstBlock.getCurrentBlock()).find());

        pattern = Pattern.compile("^000");
        Block.setLeadingZeros(3);
        firstBlock = blockFabric.createFirstBlock();
        assert(pattern.matcher(firstBlock.getCurrentBlock()).find());
    }

    @Test
    public void verifyStringRepresentation() {
        Block.setLeadingZeros(1);
        Block firstBlock = blockFabric.createFirstBlock();

        assert(firstBlock.getPreviousBlock().equals("0"));
        Pattern pattern = Pattern.compile("^0");
        assert(pattern.matcher(firstBlock.getCurrentBlock()).find());

        System.out.println(firstBlock.toString());
    }
}
