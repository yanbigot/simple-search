# Simple search engine

Allows to search for exact terms in a set of "txt" documents in a directory and sub directories interactively

## Change log
- 10/06/2019: duration 2 hours
    - add html reports
    - separate console application logic from display - with tests
    - rename context loader
    - add test cases on argument parsing
    - add docId and change filename computation to relative file path for handling same files in different folders

## About the challenge itself

#### Objective: a 2 to 3 hours session
#### Duration: a rainy day (around 10 hours) as follow:
- 1 hour to read the requirements and study the "search" field
- 2 hour design (index and data structure) iteration and looking for test data
- 4 hours for 1st iteration in a test first approach
- 2 hour for 2nd iteration in various modifications 
- 1 hour for deployement (I wanted a fat jar for avoiding -cp burden) and documentation 

It seems pretty standard to me, not really sure what could be done in 1 hour ...

#### The next steps
- stemming and cleaning terms (dictionary or algo approach ??)
- a relevant scoring (td idf ...) for terms
- a relevant scoring for a whole search expression

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

This is a **scala** project built with **sbt**

```
- JDK at least 1.8.x installed with environment variables
- SBT at least 1.2.x installed with environment variables
```

### Installing

Unzip `SimpleSearchEngine.zip` to `destination_folder`, you should have

```
SearchCodeChallenge
    |_ .idea
    |_ project
    |_ src
       |_ main/resources/data
       build.sbt
       README.md
```

Build from the command line with assembly plugin (fat jar generation)
```
destination_folder> sbt assembly

[info] Packaging ...SearchCodeChallenge-assembly-0.1.jar
[info] Done packaging.
[success] ...
```

Initially built with IntelliJ (hence the .idea folder)

## Running the tests

Tests under Scalatest framework, to run them
```
destination_folder> sbt test
```
Html reports of the test cases can be found in
```
target/test-html-reports/index.html
```

## Deployment

Not yet covered

## Run the Application

Application parameters
```
"-interactive [true|false]", interactive will start a prompt otherwise appllication can be used by third party applications
"-directory [path_to_dir]", the directory containing the documents to be indexed and searched for
"-format [json|txt]", response format either simple txt or json
"-max_results [integer]", number of top n results
```

Run with java -jar argument
```
java -jar search-code-challenge-assembly-0.1.jar -interactive true -format json -directory path_to_dir  
```

## Usage

When prompted, type in your search expression
```
search>love
```
Example response  
```
found 22000000 nanoseconds
{
  "hits": [
    {
      "fileName": "shakespeare.txt",
      "score": "100.0%",
      "terms": [
        {
          "term": "love",
          "occurence": 2198
        }
      ]
    },
    {
      "fileName": "metamorphosis.txt",
      "score": "100.0%",
      "terms": [
        {
          "term": "love",
          "occurence": 2
        }
      ]
    },
    {
      "fileName": "simple.txt",
      "score": "100.0%",
      "terms": [
        {
          "term": "love",
          "occurence": 3
        }
      ]
    }
  ]
}
```

Prompt functions
```
- to quit ":quit"
- to show the index ":index"
- to show the list of indexed files ":list" 
- for help ":help"
```

## Built With

* [SBT](https://www.scala-sbt.org/) - Dependency Management

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

No github version control publication allowed by the requirements

## Authors

* **Yan Bigot** - *Initial work*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
