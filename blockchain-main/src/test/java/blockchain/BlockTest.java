package blockchain;


import blockchain.block.Block;
import blockchain.block.BlockBuilder;
import blockchain.block.SimpleBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@DisplayName("Basic block chain creation suite")
public class BlockTest {


    @BeforeEach
    public void init() {
    }

    @Test
    @DisplayName("Creating 5 blocks with leading zeros of 1")
    public void createBlocks() {
        Stack<SimpleBlock> blockStack = new Stack<>();
        Block.setLeadingZeros(1);
        SimpleBlock firstBlock = BlockBuilder.buildNext(null).build();
        blockStack.push(firstBlock);

        IntStream.range(0, 4)
                .forEach(number -> {
                    blockStack.push(BlockBuilder.buildNext(blockStack.lastElement()).build());
                });

        blockStack.forEach(System.out::println);
    }

    @Test
    @DisplayName("Checking against regex for containing leading zeros")
    public void shouldContainLeadingZeros() {
        Block.setLeadingZeros(1);
        SimpleBlock firstBlock = BlockBuilder.buildNext(null).build();

        Block.setLeadingZeros(1);
        Pattern pattern = Pattern.compile("^0");
        assert(pattern.matcher(firstBlock.getCurrentHash()).find());

        Block.setLeadingZeros(2);
        firstBlock = BlockBuilder.buildNext(null).build();
        pattern = Pattern.compile("^00");
        assert(pattern.matcher(firstBlock.getCurrentHash()).find());

        pattern = Pattern.compile("^000");
        Block.setLeadingZeros(3);
        firstBlock = BlockBuilder.buildNext(null).build();
        assert(pattern.matcher(firstBlock.getCurrentHash()).find());
    }

    @Test
    @DisplayName("Check if first block has previous block equal to 0")
    public void verifyStringRepresentation() {
        Block.setLeadingZeros(1);
        SimpleBlock firstBlock = BlockBuilder.buildNext(null).build();

        assert(firstBlock.getPreviousHash().equals("0"));
        Pattern pattern = Pattern.compile("^0");
        assert(pattern.matcher(firstBlock.getCurrentHash()).find());

        System.out.println(firstBlock.toString());
    }

    @Test
    @DisplayName("Check against leading zeros of 0")
    public void shouldWorkWithoutLeadingZeros() {
        Block.setLeadingZeros(0);
        SimpleBlock firstBlock = BlockBuilder.buildNext(null).build();

        assert(firstBlock.getPreviousHash().equals("0"));
        assert(!firstBlock.getCurrentHash().equals(""));

        System.out.println(firstBlock.toString());
    }
}
