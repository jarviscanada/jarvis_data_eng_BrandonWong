package ca.jrvs.apps.practice;

public interface RegexExc {
    /**
     * Match given filename with a jpeg format.
     * Ensure that filename ends with `.jpg` and `.jpeg`
     *
     * @param filename
     * @return a Jpeg match has been found
     */
    boolean matchJpeg(String filename);

    /**
     * Match given ip with a ip format.
     * Ensure that the ip is between 0.0.0.0 to 999.999.999.999
     *
     * @param ip
     * @return a valid ip has been given
     */
    boolean matchIp(String ip);

    /**
     * Match a given line with an empty line
     *
     * @parama line
     * @return an emptu line has been given
     */
    boolean isEmptyLine(String line);
}
