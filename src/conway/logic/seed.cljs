(ns conway.logic.seed)

(defn- calculate-fill-sides
  [original-size final-size]
  (-> (- final-size original-size)
      (/ 2)))

(defn resize
  [configuration final-size]
  (let [fill (calculate-fill-sides (:size configuration) final-size)]
    fill))
