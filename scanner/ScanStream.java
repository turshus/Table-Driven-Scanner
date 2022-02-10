/*
 * DO NOT MODIFY THIS FILE
 */
package scanner;

/**
 * Abstracts a stream reader that reads one byte at a time with possible
 * rollback.
 *
 * Do not modify this file.
 */
public final class ScanStream {

  final private byte[] data;
  private int i;

  public ScanStream(byte[] data) {
    this.data = data;
    i = 0;
  }

  public boolean eof() {
    return i >= data.length;
  }

  public char next() {
    if (eof()) {
      throw new RuntimeException("EOF reached");
    }
    return (char) data[i++];
  }

  void rollback() {
    --i;
  }
}
