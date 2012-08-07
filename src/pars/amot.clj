(ns pars.amot
  (:use [com.lithinos.amotoen.core]))

(def create-grammar
  {:Create [(pegs "CREATE") :_ :Name :$]
   
   :Name [:Alpha '(* :AlphaNum)]
   
   :AlphaNum '(| :Alpha :Num)
   :Num (lpegs '| "0123456789")
   :Alpha (lpegs '| "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

   :_* '(* :Whitespace)
   :_ '(+ :Whitespace)
   :Whitespace '(| \space \tab)
   })

(defn p-create [s]
  (pegasus :Create create-grammar (wrap-string s)))
