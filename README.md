# corncob-cigar

A Leiningen plugin with miscellaneous utilities.

> Leiningen sucked placidly at a cigar about the size of a corncob and
> for a few seconds gazed without answering at the agitated District
> Commissioner. Then he took the cigar from his lips, and leaned
> slightly forward. With his bristling grey hair, bulky nose, and lucid
> eyes, he had the look of an aging and shabby eagle.

## Obtainage

Add to the `:plugins` list (probably in your `:user` profile):

```
[com.gfredericks/corncob-cigar "0.1.0"]
```

## Usage

So far everything is oriented toward customizing `lein deploy`. In
particular, lein currently does not support disabling PGP signatures
when doing git tags, and it has no mechanism for updating version
references in README files and similar. This library provides two
custom tasks to solve those problems.

### `vcs-tag-no-sign`

This task is like the built-in `lein vcs tag` but it creates a simple
tag named "<project-name>-<version>" with no PGP signature.

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

## License

Copyright © 2014 Gary Fredericks

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
