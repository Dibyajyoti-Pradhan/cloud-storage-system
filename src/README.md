# Cloud Storage System

A Java-based cloud storage system that allows users to manage files and directories, perform file operations, and handle compression and decompression of files. The system supports multiple users with individual storage capacities and enforces file ownership and access permissions.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Usage](#usage)
  - [Building the Project](#building-the-project)
  - [Running Tests](#running-tests)
  - [Running the Application](#running-the-application)
  - [Modifying the Main Class](#modifying-the-main-class)

## Features

- File Management: Add, copy, and delete files within the cloud storage system.
- User Management: Create users with specific storage capacities.
- File Ownership: Enforce file ownership and permissions.
- Compression: Compress and decompress files to manage storage space.
- Validation: Ensure file names and user inputs meet specified criteria.
- Testing: Comprehensive unit tests using JUnit 5 and Maven.

## Prerequisites

- Java Development Kit (JDK): Version 17 or higher.
- Apache Maven: Version 3.6.0 or higher.

## Installation

1. Clone the Repository

```
git clone https://github.com/Dibyajyoti-Pradhan/cloud-storage-system.git
```

2. Navigate to the Project Directory

```
cd cloud-storage-system
```

3. Verify Java and Maven Installation

Ensure that Java and Maven are installed and available in your system's PATH.

```
java -version
mvn -version
```

## Project Structure

```
cloud-storage-system/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/dibyajyoti/cloudstorage/
│   │           ├── CloudStorage.java
│   │           ├── CloudStorageImpl.java
│   │           ├── Main.java
│   │           ├── models/
│   │           │   ├── DirectoryNode.java
│   │           │   ├── FileNode.java
│   │           │   ├── Node.java
│   │           │   └── User.java
│   │           └── utils/
│   │               └── FileCompressor.java
│   └── test/
│       └── java/
│           └── com/dibyajyoti/cloudstorage/
│               └── CloudStorageTest.java
```

## Usage

### Building the Project

Compile the project using Maven:

```
mvn clean compile
```

### Running Tests

Execute the unit tests to ensure everything is working correctly:

```
mvn test
```

All tests should pass, indicating that the system functions as expected.

### Running the Application

You can run the application using the exec plugin in Maven. The Main class provides a simple demonstration of the system's functionality:

Sample Output:

```yaml
File size: 1000
User1 remaining capacity: 4000
After compression, User1 remaining capacity: 4500
Compressed file size: 500
```

### Modifying the Main Class

You can modify the Main.java class to experiment with different functionalities.
