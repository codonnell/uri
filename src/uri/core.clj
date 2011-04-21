(ns uri.core
  (:refer-clojure :exclude [resolve])
  (:import (java.net URI)))

(defn uri
  "Constructs a uri from the given components. See the java.net.URI doc."
  ([#^String str-uri]
   (URI. str-uri))
  ([#^String scheme #^String ssp #^String fragment]
   (URI. scheme ssp fragment))
  ([#^String scheme #^String user-info #^String host #^Integer port 
    #^String path #^String query #^String fragment]
   (URI. scheme user-info host port path query fragment))
  ([#^String scheme #^String host #^String path #^String fragment]
   (URI. scheme host path fragment))
  ([#^String scheme #^String authority #^String path #^String query #^String fragment]
   (URI. scheme authority path query fragment)))

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
  (.relativize base given))

(defn resolve 
  "Resolves the given URI against the relative URI. Also takes a string as the
  second argument."
  [#^URI base rel]
  (.resolve base given))

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
  (.getPort uri))

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
