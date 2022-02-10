/*
 * Do not modify this file.
 */
package scanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TableParser provides all functionality for parsing the table definition file.
 *
 * Do not modify this file.
 */
public final class TableReader {

  // CharCat class
  // Used in the classifier table
  public class CharCat {

    final private char c;
    final private String category;

    public CharCat(char c, String category) {
      this.c = c;
      this.category = category;
    }

    public char getC() {
      return c;
    }

    public String getCategory() {
      return category;
    }

  }

  // Transition class
  // Used in the transition table
  public class Transition {

    final private String fromState;
    final private String category;
    final private String toState;

    public Transition(String fromState, String category, String toState) {
      this.fromState = fromState;
      this.category = category;
      this.toState = toState;
    }

    public String getFromStateName() {
      return fromState;
    }

    public String getCategory() {
      return category;
    }

    public String getToStateName() {
      return toState;
    }
  }

  // TokenType class
  // Used in the token type table
  public class TokenType {

    final private String state;
    final private String type;

    public TokenType(String state, String type) {
      this.state = state;
      this.type = type;
    }

    public String getState() {
      return state;
    }

    public String getType() {
      return type;
    }
  }

  private enum WhichTable {

    CLASS, TRANS, TOKEN
  };

  private ArrayList<CharCat> classifier;
  private ArrayList<Transition> transitions;
  private ArrayList<TokenType> tokens;

  public TableReader(String filename) throws IOException {
    parse(filename);
  }

  public Iterable<CharCat> getClassifier() {
    return classifier;
  }

  public Iterable<Transition> getTransitions() {
    return transitions;
  }

  public Iterable<TokenType> getTokens() {
    return tokens;
  }

  public void parse(String fn) throws FileNotFoundException, IOException {
    this.classifier = new ArrayList<>();
    this.transitions = new ArrayList<>();
    this.tokens = new ArrayList<>();

    WhichTable table = WhichTable.CLASS;
    try (BufferedReader br = new BufferedReader(new FileReader(fn))) {
      String line;
      while ((line = br.readLine()) != null) {
        final int idx = line.indexOf("//");
        if (idx > -1) {
          line = line.substring(0, idx);
        }
        line = line.trim();
        if (!line.isEmpty()) {
          if (line.equals("ClassifierTable")) {
            table = WhichTable.CLASS;
          } else if (line.equals("TransitionTable")) {
            table = WhichTable.TRANS;
          } else if (line.equals("TokenTypeTable")) {
            table = WhichTable.TOKEN;
          } else {
            String[] lineTokens = line.split(" ");
            if (table == WhichTable.CLASS) {
              char c = lineTokens[0].charAt(0);
              if (lineTokens[0].equals("\\space")) {
                c = ' ';
              } else if (lineTokens[0].equals("\\t")) {
                c = '\t';
              } else if (lineTokens[0].equals("\\n")) {
                c = '\n';
              }
              CharCat cc = new CharCat(c, lineTokens[1]);
              classifier.add(cc);
            } else if (table == WhichTable.TRANS) {
              Transition t = new Transition(lineTokens[0], lineTokens[1], lineTokens[2]);
              transitions.add(t);
            } else if (table == WhichTable.TOKEN) {
              TokenType tt = new TokenType(lineTokens[0], lineTokens[1]);
              tokens.add(tt);
            }
          }
        }
      }
    }
  }
}
