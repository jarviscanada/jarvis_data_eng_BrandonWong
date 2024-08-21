package ca.jrvs.apps.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

    static final Logger logger = LoggerFactory.getLogger(RegexExcImp.class);

    public static void main(String[] args) {
        RegexExcImp regex = new RegexExcImp();

        logger.info("{}", regex.matchJpeg("hello.jpg"));
        logger.info("{}", regex.matchJpeg("hello.jpeg"));
        logger.info("{}", regex.matchJpeg("hello.jg"));
        logger.info("{}", regex.matchJpeg("hello.png"));
        logger.info("{}", regex.matchJpeg("hello"));
        logger.info("{}", regex.matchIp("0.0.0.0"));
        logger.info("{}", regex.matchIp("999.999.999.999"));
        logger.info("{}", regex.matchIp("0.0.0.0.0"));
        logger.info("{}", regex.matchIp("0000.000.000.000"));
        logger.info("{}", regex.matchIp("120.0.0.0"));
        logger.info("{}", regex.matchIp("0.000.0.0"));
        logger.info("{}", regex.isEmptyLine(""));
        logger.info("{}", regex.isEmptyLine("a"));

    }
    @Override
    public boolean matchJpeg(String filename) {
        return Pattern.matches(".+(\\.(jpg|jpeg))$", filename);
    }
    @Override
    public boolean matchIp(String ip) {
        return Pattern.matches("(([0-9]){1,3}+\\.){3}([0-9]{1,3})", ip);
    }
    @Override
    public boolean isEmptyLine(String line) {
        return Pattern.matches("^\\s*$", line);
    }
}
