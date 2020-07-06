package model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ComplexityChange extends BlockDecorator{
    private String msg;
    private static final double serialVersionUID = 46L;

    public ComplexityChange(String msg, SimpleBlock block) {
        super(block);
        this.msg = msg;
    }

    @Override
    public String toString() { return block + msg;
    }
}
