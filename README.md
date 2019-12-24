# flow-spring-base
Project Base for Vaadin Flow and Spring Boot centered around Spring Security

[![NPM Version][npm-image]][npm-url]
[![Build Status][travis-image]][travis-url]
[![Downloads Stats][npm-downloads]][npm-url]

This project can be used as a starting point to create your own Vaadin Flow application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

No really, nothing else, that's its. 

## Development setup

Version Control: 

```mkdir new-project-name
git clone --bare https://github.com/git-abstrct/flow-spring-base.git
cd flow-spring-base.git
git push --mirror https://github.com/exampleuser/new-project-name.git
```

Now push to new project repo.

```
git push --mirror https://github.com/exampleuser/new-project-name.git
```

Remove local copy of base repository
```
cd ..
rm -rf flow-spring-base.git
```

See more on [duplicating a repository][dub-repo]

_For more examples and usage, please refer to the [Wiki][wiki]._

Other:
Import the project to the IDE of your choosing as a Maven project. 

## Usage
Run application using
`mvn spring-boot:run`

Open http://localhost:8080/ in browser


## Release History

* 0.0.1
    * Work in progress

## Meta

Folarin O – [@itsflow](https://twitter.com/itsflow)

Distributed under the GPL license. See ``LICENSE`` for more information.

[https://github.com/abstct](https://github.com/abstct/)

## Contributing

1. Fork it (<https://github.com/yourname/yourproject/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

For documentation on using Vaadin Flow and Spring, visit [vaadin.com/docs](https://vaadin.com/docs/v10/flow/spring/tutorial-spring-basic.html)

For more information on Vaadin Flow, visit https://vaadin.com/flow.

<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[dub-repo]:  https://help.github.com/articles/duplicating-a-repository/#platform-linux
[wiki]: https://github.com/yourname/yourproject/wiki
