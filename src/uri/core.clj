(ns uri.core
  "The main namespace for the clojure URI library.   

  You can create a URI with:

  => (uri \"http://clojure.org/\")
  #<URI http://clojure.org/>
  (uri \"http\" \"clojure.github.com\" \"/clojure/clojure.core.api.html\"
  \"clojure.core/defmulti\")
  #<URI
  http://clojure.github.com/clojure/clojure.core.api.html#clojure.core/defmulti>

  The 'getters' all return nil in the case that the attribute does not exist in
  the URI. Note that a URI lacking a port will return -1 in the java API, but in
  this library it returns nil, same as the rest of the getters.
  
  => (port (uri \"http://localhost:8080\"))
  8080
  => (port (uri \"http://clojure.org/\"))
  nil

  The remaining 'getters' are: authority, fragment, host, path, query, scheme,
  ssp (scheme-specific part), user-info, raw-authority, raw-fragment, raw-path,
  raw-query, raw-ssp, and raw-user-info.
  
  There are two predicates: absolute? and opaque?.
  
  => (absolute? (uri \"http://clojure.org/\"))
  true
  => (absolute? (uri \"clojure/clojure.core.api.html\"))
  false
  
  There are three functions to modify URIs: normalize, resolve, and relativize.
  See the java.net.URI doc for more information about their operation.
  
  => (normalize (uri \"docs/../clojure.core.api.html\"))
  #<URI clojure.core.api.html>
  => (resolve (uri \"http://clojure.org/\") (uri \"docs/clojure.core.api.html\"))
  #<URI http://clojure.org/docs/clojure.core.api.html>
  => (relativize (uri \"http://clojure.org/\")
                 (uri \"http://clojure.org/docs/clojure.core.api.html\"))
  #<URI docs/clojure.core.api.html>
  
  You can also generate a URL object from a URI using url.
  
  => (url (uri \"http://clojure.org/\"))
  #<URL http://clojure.org/>"
  (:refer-clojure :exclude [resolve])
  (:import (java.net URI URLEncoder URLDecoder))
  (:require [clojure.string :as s]))

(defn url-encode
  "Returns an URL encoded representation of its argument"
  [arg]
  (URLEncoder/encode (str arg) "UTF-8"))

(defn form-url-encode [arg]
  "Returns application/x-www-form-urlencoded representation of a map"
  (s/join \& (map (fn [[k v]]
                    (if (vector? v)
                      (form-url-encode (map (fn [v] [k v]) v))
                      (str (url-encode (name k))
                           \=
                           (url-encode v))))
                  arg)))

(defn url-decode [arg]
  "Returns URL decoded representation of its argument"
  (URLDecoder/decode arg "UTF-8"))

(defn form-url-decode [str]
  (into {}
        (map (fn [p] (vector (keyword (first p)) (second p)))
             (map (fn [s] (map url-decode (s/split s #"=")))
                  (s/split str #"&")))))
(defn- format* [str a b]
  (if a (format str b a) b))

(defn map->uri [{:keys [scheme user-info host port path fragment query]}]
  "Constructs a uri from a map"
  (->> (format* "%2$s://%1$s" scheme "")
       (format* "%s%s@" user-info)
       (format* "%s%s" host)
       (format* "%s:%d" (and (not (= -1 port)) port))
       (format* "%s%s" path)
       (format* "%s?%s" (and query
                             (if (map? query)
                               (form-url-encode query)
                               query)))
       (format* "%s#%s" fragment)
       URI.))

(defn uri
  "Constructs a uri from the given components. See the java.net.URI
doc. Alternatively, a map can be passed which will be passed on to map->uri."
  ([str-or-map]
     (if (string? str-or-map)
       (URI. #^String str-or-map)
       (map->uri str-or-map)))
  ([#^String scheme #^String ssp #^String fragment]
     (URI. scheme ssp fragment))
  ([#^String scheme #^String user-info #^String host #^Integer port 
    #^String path #^String query #^String fragment]
     (URI. scheme user-info host port path query fragment))
  ([#^String scheme #^String host #^String path #^String fragment]
     (URI. scheme host path fragment))
  ([#^String scheme #^String authority #^String path #^String query #^String fragment]
     (URI. scheme authority path query fragment)))

(defn parent
  "Returns a uri of the parent of the URI."
  [#^URI uri]
  (str (s/join "/" (butlast (s/split (str uri) #"/"))) "/"))

(defn absolute?
  "Tells whether or not the URI is absolute." 
  [#^URI uri]
  (.isAbsolute uri))

(defn opaque? 
  "Tells whether or not the URI is absolute."
  [#^URI uri]
  (.isOpaque uri))

(defn normalize 
  "Normalizes the URI's path. See the java.net.URI docs."
  [#^URI uri]
  (.normalize uri))

(defn relativize 
  "Relativizes the base URI against the relative URI."
  [#^URI base #^URI rel]
  (.relativize base rel))

(defn resolve 
  "Resolves the given URI against the relative URI. Also takes a string as the
  second argument."
  [#^URI base rel]
  (.resolve base rel))

(defn parse-server-authority 
  "Attempts to parse the URI's authority component, if defined, into
  user-information, host, and port components."
  [#^URI uri]
  (.parseServerAuthority uri))

(defn url 
  "Constructs a URL from the URI."
  [#^URI uri]
  (.toURL uri))

(defn authority 
  "Returns the decoded authority component of the URI."
  [#^URI uri]
  (.getAuthority uri))

(defn fragment 
  "Returns the decoded fragment component of the URI."
  [#^URI uri]
  (.getFragment uri))

(defn host 
  "Returns the host component of the URI."
  [#^URI uri]
  (.getHost uri))

(defn path 
  "Returns the given path component of the URI."
  [#^URI uri]
  (.getPath uri))

(defn port 
  "Returns the port number of the URI."
  [#^URI uri]
  (let [port (.getPort uri)]
    (if-not (= -1 port)
      port
      nil)))

(defn query 
  "Returns the decoded query component of the URI."
  [#^URI uri]
  (.getQuery uri))

(defn scheme 
  "Returns the scheme component of the URI."
  [#^URI uri]
  (.getScheme uri))

(defn ssp 
  "Returns the scheme-specific part of the URI."
  [#^URI uri]
  (.getSchemeSpecificPart uri))

(defn user-info 
  "Returns the decoded user-information component of the URI."
  [#^URI uri]
  (.getUserInfo uri))

(defn raw-authority 
  "Returns the raw authority component of the URI."
  [#^URI uri]
  (.getRawAuthority uri))

(defn raw-fragment 
  "Returns the raw fragment of the URI."
  [#^URI uri]
  (.getRawFragment uri))

(defn raw-path 
  "Returns the raw path component of the URI."
  [#^URI uri]
  (.getRawPath uri))

(defn raw-query 
  "Returns the raw query component of the URI."
  [#^URI uri]
  (.getRawQuery uri))

(defn raw-ssp 
  "Returns the raw scheme-specific part of the URI."
  [#^URI uri]
  (.getRawSchemeSpecificPart uri))

(defn raw-user-info 
  "Returns the raw user-information part of the URI."
  [#^URI uri]
  (.getRawUserInfo uri))

(defn uri->map
  "Returns a map of URI parts, optionally form-url-decoding the query string to a map"
  ([uri] (uri->map uri false))
  ([uri form-url-decode-query?]
     (let [uri-map
           (reduce (fn [m [key getter]]
                     (assoc m key (getter uri)))
                   {}
                   {:scheme scheme
                    :user-info user-info
                    :host host
                    :port port
                    :path path
                    :query query
                    :fragment fragment})]
       (if (and (:query uri-map) form-url-decode-query?)
         (assoc uri-map :query (form-url-decode (:query uri-map)))
         uri-map))))