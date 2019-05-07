(ns conway.logic.world
  (:require [conway.logic.cell :as logic.cell]))

(def possible-cell-values (-> logic.cell/state
                              vals
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

(defn- cell->coordinate [size index cell]
  {:cell cell
   :x    (mod index size)
   :y    (-> (/ index size)
             Math/floor)})

(defn generation->cell+coordinates
  "Transforms a given generation into a sequence
  of cells with coordinates like {:cell cell-value
                                  :x    x-pos
                                  :y    y-pos}"
  [size generation]
  (map-indexed (partial cell->coordinate size) generation))

(defn- no-invalid-coordinates
  [size
   {:keys [x y] :as coordinate}]
  (let [min 0
        max (dec size)]
    (cond-> coordinate
      (< x min) (assoc :x max)
      (> x max) (assoc :x min)
      (< y min) (assoc :y max)
      (> y max) (assoc :y min))))

(defn coordinates->vertical-neighbors
  [{:keys [x y]}
   size]
  (map (partial no-invalid-coordinates size) [{:x x :y (dec y)}
                                              {:x x :y (inc y)}]))

(defn coordinates->horizontal-neighbors
  [{:keys [x y]}
   size]
  (map (partial no-invalid-coordinates size) [{:x (dec x) :y y}
                                              {:x (inc x) :y y}]))

(defn coordinates->diagonal-neighbors
  [{:keys [x y]}
   size]
  (map (partial no-invalid-coordinates size) [{:x (dec x) :y (dec y)}
                                              {:x (dec x) :y (inc y)}
                                              {:x (inc x) :y (dec y)}
                                              {:x (inc x) :y (inc y)}]))

(defn coordinates->neighbors
  [{:keys [x y] :as coordinate}
   size]
  (concat (coordinates->vertical-neighbors coordinate size)
          (coordinates->horizontal-neighbors coordinate size)
          (coordinates->diagonal-neighbors coordinate size)))

(defn coordinates->generation-index
  "Return the 0-based generation index given (x, y) coordinate values and the row, column size
  according to the following formula: index = y * size + x"
  [{:keys [x y]}
   size]
  (-> (* y size)
      (+ x)))

(defn- coordinates->cell+coordinates
  [coordinates size generation]
  (->> (coordinates->generation-index coordinates size)
       (nth generation)
       (assoc coordinates :cell)))

(defn- cell+coordinates->cell+coordinates+neighbors
  [cell+coordinates size generation]
  (let [neighbors (coordinates->neighbors cell+coordinates size)]
    (assoc cell+coordinates :neighbors (map #(coordinates->cell+coordinates % size generation) neighbors))))

(defn generation->neighbors
  [size generation]
  (->> (generation->cell+coordinates size generation)
       (map #(cell+coordinates->cell+coordinates+neighbors % size generation))))

(defn rules
  "The following rules are applied in order
  to generate the next generation.

  1. Any cell with fewer than two live neighbors dies.
  2. Any cell with two or three live neighbors lives on.
  3. Any cell with more than three live neighbors dies.
  4. Any dead cell with exactly three live neighbors returns to life."
  [{:keys [cell neighbors]}]
  (cond
    (logic.cell/under-population? neighbors) (:dead logic.cell/state)
    (logic.cell/stay-alive? cell neighbors)  cell
    (logic.cell/over-population? neighbors)  (:dead logic.cell/state)
    (logic.cell/revive? cell neighbors)      (:alive logic.cell/state)
    :else                                    cell))

(defn next-generation
  [size generation]
  (->> (generation->neighbors size generation)))
