# TransactionService
## Description
This is a simple application related to transaction management within a banking system.

Implemented the following API:

- Create transaction
- Delete transaction
- Modify transaction
- List all transactions
- Implement error handling for scenarios such as creating duplicate transactions or deleting a non-existent transaction
- Perform unit testing on the API to ensure robustness and reliability
- If relevant, handle and test the logic for transaction types or categories
- Ensure the API can withstand stress tests and maintain performance under load.

## Technology Stack

| Dependency Name | Version |
| --------------- | ------- |
| spring-boot     | 3.5.6   |
| h2              | 2.3.232 |
| mybatis-plus    | 3.5.5   |
| lombok          | 1.18.4  |
| apache-common   | 3.18.0  |
| jacoco          | 0.8.10  |
| smart-doc       | 2.7.7   |

## Getting Started

### Prerequisites

- Java 17
- IntelliJ IDEA 2023.1.5 (Community Edition)
- Maven 3.9.11
- JMeter 5.5
- Docker
- Docker Desktop（Used to run Kubernetes）

### Open with IntelliJ IDEA

If you are directly using the Maven wrapper in IDEA, the download speed may be quite slow without configuring a proxy. In this case, you can modify the download address to the mirror address of Alibaba Cloud.

1. In the root directory of the project, open the configuration file:

    ```
    .mvn/wrapper/maven-wrapper.properties
    ```

2. Find the configuration item `distributionUrl`, the default is as follows:

    ```
    distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.11/apache-maven-3.9.11-bin.zip
    ```

3. Change the download address to Alibaba Cloud mirror:

    ```
    distributionUrl=https://maven.aliyun.com/repository/public/org/apache/maven/apache-maven/3.9.11/apache-maven-3.9.11-bin.zip
    ```

### API Doc

The project has integrated smart-doc, and execute the command to generate the API docs:

```
mvn smart-doc:html
```

An website will be generated in the directory:

```
./smart-doc/docs
```

### Testing

#### Unit Testing

The project has integrated **jacoco**, and after executing the command below:

```shell
mvn test
```

A visual code coverage report will be generated in this directory:

```
./jacoco/html
```

#### Stress Testing

The jmeter directory under the project root directory contains the test plan for stress testing:

**Write 100,000 pieces of data（Run this first)**

- Thread count: 50, ramp-up time: 60, loop count: 2000

**Paginated query data（Run after writing complete)**

- Thread count: 50, ramp-up time: 30, loop count: 100



If you load the test plan correctly, it seem like this:

```
Transaction Interface Stress Test
├── Concurrent Write Test
│   ├── Summary Report
│   ├── View Results Tree
│   ├── HTTP Request Defaults
│   ├── HTTP Header Manager
│   ├── CSV Data File Config # here you need to modify parameters
│   ├── Random Variable
│   └── Create Transaction
└── Concurrent Read Test
    ├── HTTP Request Defaults
    ├── HTTP Header Manager
    ├── CSV Data File Config # here you need to modify parameters
    ├── While Controller
    │   └── Query Transaction List
    │       ├── lastCreateTime Extractor
    │       ├── lastId Extractor
    │       ├── hasMore Extractor
    │       ├── Result Code Assertion
    │       └── Query Parameter Processor
    ├── Summary Report
    └── View Results Tree
```



To use the test plan, you should modify the **CSV Data File Config** and set the file path to the account.csv path in your local directory:

```
jmeter
├── Stress_Testing.jmx # the test plan you can use
└── account.csv        # the csv file that "CSV Data File Settings" in test plan used
```

### Building

#### Jar

Execute the following command to package the project into an executable file (.jar):

```shell
mvn package -DskipTests
```

The file looks like this:

```
TransactionService-0.0.1-SNAPSHOT.jar
```

#### Docker Image

In the root directory of the project, execute the following command to build a docker image:

```shell
docker build -f dockerfile/dockerfile -t transaction-service:latest .
```

Use the `docker images` command to check whether the image has been built successfully:

```
REPOSITORY                 TAG               IMAGE ID       CREATED         SIZE
transaction-service        latest            8594a93b72d0   11 hours ago    705MB
```

### Deploy

#### Push image to the repositry

In the root directory of the project, execute the following command to push a docker image:

```shell
docker tag transaction-service:latest your-registry/transaction-service:latest
docker push your-registry/transaction-service:latest
```

#### Apply YAML

In the root directory of the project, execute the following command to apply the Kubernetes configs:

```shell
# apply ConfigMap
kubectl apply -f k8s/transaction-service-configmap.yaml

# apply Deployment
kubectl apply -f k8s/transaction-service-deployment.yaml

# apply Service
kubectl apply -f k8s/transaction-service-service.yaml
```

#### Check Status

```shell
# check the status of the Pod
kubectl get pods -l app=transaction-service
```

