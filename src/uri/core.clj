(ns uri.core
  (:import (java.net URI)))

(defn uri
  "Constructs a uri from the given components."
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

(defn absolute? [#^URI uri]
  (.isAbsolute uri))

(defn opaque? [#^URI uri]
  (.isOpaque uri))

(defn normalize [#^URI uri]
  (.normalize uri))

(defn relativize [#^URI uri]
  (.relativize uri))

(defmulti resolve class)
(defmethod resolve URI [uri]
  (.resolve uri))
(defmethod resolve String [str-uri]
  (.resolve (uri str-uri)))

(defn parse-user-auth [#^URI uri]
  (.parseUserAuthority uri))

(defn url [#^URI uri]
  (.toURL uri))

(defn authority [#^URI uri]
  (.getAuthority uri))

(defn fragment [#^URI uri]
  (.getFragment uri))

(defn host [#^URI uri]
  (.getHost uri))

(defn path [#^URI uri]
  (.getPath uri))

(defn port [#^URI uri]
  (.getPort uri))

(defn query [#^URI uri]
  (.getQuery uri))

(defn scheme [#^URI uri]
  (.getScheme uri))

(defn ssp [#^URI uri]
  (.getSchemeSpecificPart uri))

(defn user-info [#^URI uri]
  (.getUserInfo uri))

(defn raw-authority [#^URI uri]
  (.getRawAuthority uri))

(defn raw-fragment [#^URI uri]
  (.getRawFragment uri))

(defn raw-path [#^URI uri]
  (.getRawPath uri))

(defn raw-query [#^URI uri]
  (.getRawQuery uri))

(defn raw-ssp [#^URI uri]
  (.getRawSchemeSpecificPart uri))

(defn raw-user-info [#^URI uri]
  (.getRawUserInfo uri))

