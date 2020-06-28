package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@DisplayName("Basic block chain creation suite")
public class BlockTest {

    private BlockFabric blockFabric;

    @BeforeEach
    public void init() {
        blockFabric = BlockFabricV1.getInstance();
    }

    @Test
    @DisplayName("Creating 5 blocks with leading zeros of 1")
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
    @DisplayName("Checking against regex for containing leading zeros")
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
    @DisplayName("Check if first block has previous block equal to 0")
    public void verifyStringRepresentation() {
        Block.setLeadingZeros(1);
        Block firstBlock = blockFabric.createFirstBlock();

        assert(firstBlock.getPreviousBlock().equals("0"));
        Pattern pattern = Pattern.compile("^0");
        assert(pattern.matcher(firstBlock.getCurrentBlock()).find());

        System.out.println(firstBlock.toString());
    }

    @Test
    @DisplayName("Check against leading zeros of 0")
    public void shouldWorkWithoutLeadingZeros() {
        Block.setLeadingZeros(0);
        Block firstBlock = blockFabric.createFirstBlock();

        assert(firstBlock.getPreviousBlock().equals("0"));
        assert(!firstBlock.getCurrentBlock().equals(""));

        System.out.println(firstBlock.toString());
    }
}
