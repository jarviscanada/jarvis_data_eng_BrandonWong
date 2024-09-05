# Introduction
This project is a Java application that allows users to fetch matching lines given a regex pattern. It takes in a directory and parses through each file line-by-line and returns each matched line to an output file. It mimics the functionality of grep written by GNU, and the Java implementation allows developers to run this program on any machine through the JVM.

## Technologies
- **Java:** Write the application to perform the grep functionality. It also provides the library to handle IO operations and pattern matching operations.
- **Maven:** Compile the application and append Apache libraries to the project.
- **Docker:** Wrap the Java application in a container to run the program in isolation.
- **Git:** Keep track of project progress

# Quick Start
To start using the application
1. Clone the repository
```
cd $HOME
git clone https://github.com/jarviscanada/jarvis_data_eng_BrandonWong.git
cd $HOME/jarvis_data_eng_BrandonWong/core_java/grep
```
2. Package the Java Grep application
```
mvn clean package
```
3. Login to Docker Hub
```
docker_user=<docker-id>
docker login -u ${docker_user} --password-stdin
```
4. Build a new Docker image
```
docker build -t ${docker_user}/grep .
docker image ls | grep "grep" # To verify if image exists
```
5. Run Docker image
```
docker run --rm \
-v `pwd`/data:/data -v `pwd/log:/log \
${docker_user}/grep <pattern> <input-directory> <output-file-path>
```

# Implementation
## Pseudocode
The program takes in as input: the `pattern`, `rootDir` and `outputFilePath` and returns a file in `outputFilePath` with each matched line in files under `rootDir` that satisfies `pattern`. It is able to achieve this functionality through a nested loop of all lines of each file under `rootDir`.
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

**Note: the project consists of `grep` and `practice` where `grep` consists of the actual implementation and `practice` is the exercises for Regex and Stream/Lambda.**
## Performance Issue
The implementation requires all files to be loaded before checking line by line. This design can exceed the allocated memory in the garbage collector and stop the program. Furthermore, the program parses through each file one-by-one. The linear approach affects the time complexity to be related to the number of total lines of all files combined.

The first issue can be resolved by extending the memory usage for the JVM on compile-time. It can also be resolve by loading one file at a time to reduce the program to fill up the heap before it actually needs the data.

The second issue can be resolved using `java.util.Stream` which allows parallelized aggregate operations that can distribute the pattern matching to multiple threads. This implementation will allow the program to parse through several files at the same time.

# Test
Unit testing using **JUnit 4** to test each method in isolation. The unit testing layer was implemented under `src/test/` where all data sources are stored under `resources` and where all the actual test cases are written under `java/ca/jrvs/apps`. Each test case is written to test a single method in isolation or after all its dependencies have been tested.

# Deployment
The application is packaged using **Maven** and containerized using **Docker**. We compile the entire program and it's related Apache libraries as a `.jar` file using **Maven** and we containerized using **Docker** to allow the user to run the application without any prior dependencies or installations.

# Improvement
- Load a single file at a time to process pattern matching. This improvement will allow the heap size to be dedicated to memory usage on the current operation for each file.
- Add logger output file that tracks the progress of the program.
- Allow different types of output such that the user can store it based on their use case.
