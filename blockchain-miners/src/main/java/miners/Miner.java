package miners;

import blockchain.Blockchain;
import blockchain.block.BlockBuilder;
import blockchain.block.SimpleBlock;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Miner implements Runnable {
    private blockchain.Blockchain blockchain;
    private String name;

    public Miner(String name) {
        this.name = name;
        this.blockchain = Blockchain.getInstance();
        log.trace(String.format("Created new worker %s on thread %s",
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
               log.error(String.format("Exception occured on thread %s for %s", Thread.currentThread().getName(), name));
            }
            log.info(String.format("Miner %s created %s", name, block));
        }
    }
}
