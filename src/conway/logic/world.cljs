(ns conway.logic.world)

(def possible-cell-values (-> (range 2)
                              set))

(defn- ones-and-zeros? [seed]
  (->> seed
       (remove #(possible-cell-values %))
       empty?))

(defn- valid-size? [size seed]
  (= (count seed)
     (* size size)))

(defn valid-seed? [{:keys [size seed]}]
  (and (valid-size? size seed)
       (ones-and-zeros? seed)))
