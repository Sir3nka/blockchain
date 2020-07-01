import model.BlockFabric;
import model.Block;
import model.BlockFabricV1;

import java.util.Scanner;
import java.util.Stack;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        String ENTRY_MESSAGE = "Enter how many zeros the hash must start with: ";
        System.out.print(ENTRY_MESSAGE);

        try (Scanner scanner = new Scanner(System.in)) {
            int numberOfLeadingZero = scanner.nextInt();
            BlockFabric blockFabric = new BlockFabricV1();

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
