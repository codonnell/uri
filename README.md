# `uri`

A URI library for Clojure, wrapping the
[URI](http://download.oracle.com/javase/1.4.2/docs/api/java/net/URI.html) class.

## Usage

Create a URI with:

    => (uri "http://clojure.org/")
    #<URI http://clojure.org/>

You can also create URIs by passing in specific attributes. Attributes may be
empty.

    => (uri "http" "clojure.github.com" "/clojure/clojure.core.api.html"
    "clojure.core/defmulti")
    #<URI http://clojure.github.com/clojure/clojure.core.api.html#clojure.core/defmulti>

You can get individual attributes from URIs:

    => (scheme (uri "http://clojure.org/"))
    "http"
    => (host (uri "http://clojure.org/"))
    "clojure.org"

You can determine whether a URI is absolute with `absolute?`:

    => (absolute? (uri "http://clojure.org/"))
    true
    => (absolute? (uri "clojure/clojure.core.api.html"))
    false

You can `normalize` a URI:

    => (normalize (uri "docs/../clojure.core.api.html"))
    #<URI clojure.core.api.html>

You can `resolve` or `relativize` a URI (see the [javadoc](http://download.oracle.com/javase/1.4.2/docs/api/java/net/URI.html)):

    => (resolve (uri "http://clojure.org/") (uri "docs/clojure.core.api.html"))
    #<URI http://clojure.org/docs/clojure.core.api.html>
    => (relativize (uri "http://clojure.org/")
                   (uri "http://clojure.org/docs/clojure.core.api.html"))
    #<URI docs/clojure.core.api.html>

## License

Copyright (C) 2010 Chris O'Donnell

Distributed under the Eclipse Public License, the same as Clojure.
