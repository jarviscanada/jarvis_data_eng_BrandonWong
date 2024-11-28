package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  static final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) throws IOException, IllegalArgumentException {
    if (args.length != 3) {
      IllegalArgumentException illegalArgumentException =
          new IllegalArgumentException("JavaGrep regex rootPath outFile");
      logError(
          "IllegalArgumentException", "main", "'provided wrong inputs'", illegalArgumentException);
      throw illegalArgumentException;
    }

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    javaGrepImp.process();
  }

  public static void logError(
      String errorName, String methodName, String reason, Exception exception) {
    logger.error("Type Method Reason: {} {} {}", errorName, methodName, reason, exception);
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    for (File file : listFiles(getRootPath())) {
      for (String line : readLines(file)) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File directory = new File(rootDir);
    File[] files = directory.listFiles();
    if (files == null) {
      return new ArrayList<>();
    }
    return Arrays.asList(files);
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
    if (inputFile == null) {
      IllegalArgumentException illegalArgumentException =
          new IllegalArgumentException("missing an input file");
      logError(
          "IllegalArgumentException", "readLines", "'inputFile is null'", illegalArgumentException);
      throw illegalArgumentException;
    }
    BufferedReader reader;
    List<String> lines = new ArrayList<>();
    try {
      reader = new BufferedReader(new FileReader(inputFile));

      String line = reader.readLine();

      while (line != null) {
        lines.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException ioException) {
      logError("IOException", "readLines", "'BufferedReader encountered an error'", ioException);
      throw ioException;
    }

    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(getRegex(), line);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(getOutFile()));
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
      writer.close();
    } catch (IOException ioException) {
      logError("IOException", "writeToFile", "'BufferedWriter encountered an error'", ioException);
      throw ioException;
    }
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
