package blockchain.block;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import utils.StringUtil;

@EqualsAndHashCode
public class Block implements SimpleBlock {
    private static final double serialVersionUID = 42L;
    private static int leadingZeros = 0;
    @Getter
    private static Pattern regex;

    private static final double NANO_SECONDS_DIVIDER = 1_000_000_000.0;
    private static final int MIN_RANGE = 1000_0000;
    private static final int MAX_RANGE = 9999_9990;

    @Getter
    private final int id;
    private final long timeStamp;
    private int magicNumber;

    @Getter
    private final String previousHash;

    @Getter
    private String currentHash;

    @Getter
    private final double executionTime;

    public static void setLeadingZeros(int leadingZeros) {
        Block.leadingZeros = leadingZeros;
        createRegexForHashMatching();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Block: ").append("\n")
                .append("Id: ").append(id).append("\n")
                .append("Timestamp: ").append(timeStamp).append("\n")
                .append("Magic number: ").append(magicNumber).append("\n")
                .append("Hash of the previous block: \n").append(previousHash).append("\n")
                .append("Hash of the block: \n").append(currentHash).append("\n")
                .append("Block was generating for ").append(executionTime).append(" seconds\n");

        return builder.toString();
    }

     public String getStringRepresentation() {
        StringBuilder builder = new StringBuilder();

        builder.append(id)
                .append(timeStamp)
                .append(previousHash)
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
                .nextInt(MIN_RANGE, MAX_RANGE + 1);
        this.currentHash = StringUtil.applySha256(this.getStringRepresentation());

        while (!isMatchingLeadingZeros(currentHash)) {
            this.magicNumber = ThreadLocalRandom
                    .current()
                    .nextInt(MIN_RANGE, MAX_RANGE + 1);
            this.currentHash = StringUtil.applySha256(this.getStringRepresentation());
        }
    }

    public static boolean isMatchingLeadingZeros(String hash) {
        return regex.matcher(hash).find();
    }

    Block() {
        long start = System.nanoTime();
        this.id = 1;
        this.timeStamp = new Date().getTime();
        previousHash = "0";
        findHashWithLeadingZeros();
        executionTime = (System.nanoTime() - start) / NANO_SECONDS_DIVIDER;
    }

    Block(SimpleBlock previous) {
        long start = System.nanoTime();
        this.id = previous.getId() + 1;
        this.timeStamp = new Date().getTime();
        this.previousHash = previous.getCurrentHash();
        findHashWithLeadingZeros();
        executionTime = (System.nanoTime() - start) / NANO_SECONDS_DIVIDER;
    }
}
