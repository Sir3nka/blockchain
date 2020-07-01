package model;

public class BlockFabricV1 implements BlockFabric {
     
    public BlockFabricV1() {
    }

    @Override
    public Block createFirstBlock() throws NotImplementedException {
        return new Block();
    }

    @Override
    public Block createNextBlock(Block previous) throws NotImplementedException {
        if (previous == null) {
            return new Block();
        }
        return new Block(previous);
    }
}
