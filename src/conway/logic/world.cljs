(ns conway.logic.world)

(defn- only-numbers? [seed]
  (->> seed
       (remove #(number? %))
       empty?))

(defn- valid-size? [size seed]
  (= (count seed)
     (* size size)))

(defn valid-seed? [{:keys [size seed]}]
  (and (valid-size? size seed)
       (only-numbers? seed)))
