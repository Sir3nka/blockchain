package model;

import common.LoggerHandler;
import lombok.Getter;
import utils.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Blockchain implements Serializable {
    private static String FILE_NAME = "blockchain.ser";
    private static final double serialVersionUID = 42L;
    private static Blockchain instance;
    private List<SimpleBlock> blockchain;
    private Queue<String> messages;
    @Getter
    private volatile SimpleBlock lastBlock;
    private int lastN;

    private Blockchain() {
        lastN = 0;
        Block.setLeadingZeros(0);
        this.blockchain = new ArrayList<>();
        this.messages = new PriorityQueue<>();
    }

    public static synchronized Blockchain getInstance() {
        if (instance == null) {
            if (!deserialize()) {
                instance = new Blockchain();
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        blockchain.forEach(builder::append);
        return builder.toString();
    }

    public synchronized void addBlock(SimpleBlock block) throws IOException {
        if (verifyBlock(block)) {
            SimpleBlock newBlock = BlockBuilder
                    .builder(block)
                    .addMsg(getMessages())
                    .addComplexity(adjustComplexityLevel(block.getExecutionTime()))
                    .build();
            blockchain.add(newBlock);
            lastBlock = newBlock;
            LoggerHandler.logger.trace(String.format("Verified block, current hash %s", block.getCurrentHash()));
            serializeBlockchain();
        } else {
            LoggerHandler.logger.error(String.format("Failed to verify %s", block));
        }
    }

    public synchronized void addMessage(String sender, String... msg) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("[Sender %s]:", sender));
        for (String var : msg) {
            builder.append(var);
        }
        messages.add(builder.toString());
    }

    private String getMessages() {
        StringBuilder builder = new StringBuilder();
        while(!messages.isEmpty()) {
            builder.append(messages.poll()).append("\n");
        }
        return builder.toString();
    }

    private String adjustComplexityLevel(double execTime) {
        if (execTime > 60) {
            Block.setLeadingZeros(--lastN);
            return ("Complexity was increased to " + lastN );
        } else {
            Block.setLeadingZeros(++lastN);
            return ("Complexity was decreased to " + lastN );
        }
    }

    private void serializeBlockchain() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(this);
                objectOutputStream.flush();
            }
        }
        LoggerHandler.logger.trace("Saved blockchain instance" + this);
    }

    private static boolean deserialize() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            LoggerHandler.logger.trace("Deserialize blockchain...");
            try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME)) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                    instance = (Blockchain) objectInputStream.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            LoggerHandler.logger.trace("Successfully loaded blockchain");
            Block.setLeadingZeros(instance.lastN);
            return true;
        } else {
            LoggerHandler.logger.trace("Haven't found blockchain file, creating a new object...");
            return false;
        }
    }

    private boolean verifyBlock(SimpleBlock block) {
        LoggerHandler.logger.trace(String.format("Veryfing block.. %s", block));
        return lastBlock != null ?
                block.getPreviousHash().equals(lastBlock.getCurrentHash()) &&
                        StringUtil.applySha256(block.getStringRepresentation()).equals(block.getCurrentHash()) &&
                        Block.isMatchingRegex(block.getCurrentHash()) :
                block.getPreviousHash().equals("0") && StringUtil.applySha256(block.getStringRepresentation())
                        .equals(block.getCurrentHash()) && Block.isMatchingRegex(block.getCurrentHash());
    }

    static class InvalidBlockException extends RuntimeException {
        InvalidBlockException(Block block) {
            super(String.format("%s is invalid", block));
        }
    }

}
