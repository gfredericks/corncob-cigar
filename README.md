# corncob-cigar

A Leiningen plugin with miscellaneous utilities.

> Leiningen sucked placidly at a cigar about the size of a corncob and
> for a few seconds gazed without answering at the agitated District
> Commissioner. Then he took the cigar from his lips, and leaned
> slightly forward. With his bristling grey hair, bulky nose, and lucid
> eyes, he had the look of an aging and shabby eagle.

## Obtention

Add to the `:plugins` list (probably in your `:user` profile):

```
[com.gfredericks/corncob-cigar "0.1.3"]
```

## Usage

### `vcs-tag-no-sign`

This task is like the built-in `lein vcs tag` but it creates a simple
tag named `<project-name>-<version>` with no PGP signature.

### `gsub-version`

This task will update the version references to your project by doing
basic string substitution on a set of files specified by the
`:gsub-version/files` entry in the project. E.g., with

```
(defproject foo/bar "1.0.0"
  ...
  :gsub-version/files #{"README.md"})
```

running `lein gsub-version` will replace anything in `README.md` that
looks like `[foo/bar "SOME OTHER VERSION"]` with `[foo/bar "1.0.0"]`.

### `deploy-fork`

A task for making releases of other people's libraries.

You'll need a `:deploy-fork` entry in your `:user` profile:

``` clojure
  :deploy-fork {:group-id-prefix "com.mydomain.forks"}
```

And then `lein deploy-fork` will package the project with a modified
group-id and a version tagged with the current git commit sha, and
deploy it.

### `benchmark-task`

A task for running a task repeatedly, gathering statistics about how
long it takes:

```
$ lein benchmark-task 10 version
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Leiningen 2.5.0 on Java 1.7.0-u65 OpenJDK 64-Bit Server VM
Ran task 10 times, runtime was mean of 1.00ms, standard deviation of 1.10ms.
```

## License

Copyright © 2014 Gary Fredericks

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
