(ns conway.logic.cell)

(def state {:dead  0
            :alive 1})

(defn- alive? [cell]
  (= cell (:alive state)))

(defn under-population?
  [neighbors]
  (->> neighbors
       (filter (comp alive? :cell))
       count
       (> 2)))

(defn over-population?
  [neighbors])

(defn stay-alive?
  [cell neighbors])

(defn revive?
  [cell neighbors])
