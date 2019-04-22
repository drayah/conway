(ns conway.logic.world-test
  (:require [conway.logic.world :as l-world]
            [cljs.test :refer-macros [deftest testing is are]]))

(def neighbor-count 8)

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

(deftest coordinates->vertical-neighbors
  (are [coordinates size result] (= (l-world/coordinates->vertical-neighbors coordinates size) result)
    {:x 0 :y 0} 3 [{:x 0 :y 2} {:x 0 :y 1}]
    {:x 1 :y 0} 3 [{:x 1 :y 2} {:x 1 :y 1}]
    {:x 2 :y 0} 3 [{:x 2 :y 2} {:x 2 :y 1}]
    {:x 0 :y 1} 3 [{:x 0 :y 0} {:x 0 :y 2}]
    {:x 1 :y 1} 3 [{:x 1 :y 0} {:x 1 :y 2}]
    {:x 2 :y 1} 3 [{:x 2 :y 0} {:x 2 :y 2}]
    {:x 0 :y 2} 3 [{:x 0 :y 1} {:x 0 :y 0}]
    {:x 1 :y 2} 3 [{:x 1 :y 1} {:x 1 :y 0}]
    {:x 2 :y 2} 3 [{:x 2 :y 1} {:x 2 :y 0}]))

(deftest coordinates->horizontal-neighbors
  (are [coordinates size result] (= (l-world/coordinates->horizontal-neighbors coordinates size) result)
    {:x 0 :y 0} 3 [{:x 2 :y 0} {:x 1 :y 0}]
    {:x 1 :y 0} 3 [{:x 0 :y 0} {:x 2 :y 0}]
    {:x 2 :y 0} 3 [{:x 1 :y 0} {:x 0 :y 0}]
    {:x 0 :y 1} 3 [{:x 2 :y 1} {:x 1 :y 1}]
    {:x 1 :y 1} 3 [{:x 0 :y 1} {:x 2 :y 1}]
    {:x 2 :y 1} 3 [{:x 1 :y 1} {:x 0 :y 1}]
    {:x 0 :y 2} 3 [{:x 2 :y 2} {:x 1 :y 2}]
    {:x 1 :y 2} 3 [{:x 0 :y 2} {:x 2 :y 2}]
    {:x 2 :y 2} 3 [{:x 1 :y 2} {:x 0 :y 2}]))

(deftest coordinates->diagonal-neighbors
  (are [coordinates size result] (= (l-world/coordinates->diagonal-neighbors coordinates size) result)
    {:x 0 :y 0} 3 [{:x 2 :y 2} {:x 2 :y 1} {:x 1 :y 2} {:x 1 :y 1}]
    {:x 1 :y 1} 3 [{:x 0 :y 0} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2}]))

(deftest coordinates->neighbors
  (testing "returns correct neighbor count"
    (is (= (-> (l-world/coordinates->neighbors {:x 0 :y 0} 3)
               count)
           neighbor-count)))

  (testing "returns all coordinate neighbors"
    (are [coordinates size result] (= (l-world/coordinates->neighbors coordinates size) result)
      {:x 1 :y 1} 3 [{:x 1 :y 0}
                     {:x 1 :y 2}
                     {:x 0 :y 1}
                     {:x 2 :y 1}
                     {:x 0 :y 0}
                     {:x 0 :y 2}
                     {:x 2 :y 0}
                     {:x 2 :y 2}]

      {:x 0 :y 0} 3 [{:x 0 :y 2}
                     {:x 0 :y 1}
                     {:x 2 :y 0}
                     {:x 1 :y 0}
                     {:x 2 :y 2}
                     {:x 2 :y 1}
                     {:x 1 :y 2}
                     {:x 1 :y 1}])))
