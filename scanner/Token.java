/*
 * DO NOT MODIFY THIS FILE
 */
package scanner;

/**
 * A class that stores a token.
 *
 * Do not modify this file.
 */
public class Token {

  private final String type;
  private final String lexeme;

  public Token(String type, String lexeme) {
    this.type = type;
    this.lexeme = lexeme;
  }

  public String toString() {
    return type + ": " + lexeme;
  }

  public String getType() {
    return type;
  }

  public String getLexeme() {
    return lexeme;
  }

}
