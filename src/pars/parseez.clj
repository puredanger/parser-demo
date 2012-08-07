(ns pars.core
  (:use [protoflex.parse]))

(defn p-name []
  (regex #"[a-zA-Z][a-zA-z0-9]*"))

(defn p-column []
  (let [col-name (p-name)
        _ (read-ws)
        col-type (p-name)]
    (read-ws)
    [col-name col-type]))

(defn p-columns []
  (let [first-col (p-column)
        rest-cols (multi*
                   (fn [] (chr \,) (p-column)))]
    (into [first-col] rest-cols)))

(defn p-create []
  (series #(word "CREATE") #(read-ws))
  (let [table-name (p-name)
        _ (series #(read-ws) #(string "(") #(read-ws))
        cols (opt p-columns)]
    (string ")")
    [table-name cols]))

(defn parse-create [s]
  (parse p-create s))

(comment
  (parse-create "CREATE foo (a, b)"))

