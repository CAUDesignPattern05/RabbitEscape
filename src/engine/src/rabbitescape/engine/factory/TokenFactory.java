package rabbitescape.engine.factory;

import rabbitescape.engine.Token;
import rabbitescape.engine.factory.Factory;

public class TokenFactory implements Factory<Token> {
    @Override
    public Token create(char c, int x, int y, Object... args) {
        switch (c) {
            case 'b': return new Token(x, y, Token.Type.bash);
            case 'd': return new Token(x, y, Token.Type.dig);
            case 'i': return new Token(x, y, Token.Type.bridge);
            case 'k': return new Token(x, y, Token.Type.block);
            case 'c': return new Token(x, y, Token.Type.climb);
            case 'p': return new Token(x, y, Token.Type.explode);
            case 'l': return new Token(x, y, Token.Type.brolly);
            default: throw new IllegalArgumentException("Unknown token character: " + c);
        }
    }
}
