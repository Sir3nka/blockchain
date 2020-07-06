package model;

import common.LoggerHandler;

public class Miner implements Runnable {
    private Blockchain blockchain;
    private String name;

    public Miner(String name) {
        this.name = name;
        this.blockchain = Blockchain.getInstance();
        LoggerHandler.logger.trace(String.format("Created new worker %s on thread %s",
                name, Thread.currentThread().getName()));
    }

    private String generateMessage() {
        return "Hi im " + name;
    }

    @Override
    public void run() {
        while (true) {
            blockchain.addMessage(name, generateMessage());
            SimpleBlock block = BlockBuilder
                    .buildNext(blockchain.getLastBlock())
                    .addCreator(name)
                    .build();
            try {
                blockchain.addBlock(block);
            } catch (Exception ex) {
                System.out.printf("Exception occured on thread %s for %s", Thread.currentThread().getName(), name);
            }
            LoggerHandler.logger.info(String.format("Miner %s created %s", name, block));
        }
    }
}
