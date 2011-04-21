# `uri`

A URI library for Clojure, wrapping the
[URI](http://download.oracle.com/javase/1.4.2/docs/api/java/net/URI.html) class.

## Usage

Create a URI with:

    => (uri "http://www.clojure.org/")
    #<URI http://www.clojure.org/>

You can also create URIs by passing in specific attributes. Attributes may be
empty.

    => (uri "http" "clojure.github.com" "/clojure/clojure.core.api.html"
    "clojure.core/defmulti")
    #<URI http://clojure.github.com/clojure/clojure.core.api.html#clojure.core/defmulti>

You can get individual attributes from URIs:

    => (scheme (uri "http://www.clojure.org/"))
    "http"
    => (host (uri "http://www.clojure.org/"))
    "www.clojure.org"

You can determine whether a URI is absolute with `absolute?`:

    => (absolute? (uri "http://www.clojure.org/"))
    true
    => (absolute? (uri "clojure/clojure.core.api.html"))
    false

You can `relativize` or `resolve` a URI:

    => (relativize (uri "http://clojure.org/rationale"))

    => (normalize (uri "docs/../clojure.core.api.html"))
    #<URI clojure.core.api.html>

## License

Copyright (C) 2010 Chris O'Donnell

Distributed under the Eclipse Public License, the same as Clojure.
