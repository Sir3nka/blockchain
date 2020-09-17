package blockchain.block.decorators;
import lombok.NoArgsConstructor;
import blockchain.block.SimpleBlock;

@NoArgsConstructor
public class ChatHistory extends BlockDecorator {

    private final String PREFIX = "Block chat\n";
    private String history;
    private static final double serialVersionUID = 45L;

    public ChatHistory(String history, SimpleBlock block) {
        super(block);
        if (history.isEmpty()) this.history = "no messages";
        else this.history = history;
    }

    @Override
    public String toString() { return block + PREFIX + history;
    }
}
