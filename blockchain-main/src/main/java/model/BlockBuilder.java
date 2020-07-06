package model;

public class BlockBuilder {
    private SimpleBlock block;

    private BlockBuilder(SimpleBlock block) {
        this.block = block;
    }

    public static BlockBuilder builder(SimpleBlock block) {
        return new BlockBuilder(block);
    }

    public static BlockBuilder buildNext(SimpleBlock previous) {
        if (previous == null) {
            return new BlockBuilder(new Block());
        }
        return new BlockBuilder(new Block(previous));
    }

    public SimpleBlock build() {
        return block;
    }

    public BlockBuilder addMsg(String msg) {
        block = new ChatHistory(msg, block);
        return this;
    }

    public BlockBuilder addCreator(String msg) {
        block = new CreatorName(msg, block);
        return this;
    }

    public BlockBuilder addComplexity(String msg) {
        block = new ComplexityChange(msg, block);
        return this;
    }
}
