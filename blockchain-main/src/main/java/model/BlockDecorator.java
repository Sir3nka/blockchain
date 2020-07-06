package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BlockDecorator implements SimpleBlock{
    protected SimpleBlock block;
    @Override
    public abstract String toString();
    @Override
    public String getStringRepresentation() { return block.getStringRepresentation();}
    @Override
    public String getCurrentHash() { return block.getCurrentHash();}
    @Override
    public String getPreviousHash() { return block.getPreviousHash();}
    @Override
    public double getExecutionTime(){ return block.getExecutionTime();}
    @Override
    public int getId(){ return block.getId();}
}
