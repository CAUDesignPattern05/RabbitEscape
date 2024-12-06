package rabbitescape.engine.factory;

import rabbitescape.engine.Token;

public class TokenFactory implements Factory<Token> {
    public enum TokenType {
        BASH('b') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.bash);
            }
        },
        DIG('d') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.dig);
            }
        },
        BRIDGE('i') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.bridge);
            }
        },
        BLOCK('k') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.block);
            }
        },
        CLIMB('c') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.climb);
            }
        },
        EXPLODE('p') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.explode);
            }
        },
        BROLLY('l') {
            @Override
            public Token create(int x, int y) {
                return new Token(x, y, Token.Type.brolly);
            }
        };

        private final char symbol;

        TokenType(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

        public abstract Token create(int x, int y);

        public static TokenType fromChar(char c) {
            for (TokenType type : values()) {
                if (type.symbol == c) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown token character: " + c);
        }
    }

    @Override
    public Token create(char c, int x, int y, Object... args) {
        return TokenType.fromChar(c).create(x, y);
    }
}
