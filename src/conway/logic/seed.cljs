(ns conway.logic.seed)

(def dead-cell 0)

(defn- calculate-number-of-extra-sides
  [original-size final-size]
  (-> (- final-size original-size)
      (/ 2)))

(defn- top
  [num-sides size]
  (vertical Math/floor num-sides size))

(defn- bottom
  [num-sides size]
  (vertical Math/ceil num-sides size))

(defn- vertical
  [correct-sides-fn num-sides size]
  (->> (repeat size dead-cell)
       (repeat (correct-sides-fn num-sides))))

(defn resize
  [configuration final-size]
  (let [num-sides (calculate-fill-sides (:size configuration) final-size)]
    (concat
     (top num-sides final-size)
     ;middle
     (bottom num-sides final-size))))
