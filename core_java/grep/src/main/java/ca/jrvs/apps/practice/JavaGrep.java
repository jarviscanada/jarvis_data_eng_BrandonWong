package ca.jrvs.apps.practice;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * Executes the grep program line by line and returns the result to a file.
     * All information should be provided as an argument or a user prompt.
     *
     * @throws IOException file or directory provided has not been found
     */
    void process() throws IOException;

    /**
     * List all files given a root directory. It does not list files inside
     * nested directory.
     *
     * @param rootDir directory that resides all files to be selected
     * @return a list of files of class File
     */
    List<File> listFiles(String rootDir);

    /**
     * Read a file line-by-line and format each line as an element of a String array.
     *
     * @param inputFile file to be read line-by-line
     * @return a String array of each line
     * @throws IllegalArgumentException invalid file
     */
    List<String> readLines(File inputFile) throws IllegalArgumentException;

    /**
     * Verify that the line has a matching pattern. Ensure that the implementation
     * has a regex pattern to match the given line.
     *
     * @param line to match with the pattern
     * @return a boolean value if it is a valid match
     */
    boolean containsPattern(String line);

    /**
     * Write to a file line-by-line.
     *
     * @param lines a String array containing all matched lines
     * @throws IOException invalid file to write content
     */
    void writeToFile(List<String> lines) throws IOException;

    /**
     * Returns the root path where we would like to fetch all files
     *
     * @return a String value of the root path.
     */
    String getRootPath();

    /**
     * Set a new root path.
     *
     * @param rootPath a String value of the root path
     */
    void setRootPath(String rootPath);

    /**
     * Returns the regex pattern to match each line
     *
     * @return a String value of the regex pattern
     */
    String getRegex();

    /**
     * Set a new regex pattern.
     *
     * @param regex a String value of the regex pattern
     */
    void setRegex(String regex);

    /**
     * Returns the output file path and name (e.g. /out/output.txt).
     *
     * @return a String value of the output file path
     */
    String getOutFile();

    /**
     * Set a new output file path
     *
     * @param outFile a String value of the output file path
     */
    void setOutFile(String outFile);
}
