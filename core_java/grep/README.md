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

## Performance Issue

# Test

# Deployment

# Improvement
