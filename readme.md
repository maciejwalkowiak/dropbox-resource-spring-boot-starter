# Dropbox Resource Spring Boot Starter

Spring Boot starter that auto-configures protocol resolver for resolving resources from Dropbox.

## Installation

This project is not published to official Maven repository, but you can include it via JitPack:

1. Add Jitpack repository:
    ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ```
2. Add the dependency:
    ```xml
    <dependency>
        <groupId>com.github.maciejwalkowiak</groupId>
        <artifactId>dropbox-resource-spring-boot-starter</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
    ```
[More info about JitPack](https://jitpack.io/)

## How to use?

Using Spring's `ResourceLoader`:

```java
@Autowired
private ResourceLoader resourceLoader;
...
Resource resource = resourceLoader.getResource("dropbox://file.txt");

```

Using `@Value`:

```java
@Value("dropbox://file.txt") 
Resource resource;
```

## But why?

This project is made mainly to demonstrate how to develop custom protocol resolver. 

I doubt there will be many projects that will need to use this Dropbox integration, but if you do [give me a shout!](http://twitter.com/maciejwalkowiak)