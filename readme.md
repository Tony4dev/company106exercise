# Company 106
This application is a simple reporting tool to know whether we are under/overpaying our employees and to see if our company structure is flat enough.

## Running with maven
Run following command in project root folder:
```
mvn compile exec:java
```
Alternatively you can specify the filename/filepath as an argument:
e.g.:
```
compile exec:java -Dexec.arguments=my-file.csv
```
or e.g.:
```
compile exec:java -Dexec.arguments=../../some-path/my-file.csv
```

## Build jar
```
mvn clean package
```
Resulting jar file can be found in /target folder.

## Running jar
You can run the jar file without arguments. In case of which the csv file must be named employees.csv and needs to sit next to the jar file:
e.g.:
```
java -jar company106-1.0-SNAPSHOT.jar
```

Alternatively you can specify the filename/filepath as an argument:
e.g.:
```
java -jar company106-1.0-SNAPSHOT.jar my-file.csv
```
or e.g.:
```
java -jar company106-1.0-SNAPSHOT.jar ../../some-path/my-file.csv
```