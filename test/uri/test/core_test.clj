(ns uri.test.core-test
  (:refer-clojure :exclude [resolve])
  (:require [uri.core :refer :all]
            [clojure.test :refer [deftest is]]))

(def test-uri1 (make "foo://bar@baz:8000/foo?bar=baz#frag"))
(def test-uri1a (make "foo" "bar" "baz" 8000 "/foo" "bar=baz" "frag"))
(def test-uri1b (make "foo" "bar@baz:8000" "/foo" "bar=baz" "frag"))
(def test-uri1c (make "foo" "//bar@baz:8000/foo?bar=baz" "frag"))
(def test-uri1d (make {:scheme "foo"
                      :user-info "bar"
                      :host "baz"
                      :port 8000
                      :path "/foo"
                      :query {:bar "baz"}
                      :fragment "frag"}))

(def test-uri2 (make "foo://bar/baz#frag"))
(def test-uri2a (make "foo" "bar" "/baz" "frag"))
(def test-uri2b (make "foo" "//bar/baz" "frag"))
(def test-uri2c (make {:scheme "foo"
                      :host "bar"
                      :path "/baz"
                      :fragment "frag"}))

(deftest constructor-test
  (is (= test-uri1 test-uri1a test-uri1b test-uri1c test-uri1d))
  (is (= test-uri2 test-uri2a test-uri2b test-uri2c)))

(deftest getter-test
  (is (= (scheme test-uri1) "foo"))
  (is (= (user-info test-uri1) "bar"))
  (is (= (host test-uri1) "baz"))
  (is (= (port test-uri1) 8000))
  (is (= (path test-uri1) "/foo"))
  (is (= (query test-uri1) "bar=baz"))
  (is (= (fragment test-uri1) "frag"))
  (is (= (authority test-uri1) "bar@baz:8000")))

(def test-uri3a (make "../foo/../bar/baz"))
(def test-uri3b (make "../bar/baz"))
(def test-uri3c (make "foo/./"))
(def test-uri3d (make "foo/"))

(deftest normalize-test
  (is (= (normalize test-uri3a) test-uri3b))
  (is (= (normalize test-uri3c) test-uri3d)))

(def test-uri4 (make "/foo/bar/baz.txt"))
(def test-uri4a (make "/foo/"))
(def test-uri4b (make "bar/baz.txt"))

(deftest relativize-resolve-test
  (is (= (relativize test-uri4a test-uri4) test-uri4b))
  (is (= (resolve test-uri4a test-uri4b) test-uri4)))

(def test-uri5a (make "news:comp.lang.lisp"))
(def test-uri5b (make "bar/baz.txt"))
(def test-uri5c (make "foo://bar.baz"))
(def test-uri5d (make "foo/bar.baz"))

(deftest predicates-test
  (is (opaque? test-uri5a))
  (is (not (opaque? test-uri5b)))
  (is (absolute? test-uri5c))
  (is (not (absolute? test-uri5d))))

(deftest uri->map-test
  (is (= (uri->map test-uri1 true)
         {:scheme "foo"
          :user-info "bar"
          :host "baz"
          :port 8000
          :path "/foo"
          :query {:bar "baz"}
          :fragment "frag"}))
  (is (= (uri->map test-uri2)
         {:scheme "foo"
          :user-info nil
          :host "bar"
          :port nil
          :path "/baz"
          :query nil
          :fragment "frag"})))
