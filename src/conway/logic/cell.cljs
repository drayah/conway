(ns conway.logic.cell)

(def state {:dead  0
            :alive 1})

(def neighbor-count {:minimum 2
                     :maximum 3})

(defn- alive? [cell]
  (= cell (:alive state)))

(def filter-alive (partial filter (comp alive? :cell)))

(defn- count-alive
  [neighbors]
  (->> neighbors
       filter-alive
       count))

(defn under-population?
  [neighbors]
  (-> neighbors
      count-alive
      (< (:minimum neighbor-count))))

(defn over-population?
  [neighbors]
  (-> neighbors
      count-alive
      (> (:maximum neighbor-count))))

(defn stay-alive?
  [cell neighbors])

(defn revive?
  [neighbors]
  (-> neighbors
      count-alive
      (= (:maximum neighbor-count))))
