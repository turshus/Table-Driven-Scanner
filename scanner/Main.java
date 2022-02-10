/*
 * CS 4481 Compilers
 * Project 1 - scanner part 1
 *
 * This file contains the method runTests(). You will uncomment tests
 * as you develop your scanner code
 */
package scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A different Main file will be used for grading, so you may make modifications as needed, but before
 * submitting you should download a fresh copy of this file, uncomment the tests and make sure you pass
 * them all.
 */
public class Main {

  public static void main(String[] args) throws FileNotFoundException, IOException {
    if (args.length > 0) {
      scanInputFile(args[0], args[1]);
    } else {
      runRegisterTests();
      runHexTests();

      System.out.println(tests.getSuccesses() + "/" + tests.getN()
              + " tests succeeded");
      int failures = tests.getFailures();

      System.exit(failures);
    }
  }

  private static void runRegisterTests() throws FileNotFoundException, IOException {
    // TODO: uncomment tests as you develop code

    //------------------------------------------------------------
    // Test register.table reading, table building and token
    // parsing. These tests should pass once you have the Scanner
    // constructor implemented.
    //------------------------------------------------------------

    String tableFile = "data/register.table";
    TableReader tableReader = new TableReader(tableFile);
    Scanner scanner = new Scanner(tableReader);

   tests.test(scanner.getCategory('r').equals("register"));
   tests.test(scanner.getCategory('3').equals("digit"));
   tests.test(scanner.getCategory('8').equals("digit"));
   tests.test(scanner.getCategory(' ').equals("whitespace"));
   tests.test(scanner.getCategory('x').equals("not in alphabet"));

   tests.test(scanner.getNewState("s0", "register").equals("s1"));
   tests.test(scanner.getNewState("s1", "digit").equals("s2"));
   tests.test(scanner.getNewState("s2", "digit").equals("s2"));
   tests.test(scanner.getNewState("s0", "whitespace").equals("s3"));
   tests.test(scanner.getNewState("s0", "digit").equals("error"));
   tests.test(scanner.getNewState("s1", "register").equals("error"));
   tests.test(scanner.getNewState("s2", "register").equals("error"));

   tests.test(scanner.getTokenType("s0").equals("error"));
   tests.test(scanner.getTokenType("s1").equals("error"));
   tests.test(scanner.getTokenType("s2").equals("register"));
   tests.test(scanner.getTokenType("s3").equals("ignore"));

    //------------------------------------------------------------
    // The following tests should pass once you have
    // Scanner.nextToken() implemented.
    //------------------------------------------------------------
    {
      ScanStream ss = getDataStream("data/test1-reg.txt");
//      testToken(scanner, ss, "register", "r302");
//      testToken(scanner, ss, "ignore");
//      testToken(scanner, ss, "register", "r02");
//      testToken(scanner, ss, "ignore");
//      testToken(scanner, ss, "register", "r233987");
    }
//    {
//      ScanStream ss = getDataStream("data/err1-reg.txt");
//      testToken(scanner, ss, null);
//    }
//    {
//      ScanStream ss = getDataStream("data/err2-reg.txt");
//      testToken(scanner, ss, null);
//    }
//    {
//      ScanStream ss = getDataStream("data/err3-reg.txt");
//      testToken(scanner, ss, "register", "r33");
//      testToken(scanner, ss, null);
//    }
  }

  private static void runHexTests() throws FileNotFoundException, IOException {
    // TODO: uncomment tests as you develop code

    //------------------------------------------------------------
    // Test register.table reading, table building and token
    // parsing. These tests should pass once you have the Scanner
    // constructor implemented.
    //------------------------------------------------------------

    String tableFile = "data/hexadecimal.table";
    TableReader tableReader = new TableReader(tableFile);
    Scanner scanner = new Scanner(tableReader);

   tests.test(scanner.getCategory('x').equals("x"));
   tests.test(scanner.getCategory('0').equals("zero"));
   tests.test(scanner.getCategory('8').equals("digit"));
   tests.test(scanner.getCategory('B').equals("digit"));
   tests.test(scanner.getCategory(' ').equals("whitespace"));
   tests.test(scanner.getCategory('z').equals("not in alphabet"));

   tests.test(scanner.getNewState("s0", "zero").equals("s1"));
   tests.test(scanner.getNewState("s1", "x").equals("s2"));
   tests.test(scanner.getNewState("s2", "zero").equals("s3"));
   tests.test(scanner.getNewState("s2", "digit").equals("s3"));
   tests.test(scanner.getNewState("s3", "zero").equals("s3"));
   tests.test(scanner.getNewState("s3", "digit").equals("s3"));
   tests.test(scanner.getNewState("s0", "whitespace").equals("s4"));
   tests.test(scanner.getNewState("s4", "whitespace").equals("s4"));
   tests.test(scanner.getNewState("s0", "digit").equals("error"));
   tests.test(scanner.getNewState("s1", "zero").equals("error"));
   tests.test(scanner.getNewState("s2", "x").equals("error"));

   tests.test(scanner.getTokenType("s0").equals("error"));
   tests.test(scanner.getTokenType("s1").equals("error"));
   tests.test(scanner.getTokenType("s2").equals("error"));
   tests.test(scanner.getTokenType("s3").equals("hexnumber"));
   tests.test(scanner.getTokenType("s4").equals("ignore"));

    //------------------------------------------------------------
    // The following tests should pass once you have
    // Scanner.nextToken() implemented.
    //------------------------------------------------------------
//    {
//      ScanStream ss = getDataStream("data/test1-hex.txt");
//      testToken(scanner, ss, "hexnumber", "0x3F");
//      testToken(scanner, ss, "ignore");
//      testToken(scanner, ss, "hexnumber", "0x1234");
//      testToken(scanner, ss, "ignore");
//      testToken(scanner, ss, "hexnumber", "0x1234567890ABCDEF");
//    }
//    {
//      ScanStream ss = getDataStream("data/err1-hex.txt");
//      testToken(scanner, ss, null);
//    }
//    {
//      ScanStream ss = getDataStream("data/err2-hex.txt");
//      testToken(scanner, ss, null);
//    }
//    {
//      ScanStream ss = getDataStream("data/err3-hex.txt");
//      testToken(scanner, ss, "hexnumber", "0x3A");
//      testToken(scanner, ss, null);
//    }
  }

  //--------------------------------------------------------------------------------
  // Utility stuff
  // You can ignore code from here to the end of the file.
  //--------------------------------------------------------------------------------

  static Tests tests = new Tests();

  private static void testToken(Scanner scanner, ScanStream ss, String type, String lexeme) {
    try {
      Token t = scanner.nextToken(ss);
      if (type == null) {
        tests.test(t == null, "expected lexical error");
      } else {
        tests.test(t.getType().equals(type), t.getType() + " not equal to " + type);
      }

      if (lexeme != null) {
        tests.test(t.getLexeme().equals(lexeme), t.getLexeme() + " not equal to " + lexeme);
      }
    } catch(Exception e) {
      tests.addFailure();
    }
  }

  private static void testToken(Scanner scanner, ScanStream ss, String type) {
    testToken(scanner, ss, type, null);
  }

 private static ScanStream getDataStream(String testFile) throws IOException {
    File file = new File(testFile);
    FileInputStream fis = new FileInputStream(file);
    byte[] data = new byte[(int) file.length()];
    fis.read(data);
    fis.close();
    ScanStream ss = new ScanStream(data);
    return ss;
  }

  public static void scanInputFile(String tableFile, String testFile)
          throws FileNotFoundException, IOException {
    TableReader tableReader = new TableReader(tableFile);

    File file = new File(testFile);
    FileInputStream fis = new FileInputStream(file);
    byte[] data = new byte[(int) file.length()];
    fis.read(data);
    fis.close();

    PrintWriter writer = new PrintWriter(testFile + ".out", "UTF-8");

    Scanner scanner = new Scanner(tableReader);
    ScanStream ss = new ScanStream(data);
    Token t;
    do {
      t = scanner.nextToken(ss);
      if (t == null) {
        System.out.println("Lexical error: " + ss.next());
        ss.rollback();
        writer.println("Lexical error: " + ss.next());
      } else if (!t.getType().equals("ignore")) {
        System.out.println(t.toString());
        writer.println(t.toString());
      }
    } while (t != null && !ss.eof());

    writer.close();
  }


}
