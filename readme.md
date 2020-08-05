# Dropbox Resource Spring Boot Starter

Spring Boot starter that auto-configures protocol resolver for resolving resources from Dropbox.

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