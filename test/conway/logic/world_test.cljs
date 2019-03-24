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

(defn ->coordinate [[cell x y]]
  {:cell cell
   :x    x
   :y    y})

(defn coords [& params]
  (map ->coordinate params))

(deftest generation->cell+coordinates
  (are [generation size result] (= (l-world/generation->cell+coordinates size generation) result)
    [0 0 0 0]           2 (coords [0 0 0] [0 1 0] [0 0 1] [0 1 1])
    [0 1 0 1]           2 (coords [0 0 0] [1 1 0] [0 0 1] [1 1 1])
    [1 0 1 0]           2 (coords [1 0 0] [0 1 0] [1 0 1] [0 1 1])
    [1 1 1 1]           2 (coords [1 0 0] [1 1 0] [1 0 1] [1 1 1])
    [0 0 0 1 1 1 0 0 0] 3 (coords [0 0 0] [0 1 0] [0 2 0] [1 0 1] [1 1 1] [1 2 1] [0 0 2] [0 1 2] [0 2 2])))

(deftest coordinates->generation-index
  (are [coordinates size result] (= (l-world/coordinates->generation-index coordinates size) result)
    {:x 0 :y 0} 2 0
    {:x 1 :y 0} 2 1
    {:x 0 :y 1} 2 2
    {:x 1 :y 1} 2 3
    {:x 1 :y 2} 5 11))
