package scanner;

import java.util.HashMap;
import java.util.Stack;

public class Scanner {
  //Hash Maps representing the classifer, transition table, & token table
  HashMap<Character, String> m_classifier = new HashMap<Character, String>(); //character --> category
  HashMap<String, HashMap<String, String>> m_transitionTable = new HashMap<String, HashMap<String, String>>();
  HashMap<String, String> m_tokenTable = new HashMap<String, String>();
  Stack<String> m_stateStack = new Stack<>();

  public Scanner(TableReader tableReader) {
    // Build catMap, mapping a character to a category.
    for (TableReader.CharCat cat : tableReader.getClassifier()) {
      m_classifier.put(cat.getC(), cat.getCategory());
    }

    // Build the transition table. Given a state and a character category, give a new state
    for (TableReader.Transition t : tableReader.getTransitions()) {
      HashMap<String, String> nestedHash = new HashMap<String, String>();

      if(m_transitionTable.get(t.getFromStateName()) == null) nestedHash.put(t.getCategory(), t.getToStateName());
      else {
        nestedHash = m_transitionTable.get(t.getFromStateName());
        nestedHash.put(t.getCategory(), t.getToStateName());
      }
      m_transitionTable.put(t.getFromStateName(), nestedHash);
    }

    // Build the token types table
    for (TableReader.TokenType tt : tableReader.getTokens()) {
      m_tokenTable.put(tt.getState(), tt.getType());
      System.out.println("State " + tt.getState()
              + " accepts with the lexeme being of type " + tt.getType());
    }
  }
  
  //returns the category or "not in alphabet" based on the character passed in
  public String getCategory(Character c) {
    if(m_classifier.get(c) == null) return "not in alphabet"; //if the result of g_catMap.get(c) is null, return the specified string
    return m_classifier.get(c);
  }

  //given the starting state and the category, return the new state
  public String getNewState(String state, String category) {
    if(m_transitionTable.get(state).get(category) == null) return "error";
    return m_transitionTable.get(state).get(category);
  }

  /**
   * Returns the type of token corresponding to a given state. If the state
   * is not accepting then return "error".
   * Do not hardcode any state names or token types.
   */
  public String getTokenType(String state) {
    if(m_tokenTable.get(state) == null) return "error";
    return m_tokenTable.get(state);
  }

  //------------------------------------------------------------
  // TODO: implement nextToken
  //------------------------------------------------------------

  /**
   * Return the next token or null if there's a lexical error.
   */
  public Token nextToken(ScanStream ss) {
    // TODO: get a single token. This is an implementation of the nextToken
    // algorithm given in class. You may *not* use TableReader in this
    // function. Return null if there is a lexical error.
    String state = "s0";
    String lexeme = "";
    m_stateStack.clear();
    m_stateStack.push("bad");
    while(!state.equals("error")) {
      char c = ss.next();
      lexeme = lexeme + c;
    }
    return null;
  }

}
