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
  [neighbors]
  (let [threshold-low       (-> (:minimum neighbor-count) dec)
        threshold-high      (-> (:maximum neighbor-count) inc)
        live-neighbor-count (count-alive neighbors)]
    (< threshold-low
       live-neighbor-count
       threshold-high)))

(defn revive?
  [neighbors]
  (-> neighbors
      count-alive
      (= (:maximum neighbor-count))))
