import model.AbstractBlockFabric;
import model.Block;
import model.BlockFabricV1;

import java.util.Scanner;
import java.util.Stack;
import java.util.stream.IntStream;

public class Main {
    private static String ENTRY_MESSAGE = "Enter how many zeros the hash must start with: ";

    public static void main(String[] args) {
        System.out.print(ENTRY_MESSAGE);

        try (Scanner scanner = new Scanner(System.in)) {
            int numberOfLeadingZero = scanner.nextInt();
            AbstractBlockFabric blockFabric = new BlockFabricV1();

            Stack<Block> blockStack = new Stack<>();
            Block.setLeadingZeros(numberOfLeadingZero);
            Block firstBlock = blockFabric.createFirstBlock();
            blockStack.push(firstBlock);

            IntStream.range(0, 4)
                    .forEach(number -> blockStack.push(blockFabric.createNextBlock(blockStack.lastElement())));

            blockStack.forEach(System.out::println);
        }
    }
}
