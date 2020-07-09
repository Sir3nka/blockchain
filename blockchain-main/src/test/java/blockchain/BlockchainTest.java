package blockchain;
import blockchain.block.BlockBuilder;
import blockchain.block.SimpleBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@DisplayName("Blockchain test suite")
class BlockchainTest {
    private Blockchain blockchain;
    private final static String FILE_NAME = "blockchain.ser";

    @BeforeEach
    void getInstance() {
        File serialized = new File(FILE_NAME);
        if (serialized.exists()) {
            serialized.delete();
        }
        blockchain = Blockchain.getInstance();
    }

    @AfterEach
    void clear() {
        File serialized = new File(FILE_NAME);
        if (serialized.exists()) {
            serialized.delete();
        }
    }

    @Test
    @DisplayName("Adding two valid blocks")
    void twoValidBlocks() throws IOException {
        SimpleBlock block = BlockBuilder.buildNext(null).build();
        blockchain.addBlock(block);
        blockchain.addBlock(BlockBuilder.buildNext(blockchain.getLastBlock()).build());
        assert(blockchain.getBlockchain().size() == 2);
    }

    @Test
    @DisplayName("Adding same block")
    void twoSameBlocks() throws IOException {
        blockchain.addBlock(BlockBuilder.buildNext(null).build());
        blockchain.addBlock(BlockBuilder.buildNext(null).build());
        assert(blockchain.getBlockchain().size() == 1);
    }
}
