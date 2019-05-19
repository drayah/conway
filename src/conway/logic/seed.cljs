(ns conway.logic.seed)

(def dead-cell 0)

(defn- calculate-extra-sides
  [original-size new-size]
  (-> (- new-size original-size)
      (/ 2)))

(defn- vertical
  [correct-sides-fn num-sides new-size]
  (->> (repeat new-size dead-cell)
       (repeat (correct-sides-fn num-sides))))

(defn- top
  [num-sides new-size]
  (vertical Math/floor num-sides new-size))

(defn- bottom
  [num-sides new-size]
  (vertical Math/ceil num-sides new-size))

(defn- left
  [num-sides]
  (repeat (Math/floor num-sides) dead-cell))

(defn- right
  [num-sides]
  (repeat (Math/ceil num-sides) dead-cell))

(defn- resize-row
  [num-sides row]
  (concat (left num-sides) row (right num-sides)))

(defn- middle
  [num-sides rows]
  (map (partial resize-row num-sides) rows))

(defn- merge-rows
  [resized-rows]
  (apply concat resized-rows))

(defn resize
  [configuration new-size]
  (let [num-sides (calculate-extra-sides (:size configuration) new-size)
        rows      (partition (:size configuration) (:seed configuration))]
    {:size new-size
     :seed (-> (concat (top num-sides new-size)
                       (middle num-sides rows)
                       (bottom num-sides new-size))
               merge-rows)}))
