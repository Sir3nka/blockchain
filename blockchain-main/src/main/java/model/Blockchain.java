package model;

import common.LoggerHandler;
import lombok.Getter;
import utils.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Blockchain implements Serializable {
    private static String FILE_NAME = "blockchain.ser";
    private static final double serialVersionUID = 42L;
    private static Blockchain instance;
    private volatile List<Block> blockchain;
    @Getter
    private volatile Block lastBlock;

    private Blockchain() {
        Block.setLeadingZeros(6);
        this.blockchain = new ArrayList<>();
    }

    public static synchronized Blockchain getInstance() {
        if (instance == null) {
            if(!deserialize()) {
                instance = new Blockchain();
            }
        }
        return instance;
    }

    private boolean verifyBlock(Block block) {
        LoggerHandler.logger.trace(String.format("Veryfing block.. %s", block));
        return lastBlock != null ?
                block.getPreviousHash().equals(lastBlock.getCurrentHash()) &&
                StringUtil.applySha256(block.getStringRepresentation()).equals(block.getCurrentHash()) &&
                Block.isMatchingRegex(block.getCurrentHash()) :
                block.getPreviousHash().equals("0") && StringUtil.applySha256(block.getStringRepresentation())
                .equals(block.getCurrentHash()) && Block.isMatchingRegex(block.getCurrentHash());
    }

    //TODO Implement me
    private boolean isBlockchainValid() {
        return true;
    }
    //TODO Implement me
    private void handleLeadingZeros() {

    }
    //Only one thread at a time can create blocks
    public synchronized void addBlock(Block block) throws IOException {
        if (verifyBlock(block)) {
            blockchain.add(block);
            lastBlock = block;
            LoggerHandler.logger.trace(String.format("Verified block, current hash %s", block.getCurrentHash()));
            serializeBlockchain();
            //TODO Serialize whole blockchain after succesfull verification of single node & whole blockchain
        } else {
            LoggerHandler.logger.error(String.format("Failed to verify %s", block));
        }
    }

    private void serializeBlockchain() throws IOException {
        File file = new File (FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(this);
                objectOutputStream.flush();
            }
        }
        LoggerHandler.logger.trace("Saved blockchain instance");
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
            Block.setLeadingZeros(6);
            return true;
        } else {
            LoggerHandler.logger.trace("Haven't found blockchain file, creating a new object...");
            return false;
        }
    }

    static class InvalidBlockException extends RuntimeException {
        InvalidBlockException(Block block) {
            super(String.format("%s is invalid", block));
        }
    }

}
