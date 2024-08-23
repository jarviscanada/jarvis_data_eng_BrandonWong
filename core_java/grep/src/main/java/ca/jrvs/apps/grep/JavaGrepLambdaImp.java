package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class JavaGrepLambdaImp implements JavaGrep {

    static final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (IOException ioException) {
            logger.error("ERROR: IO Exception {}", ioException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("ERROR: Illegal Argument Exception {}", illegalArgumentException.getMessage());
        }
    }
    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        Stream<File> fileStream = listFiles(getRootPath()).stream();
        fileStream.forEach(file -> {
            Stream<String> lineStream = readLines(file).stream();
            lineStream.forEach(line -> {
               if (containsPattern(line)) matchedLines.add(line);
            });
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
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        if (inputFile == null) throw new IllegalArgumentException("ERROR: inputFile is null.");
        ArrayList<String> lines = new ArrayList<>();
        try (Stream<String> linesStream = Files.lines(inputFile.toPath())) {
            linesStream.forEach(lines::add);
        } catch (IOException ioException) {
            logger.error("ERROR: ReadLine method {}", ioException.getMessage());
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

        writer = new BufferedWriter(new FileWriter(getOutFile()));
        linesStream.forEach(line -> {
            try {
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
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
