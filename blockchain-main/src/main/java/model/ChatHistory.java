package model;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class ChatHistory extends BlockDecorator{
    private String history;
    private static final double serialVersionUID = 45L;

    public ChatHistory(String history, SimpleBlock block) {
        super(block);
        this.history = history;
    }
    @Override
    public String toString() { return block + history;
    }
}
