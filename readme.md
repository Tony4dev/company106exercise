# Company 106
This application is a simple reporting tool to know whether we are under/overpaying our employees and to see if our company structure is flat enough.

## Build
```
mvn clean package
```
Resulting jar file can be found in /target folder.

## Running
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