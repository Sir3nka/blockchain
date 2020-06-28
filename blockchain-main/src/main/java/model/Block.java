package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import utils.StringUtil;

@EqualsAndHashCode
public final class Block {
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

    public static void setLeadingZeros(int leadingZeros) {
        Block.leadingZeros = leadingZeros;
        createRegexForHashMatching();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Block: \n")
                .append("Id: ").append(id).append("\n")
                .append("Timestamp: ").append(timeStamp).append("\n")
                .append("Magic number: ").append(magicNumber).append("\n")
                .append("Hash of the previous block: \n").append(previousBlock).append("\n")
                .append("Hash of the block: \n").append(currentBlock).append("\n")
                .append("Block was generating for ").append(executionTime).append(" seconds\n");

        return builder.toString();
    }

     String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();

        builder.append(id)
                .append(timeStamp)
                .append(previousBlock)
                .append(magicNumber);

        return builder.toString();
    }

    private static void createRegexForHashMatching() {
        StringBuilder regexBuilder = new StringBuilder();
        regexBuilder.append("^");

        IntStream.range(0, leadingZeros)
                .forEach(num -> regexBuilder.append("0"));

        regex = Pattern.compile(regexBuilder.toString());
    }

    private void findHashWithLeadingZeros() {
        this.magicNumber = ThreadLocalRandom.current()
                .nextInt(min, max + 1);
        this.currentBlock = StringUtil.applySha256(this.getStringRepresentation());

        while (!regex.matcher(currentBlock).find()) {
            this.magicNumber = ThreadLocalRandom
                    .current()
                    .nextInt(min, max + 1);
            this.currentBlock = StringUtil.applySha256(this.getStringRepresentation());
        }
    }

    Block() {
        long start = System.nanoTime();

        this.id = 1;
        this.timeStamp = new Date().getTime();
        previousBlock = "0";
        findHashWithLeadingZeros();

        executionTime = (System.nanoTime() - start) / nanoSecondsDivider;
    }

    Block(Block previous) {
        long start = System.nanoTime();

        this.id = previous.getId() + 1;
        this.timeStamp = new Date().getTime();
        this.previousBlock = previous.getCurrentBlock();
        findHashWithLeadingZeros();

        executionTime = (System.nanoTime() - start) / nanoSecondsDivider;

    }
}
