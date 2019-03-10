(ns conway.logic.world-test
  (:require [conway.logic.world :as l-world]
            [cljs.test :refer-macros [deftest testing is are]]))

(deftest valid-seed?
  (are [params result] (= (l-world/valid-seed? params) result)
    {:size 0 :seed []}                  true
    {:size 2 :seed [0 0 0 0]}           true
    {:size 2 :seed [1 0 0 0]}           true
    {:size 2 :seed [0 1 0 0]}           true
    {:size 2 :seed [0 0 1 0]}           true
    {:size 2 :seed [0 0 0 1]}           true
    {:size 2 :seed [1 1 1 1]}           true
    {:size 2 :seed [0 1 1 1]}           true
    {:size 2 :seed [1 0 1 1]}           true
    {:size 2 :seed [1 1 0 1]}           true
    {:size 2 :seed [1 1 1 0]}           true
    {:size 2 :seed [0 0 0 2]}           false
    {:size 2 :seed [0 9 0 0]}           false
    {:size 2 :seed [1 2 3 4]}           false
    {:size 3 :seed [0 0 0 0 0 0 0 0 0]} true))
