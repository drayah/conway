(ns conway.logic.world)

(def possible-cell-values #{0 1})

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

(defn no-invalid-coordinates
  [size
   {:keys [x y] :as coordinate}]
  (let [min 0
        max (- size 1)]
    (cond-> coordinate
      (< x min) (assoc :x max)
      (> x max) (assoc :x min)
      (< y min) (assoc :y max)
      (> y max) (assoc :y min))))

(defn coordinates->vertical-neighbors
  [{:keys [x y]}
   size]
  (map (partial no-invalid-coordinates size)
       [{:x x :y (- y 1)}
        {:x x :y (+ y 1)}]))

(defn coordinates->horizontal-neighbors [])
(defn coordinates->diagonal-neighbors [])

(defn coordinates->generation-index
  "Return the 0-based generation index given (x, y) coordinate values and the row, column size
  according to the following formula: index = y * size + x"
  [{:keys [x y]}
   size]
  (-> (* y size)
      (+ x)))

(defn next-generation
  "The following rules are applied in order
  to generate the next generation.

  1. Any cell with fewer than two live neighbors dies.
  2. Any cell with two or three live neighbors lives on.
  3. Any cell with more than three live neighbors dies.
  4. Any dead cell with exactly three live neighbors returns to life."
  [size generation])
