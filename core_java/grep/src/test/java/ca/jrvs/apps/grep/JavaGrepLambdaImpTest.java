package ca.jrvs.apps.grep;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImpTest {

  static final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImpTest.class);
  private final String resourcePath = "src/test/resources/data/";

  JavaGrepLambdaImp javaGrepImp;

  @Before
  public void setup() {
    javaGrepImp = new JavaGrepLambdaImp();
  }

  @After
  public void remove() {
    File directory = new File(resourcePath + "out");
    for (File file : Objects.requireNonNull(directory.listFiles())) {
      if (file == null) continue;
      if (file.delete()) {
        logger.info("{} is deleted", file.getName());
      } else {
        logger.info("{} is not deleted", file.getName());
      }
    }
  }

  @Test
  public void listFilesOnExistingDirectory() {
    List<File> listedFiles = javaGrepImp.listFiles(resourcePath + "txt");
    assertEquals(2, listedFiles.size());
  }

  @Test
  public void listFilesOnEmptyDirectory() {
    List<File> listedFiles = javaGrepImp.listFiles(resourcePath + "empty");
    assertEquals(0, listedFiles.size());
  }

  @Test
  public void listFilesOnNonExistingDirectory() {
    List<File> listedFiles = javaGrepImp.listFiles(resourcePath + "test");
    assertEquals(0, listedFiles.size());
  }

  @Test
  public void listFilesOnFilepath() {
    List<File> listedFiles = javaGrepImp.listFiles(resourcePath + "txt/valid_file.txt");
    assertEquals(0, listedFiles.size());
  }

  @Test
  public void readLinesFromExistingFile() throws IOException {
    File file = new File(resourcePath + "txt/valid_file.txt");
    List<String> lines = javaGrepImp.readLines(file);
    assertEquals(6, lines.size());
  }

  @Test
  public void readLinesFromEmptyFile() throws IOException {
    File file = new File(resourcePath + "txt/empty_file.txt");
    List<String> lines = javaGrepImp.readLines(file);
    assertEquals(0, lines.size());
  }

  @Test(expected = IOException.class)
  public void readLinesFromNonExistingFile() throws IOException {
    File file = new File(resourcePath + "txt/test.txt");
    javaGrepImp.readLines(file);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readLinesFromNullFile() throws IOException {
    javaGrepImp.readLines(null);
  }

  @Test
  public void containsMatching() {
    javaGrepImp.setRegex("Hello World");
    assertTrue(javaGrepImp.containsPattern("Hello World"));
  }

  @Test
  public void containsNoMatching() {
    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex("a");
    assertFalse(javaGrepImp.containsPattern("Hello World"));
  }

  @Test
  public void writeToNewFile() throws IOException {
    List<String> lines = new ArrayList<>();
    lines.add("Hello, World");
    String outFile = resourcePath + "out/result.txt";
    javaGrepImp.setOutFile(outFile);
    javaGrepImp.writeToFile(lines);

    File file = new File(outFile);
    assertTrue(file.exists());
    assertEquals(1, javaGrepImp.readLines(file).size());
  }

  @Test
  public void writeToExistingFile() throws IOException {
    List<String> lines = new ArrayList<>();
    String outFile = resourcePath + "out/result.txt";
    javaGrepImp.setOutFile(outFile);
    javaGrepImp.writeToFile(lines);
    javaGrepImp.writeToFile(lines);

    File file = new File(outFile);
    assertTrue(file.exists());
  }

  @Test(expected = IOException.class)
  public void writeToDirectory() throws IOException {
    List<String> lines = new ArrayList<>();
    String outFile = resourcePath + "out/";
    javaGrepImp.setOutFile(outFile);
    javaGrepImp.writeToFile(lines);
  }
}
