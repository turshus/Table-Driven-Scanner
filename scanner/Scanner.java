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

  public String getTokenType(String state) {
    if(m_tokenTable.get(state) == null) return "error";
    return m_tokenTable.get(state);
  }

  /**
   * Return the next token or null if there's a lexical error.
   */
  public Token nextToken(ScanStream ss) {
    String state = "s0";
    String lexeme = "";
    m_stateStack.clear();
    m_stateStack.push("bad");
    while(!state.equals("error")) {
      char c = '[';
      try{
        c = ss.next();
      } catch(RuntimeException e) {
        System.out.println("ERROR ERROR");
        System.out.println(e.toString());
      }
      
      lexeme = lexeme + c;
      if(m_tokenTable.containsKey(state)) m_stateStack.clear();
      m_stateStack.push(state);
      String category = m_classifier.get(c);
      if(m_transitionTable.get(state).get(category) == null) state = "error";
      else state = m_transitionTable.get(state).get(category);
    }
    
    while(!m_tokenTable.containsKey(state) && !state.equals("bad")) {
      state = m_stateStack.pop();
      if(lexeme.length() > 0) lexeme = lexeme.substring(0, lexeme.length() - 1);
      ss.rollback();
    }

    if(m_tokenTable.containsKey(state)) return new Token(m_tokenTable.get(state), lexeme);
    return null;
  }

}
