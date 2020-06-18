package model;

public class BlockFabricV1 implements AbstractBlockFabric {
    @Override
    public Block createFirstBlock() throws NotImplementedException {
        return new Block();
    }

    @Override
    public Block createNextBlock(Block previous) throws NotImplementedException {
        return new Block(previous);
    }
}
