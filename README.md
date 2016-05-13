[![Build Status](https://travis-ci.org/rlishtaba/scala-commutable.svg?branch=master)](https://travis-ci.org/rlishtaba/scala-commutable)

# Commutable

Is a unified adapter to TCP/IP and Serial comm types.

# Usage

    import com.github.rlishtaba.commutable.layers.OSILayer
    
    class MyLayer extends OSILayer[Int, Byte] {
      
       ... 

    }
        

## Maven repository access

Use the following [link to the Maven Central](http://mvnrepository.com/artifact/com.github.rlishtaba/commutable) in order to grab suitable instruction for your packet manager such as maven, gradle, sbt, etc. 

### Maven example

    <dependency>
    	<groupId>com.github.rlishtaba</groupId>
    	<artifactId>commutable</artifactId>
    	<version>1.6.1</version>
    </dependency>
    
### Sbt example
    
    libraryDependencies += "com.github.rlishtaba" % "commutable" % "1.6.1"
 
# Development
         
Project can be assembled using `sbt` or `maven`. See SBT or Maven documentation.

## Prefer immutable environment in order to get it built    
         
    docker-compose build
    
using sbt:
             
    docker-compose run app sbt     
    
    $ clean test
            
using maven:

    docker-compose run mvn test package
             