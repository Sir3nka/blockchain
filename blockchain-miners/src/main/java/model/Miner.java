package model;

import common.LoggerHandler;

public class Miner implements Runnable {
    private Blockchain blockchain;
    private BlockFabric blockFabric;
    private String name;

    public Miner(String name) {
        this.name = name;
        this.blockchain = Blockchain.getInstance();
        this.blockFabric = new BlockFabricV1();
        LoggerHandler.logger.trace(String.format("Created new worker %s on thread %s",
                name, Thread.currentThread().getName()));
    }

    @Override
    public void run() {
        while (true) {
            Block block = blockFabric.createNextBlock(blockchain.getLastBlock());
            try {
                blockchain.addBlock(block);
            } catch (Exception ex) {
                System.out.printf("Exception occured on thread %s for %s", Thread.currentThread().getName(), name);
            }
            LoggerHandler.logger.info(String.format("Miner %s created block %s", name, block));
        }
    }
}
