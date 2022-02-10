/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 * A test utility class.
 *
 * Do not modify this file.
 */
public class Tests {

  private int N = 0;
  private int failures = 0;

  public void test(boolean b) {
    N++;
    if (!b) {
      System.err.println("Failed test " + N);
      failures++;
    }
  }

  public void test(boolean b, String message) {
    test(b);
    if (!b) {
      System.err.println(message);
    }
  }

  public void addFailure() {
    N++;
    failures++;
  }

  public int getN() {
    return N;
  }

  public int getSuccesses() {
    return N - failures;
  }

  public int getFailures() {
    return failures;
  }
}
