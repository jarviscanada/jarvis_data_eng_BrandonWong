package ca.jrvs.apps.grep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp implements JavaGrep {

  static final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);

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

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    javaGrepLambdaImp.process();
  }

  public static void logError(
      String errorName, String methodName, String reason, Exception exception) {
    logger.error("Type Method Reason: {} {} {}", errorName, methodName, reason, exception);
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    Stream<File> fileStream = listFiles(getRootPath()).stream();
    fileStream.forEach(
        file -> {
          try (Stream<String> lineStream = readLines(file).stream()) {
            lineStream.forEach(
                line -> {
                  if (containsPattern(line)) matchedLines.add(line);
                });
          } catch (IOException ioException) {
            logError("IOException", "process", "Stream encountered an error", ioException);
          }
        });
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File directory = new File(rootDir);
    File[] files = directory.listFiles();
    if (files == null) return new ArrayList<>();
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
    ArrayList<String> lines = new ArrayList<>();
    try (Stream<String> linesStream = Files.lines(inputFile.toPath())) {
      linesStream.forEach(lines::add);
    } catch (SecurityException securityException) {
      logError("SecurityException", "readLines", "Stream encountered an error", securityException);
      throw securityException;
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
    Stream<String> linesStream = lines.stream();
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(getOutFile()));
      linesStream.forEach(
          line -> {
            try {
              writer.write(line);
              writer.newLine();
            } catch (IOException exception) {
              logError(
                  "IOException",
                  "writeToFile",
                  "'BufferedWriter encountered an error while writing'",
                  exception);
              throw new RuntimeException(exception);
            }
          });
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
