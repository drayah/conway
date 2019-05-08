(ns conway.logic.cell-test
  (:require [conway.logic.cell :as logic.cell]
            [cljs.test :refer-macros [deftest testing is are]]
            [conway.logic.helpers :refer [coords]]))

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

(deftest over-population?
  (are [neighbors result] (= (logic.cell/over-population? neighbors) result)
    (coords)                                 false
    (coords [0 0 0])                         false
    (coords [0 0 0] [0 0 1])                 false
    (coords [1 0 0] [1 0 1] [1 1 1])         false
    (coords [0 0 0] [0 0 1] [0 1 0] [0 1 1]) false
    (coords [1 0 0] [1 0 1] [0 1 0] [0 1 1]) false
    (coords [1 0 0] [1 0 1] [0 1 0] [1 1 1]) false
    (coords [1 0 0] [1 0 1] [1 1 0] [1 1 1]) true))

(deftest revive?
  (are [neighbors result] (= (logic.cell/revive? neighbors) result)
    (coords)                                 false
    (coords [0 0 0])                         false
    (coords [1 0 0])                         false
    (coords [0 0 0] [0 1 0] [0 1 1])         false
    (coords [0 0 0] [1 1 0] [1 1 1])         false
    (coords [1 0 0] [1 1 0] [1 1 1])         true
    (coords [1 0 0] [1 1 0] [0 0 1] [1 1 1]) true
    (coords [1 0 0] [0 1 0] [1 0 1] [1 1 1]) true
    (coords [1 0 0] [1 1 0] [1 0 1] [1 1 1]) false))

(deftest stay-alive?
  (are [neighbors result] (= (logic.cell/stay-alive? neighbors) result)
    (coords)                                 false
    (coords [0 0 0])                         false
    (coords [1 0 0])                         false
    (coords [1 0 0] [1 0 1])                 true
    (coords [1 0 0] [1 0 1] [0 1 1])         true
    (coords [1 0 0] [0 0 1] [1 1 1])         true
    (coords [1 0 0] [1 0 1] [1 1 1])         true
    (coords [1 0 0] [1 0 1] [0 1 0] [1 1 1]) true
    (coords [1 0 0] [1 0 1] [1 1 0] [1 1 1]) false))
