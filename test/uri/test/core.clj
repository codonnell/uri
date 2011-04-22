(ns uri.test.core
  (:refer-clojure :exclude [resolve])
  (:use [uri.core] :reload)
  (:use [clojure.test]))

(def test-uri1 (uri "foo://bar@baz:8000/foo?bar=baz#frag"))
(def test-uri1a (uri "foo" "bar" "baz" 8000 "/foo" "bar=baz" "frag"))
(def test-uri1b (uri "foo" "bar@baz:8000" "/foo" "bar=baz" "frag"))
(def test-uri1c (uri "foo" "//bar@baz:8000/foo?bar=baz" "frag"))

(def test-uri2 (uri "foo://bar/baz#frag"))
(def test-uri2a (uri "foo" "bar" "/baz" "frag"))
(def test-uri2b (uri "foo" "//bar/baz" "frag"))

(deftest constructor-test
  (is (= test-uri1 test-uri1a test-uri1b test-uri1c))
  (is (= test-uri2 test-uri2a test-uri2b)))

(deftest getter-test
  (is (= (scheme test-uri1) "foo"))
  (is (= (user-info test-uri1) "bar"))
  (is (= (host test-uri1) "baz"))
  (is (= (port test-uri1) 8000))
  (is (= (path test-uri1) "/foo"))
  (is (= (query test-uri1) "bar=baz"))
  (is (= (fragment test-uri1) "frag"))
  (is (= (authority test-uri1) "bar@baz:8000")))

(def test-uri3a (uri "../foo/../bar/baz"))
(def test-uri3b (uri "../bar/baz"))
(def test-uri3c (uri "foo/./"))
(def test-uri3d (uri "foo/"))

(deftest normalize-test
  (is (= (normalize test-uri3a) test-uri3b))
  (is (= (normalize test-uri3c) test-uri3d)))

(def test-uri4 (uri "/foo/bar/baz.txt"))
(def test-uri4a (uri "/foo/"))
(def test-uri4b (uri "bar/baz.txt"))

(deftest relativize-resolve-test
  (is (= (relativize test-uri4a test-uri4) test-uri4b))
  (is (= (resolve test-uri4a test-uri4b) test-uri4)))

(def test-uri5a (uri "news:comp.lang.lisp"))
(def test-uri5b (uri "bar/baz.txt"))
(def test-uri5c (uri "foo://bar.baz"))
(def test-uri5d (uri "foo/bar.baz"))

(deftest predicates-test
  (is (opaque? test-uri5a))
  (is (not (opaque? test-uri5b)))
  (is (absolute? test-uri5c))
  (is (not (absolute? test-uri5d))))
