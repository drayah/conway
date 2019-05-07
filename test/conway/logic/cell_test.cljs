(ns conway.logic.cell-test
  (:require [conway.logic.cell :as logic.cell]
            [cljs.test :refer-macros [deftest testing is are]]))

(defn- ->coordinate [[cell x y]]
  {:cell cell
   :x    x
   :y    y})

(defn- coords [& params]
  (map ->coordinate params))

(deftest under-population?
  (are [neighbors result] (= (logic.cell/under-population? neighbors) result)
    (coords)                         true
    (coords [0 0 0])                 true
    (coords [1 0 0] [1 0 1])         false
    (coords [0 0 0] [0 0 1])         true
    (coords [1 0 0] [1 0 1] [0 0 0]) false
    (coords [0 0 0] [1 0 1] [1 0 0]) false
    (coords [0 0 0] [0 0 1] [1 0 0]) true
    (coords [0 0 0] [1 0 1] [0 0 0]) true
    (coords [1 0 0] [1 0 1] [1 0 0]) false
    (coords [0 0 0] [0 0 1] [0 0 0]) true))
