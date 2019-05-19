(ns conway.logic.seed-test
  (:require [conway.logic.seed :as logic.seed]
            [cljs.test :refer-macros [deftest testing is are]]))

(def configuration {:size 3
                    :seed [0 0 0
                           1 1 1
                           0 0 0]})

(deftest resize
  (is (= (logic.seed/resize configuration 4) {:size 4
                                              :seed [0 0 0 0
                                                     1 1 1 0
                                                     0 0 0 0
                                                     0 0 0 0]}))

  (is (= (logic.seed/resize configuration 9) {:size 9
                                              :seed [0 0 0 0 0 0 0 0 0
                                                     0 0 0 0 0 0 0 0 0
                                                     0 0 0 0 0 0 0 0 0
                                                     0 0 0 0 0 0 0 0 0
                                                     0 0 0 1 1 1 0 0 0
                                                     0 0 0 0 0 0 0 0 0
                                                     0 0 0 0 0 0 0 0 0
                                                     0 0 0 0 0 0 0 0 0
                                                     0 0 0 0 0 0 0 0 0]})))
