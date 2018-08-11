# flow-spring-base
> A small template for projects using Vaadin Flow with Spring.

[![NPM Version][npm-image]][npm-url]
[![Build Status][travis-image]][travis-url]
[![Downloads Stats][npm-downloads]][npm-url]

Just a project base focused on spring security. No really, nothing else, thats its. 

## Development setup

OS X & Linux:

```mkdir new-project-name
git clone --bare https://github.com/git-abstrct/flow-spring-base.git
cd flow-spring-base.git
git push --mirror https://github.com/exampleuser/new-project-name.git
```

Windows:

Same as above.

## Usage example

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


## Release History

* 0.0.1
    * Work in progress

## Meta

Folarin O – [@itsflow](https://twitter.com/itsflow) – yes@iamfolarin.com 

Distributed under the GPL license. See ``LICENSE`` for more information.

[https://github.com/abstct](https://github.com/abstct/)

## Contributing

1. Fork it (<https://github.com/yourname/yourproject/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[dub-repo]:  https://help.github.com/articles/duplicating-a-repository/#platform-linux
[wiki]: https://github.com/yourname/yourproject/wiki