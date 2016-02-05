[![Build Status](https://travis-ci.org/rlishtaba/commutable.svg)](https://travis-ci.org/rlishtaba/commutable)

# Commutable

This is playground project. Unified adapter to TCP/IP and Serial comm types.

# Usage

    import com.github.rlishtaba.commutable.layers.OSILayer
    
    class MyLayer extends OSILayer[Int, Byte] {
      
       ... 

    }
        
# Build
         
Project can be build using `sbt` or `maven`. See SBT or Maven documentaion.

## Using immutable environment:    
         
    docker-compose build
    
using sbt:
             
    docker-compose run app sbt     
    
    $ clean test
            
using maven:

    docker-compose run mvn test
             