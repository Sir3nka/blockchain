package model;

import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
public class CreatorName extends BlockDecorator implements Serializable {
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
