# LionX

This is a group assignment for IST 311. This application
was built using OpenJFX 11.

[![Build Status](https://travis-ci.com/Triippz/LionX.svg?branch=master)](https://travis-ci.com/Triippz/LionX)

## Development
Ensure you have [Maven](https://maven.apache.org/download.cgi) downloaded for CLI interfacing.

Clone the repository
```bash
git clone https://github.com/Triippz/LionX
```

Enter the directory
```bash
cd LionX
```

Run the maven goal for JavaFX 11
```bash
mvn javafx:run
```

Run tests
```bash
mvn test
```

Since JavaFX is no longer bundled with the JDK it can be some work to
run JavaFX using an IDE configuration, if you have no experience.
If using IntelliJ on MacOS, you need to ensure you point your VM options 
to any applicable JavaFX modules you wish to use. This should suffice on mac.

```bash
--module-path lib/javafx-sdk-11.0.2/lib --add-modules=javafx.controls,javafx.fxml
```

It may be easier just to run the Maven goals from within IntelliJ for debugging and running.


## Build

Run the maven goal to build a 'Fat Jar'
```bash
mvn package
```

## Execute the Binary
```bash
./LionX.jar
```

or

```bash
java -jar LionX
```


## Notes
To ensure we have a database for submission it has been added to the root of 
the project root directory it is named `H2DB`, you will find, as you guessed it,
a file-based H2 datastore.

This project uses:
- Java 11
- OpenJFX 11
- Hibernate
- DHCP for connection pooling
- Java-Stellar-SDK for Stellar Lumens integration
- ControlsFX for more JavaFX components

## License
GPL V3


If you have questions, contact Mark, I can help you with whatever you need.

`Right now, this only uses Stellar's Test Net.`