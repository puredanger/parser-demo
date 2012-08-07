(ns pars.parsley
  (:require [net.cgrand.parsley :as p])
  (:use [clojure.pprint]))

(def p (p/parser :expr #{"x" ["(" :expr* ")"]}))
(pprint (p "(x(x))"))

(def p-create
  (p/parser {:main :create
             :space :ws?
             :make-node (fn [tag content]
                          (case tag
                                :name (first content)
                                {:tag tag :content content}))}
            :create [:create-kw :name :lp :rp]
            :ws- #"\s+"
            :name #"[a-zA-Z][a-zA-Z0-9]*"
            :lp- "("
            :rp- ")"
            :create-kw- "CREATE"
            ))

;;(pprint (p-create "CREATE foo ()"))


