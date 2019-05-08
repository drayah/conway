(ns conway.logic.cell)

(def state {:dead  0
            :alive 1})

(defn- alive? [cell]
  (= cell (:alive state)))

(def filter-alive (partial filter (comp alive? :cell)))

(defn under-population?
  [neighbors]
  (->> neighbors
       filter-alive
       count
       (> 2)))

(defn over-population?
  [neighbors]
  (->> neighbors
       filter-alive
       count
       (< 3)))

(defn stay-alive?
  [cell neighbors])

(defn revive?
  [neighbors]
  (->> neighbors))
