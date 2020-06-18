package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import utils.StringUtil;

@EqualsAndHashCode
final class Block {
    private static int leadingZeros = 0;
    private static final double nanoSecondsDivider = 1_000_000_000.0;
    private static Pattern regex;

    private final static int min = 1000_0000;
    private final static int max = 9999_9990;

    @Getter
    private final int id;
    private final long timeStamp;
    private int magicNumber;

    @Getter
    private final String previousBlock;

    @Getter
    private String currentBlock;
    private final double executionTime;

    /**
     * @param leadingZeros = number of leading zeros for hash to be valid
     *                     more zeros makes it more expensive to create a block unit
     */
    static void setLeadingZeros(int leadingZeros) {
        Block.leadingZeros = leadingZeros;
        createRegexForHashMatching();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Block: \n")
                .append("Id: ").append(id).append("\n")
                .append("Timestamp: ").append(timeStamp).append("\n")
                .append("Hash of the previous block: \n").append(previousBlock).append("\n")
                .append("Hash of the block: \n").append(currentBlock).append("\n")
                .append("Magic number: ").append(magicNumber).append("\n")
                .append("Execution time: ").append(executionTime);

        return builder.toString();
    }

    private String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();

        builder.append(id)
                .append(timeStamp)
                .append(previousBlock)
                .append(magicNumber);

        return builder.toString();
    }

    /**
     * function helper to reduce redundant
     * pattern compilation which is costly
     */
    private static void createRegexForHashMatching() {
        StringBuilder regexBuilder = new StringBuilder();
        regexBuilder.append("^");

        IntStream.range(0, leadingZeros)
                .forEach(num -> regexBuilder.append("0"));

        regex = Pattern.compile(regexBuilder.toString());
    }

    private void findHashWithLeadingZeros() {
        this.currentBlock = "";

        while (!regex.matcher(currentBlock).find()) {

            this.magicNumber = ThreadLocalRandom
                    .current()
                    .nextInt(min, max + 1);
            this.currentBlock = StringUtil.applySha256(this.getStringRepresentation());

        }
    }

    /**
     * Method for creating first block of blockchain
     */
    Block() {
        long start = System.nanoTime();

        this.id = 1;
        this.timeStamp = new Date().getTime();
        previousBlock = "0";
        findHashWithLeadingZeros();

        executionTime = (System.nanoTime() - start) / nanoSecondsDivider;

    }

    /**
     * Constructor used to create next blocks
     * @param previous = Previous block to make them connected
     */
    Block(Block previous) {
        long start = System.nanoTime();

        this.id = previous.getId() + 1;
        this.timeStamp = new Date().getTime();
        this.previousBlock = previous.getCurrentBlock();
        findHashWithLeadingZeros();

        executionTime = (System.nanoTime() - start) / nanoSecondsDivider;

    }
}
