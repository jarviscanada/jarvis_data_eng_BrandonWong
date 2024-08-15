package ca.jrvs.apps.practice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImp implements JavaGrep {

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {

    }

    @Override
    public void process() throws IOException {

    }

    @Override
    public List<File> listFiles(String rootDir) {
        return new ArrayList<File>();
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        return new ArrayList<String>();
    }

    @Override
    public boolean containsPattern(String line) {
        return false;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

    }

    @Override
    public String getRootPath() {
        return "";
    }

    @Override
    public void setRootPath(String rootPath) {

    }

    @Override
    public String getRegex() {
        return "";
    }

    @Override
    public void setRegex(String regex) {

    }

    @Override
    public String getOutFile() {
        return "";
    }

    @Override
    public void setOutFile(String outFile) {

    }
}
