# `uri`

A URI library for Clojure, wrapping the
[java.net.URI](http://download.oracle.com/javase/1.4.2/docs/api/java/net/URI.html) class.

## Usage

Create a URI with:

    => (make "http://clojure.org/")
    #<URI http://clojure.org/>

You can also create URIs by passing in specific attributes. 

    => (make "http" "clojure.github.com" "/clojure/clojure.core.api.html"
    "clojure.core/defmulti")
    #<URI http://clojure.github.com/clojure/clojure.core.api.html#clojure.core/defmulti>

You can get individual attributes from URIs:

    => (scheme (make "http://clojure.org/"))
    "http"
    => (host (make "http://clojure.org/"))
    "clojure.org"

If a URI does not contain a particular attribute, they return nil. Note that
this is different from the java library, which returns -1 for a missing port.

You can determine whether a URI is absolute with `absolute?` or opaque with
`opaque?`:

    => (absolute? (make "http://clojure.org/"))
    true
    => (absolute? (make "clojure/clojure.core.api.html"))
    false
    => (opaque? (make "mailto:java-net@java.sun.com"))
    true
    => (opaque? (make "http://clojure.org/"))
    false

You can `normalize` a URI:

    => (normalize (make "docs/../clojure.core.api.html"))
    #<URI clojure.core.api.html>

You can `resolve` or `relativize` a URI (see the [javadoc](http://download.oracle.com/javase/1.4.2/docs/api/java/net/URI.html)):

    => (resolve (make "http://clojure.org/") (make "docs/clojure.core.api.html"))
    #<URI http://clojure.org/docs/clojure.core.api.html>
    => (relativize (make "http://clojure.org/")
                   (make "http://clojure.org/docs/clojure.core.api.html"))
    #<URI docs/clojure.core.api.html>

You can create a URL from a URI:

    => (url (make "http://clojure.org/"))
    #<URL http://clojure.org/>

You can swap between URIs and clojure maps:

    => (uri->map (make "foo://bar@baz:8000/foo?bar=baz#frag"))
    {:fragment "frag", :query "bar=baz", :path "/foo", :port 8000, :host "baz",
    :user-info "bar", :scheme "foo"}
    => (map->uri {:fragment "frag", :query "bar=baz", :path "/foo", :port 8000,
    :host "baz", :user-info "bar", :scheme "foo"})
    #<URI foo://bar@baz:8000/foo?bar=baz#frag>

## Changelist

v1.1.0

Bumped clojure version from 1.2.0 to 1.2.1.
Changed the constructor name from `uri` to `make`.
Added `uri->map` and `map->uri` to ease creating and dissecting uris.

## Contributors

DerGuteMoritz

## License

Copyright (C) 2011 Chris O'Donnell

Distributed under the Eclipse Public License, the same as Clojure.
