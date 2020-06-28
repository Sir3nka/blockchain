package model;

import lombok.Getter;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

//Singleton intance of blockchain
public class Blockchain {
    private static Blockchain instance;
    private volatile List<Block> blockchain;
    private BlockFabric blockFabric;
    @Getter
    private volatile Block lastBlock;

    private Blockchain() {
        this.blockchain = new ArrayList<>();
        this.blockFabric = BlockFabricV1.getInstance();
    }

    ;

    public static Blockchain getInstance() {
        if (instance == null) {
            instance = new Blockchain();
        }
        return instance;
    }


    //TODO CHECK IF MATCHES REGEX
    private boolean verifyBlock(Block block) {
        //Check if previous hash of blockchain is not changed && check if calculated hash matches one in object
        // && check hash against leading zeros
        return block.getPreviousBlock().equals(lastBlock.getPreviousBlock()) &&
                !StringUtil.applySha256(block.getStringRepresentation()).equals(block.getCurrentBlock());
    }

    private void handleLeadingZeros() {

    }

    //Only one thread at a time can create blocks
    public synchronized void acceptBlock(Block block) {
        if (verifyBlock(block)) {
            blockchain.add(block);
            lastBlock = block;
        } else {
            throw new InvalidBlockException(block);
        }
    }

    static class InvalidBlockException extends RuntimeException {
        InvalidBlockException(Block block) {
            super(String.format("%s is invalid", block.getStringRepresentation()));
        }
    }

}
