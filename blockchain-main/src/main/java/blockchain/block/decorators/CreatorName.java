package blockchain.block.decorators;
import lombok.NoArgsConstructor;
import blockchain.block.SimpleBlock;

@NoArgsConstructor
public class CreatorName extends BlockDecorator {
    private String msg;
    private static final double serialVersionUID = 47L;

    public CreatorName(String msg, SimpleBlock block) {
        super(block);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return block + msg + "\n";
    }
}
