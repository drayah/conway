(ns conway.game.surface
  (:require [conway.protocols.world :as p-world]
            [conway.logic.cell :as logic.cell]))

(def dead-color "rgb(40, 40, 40)")
(def alive-color "rgb(33, 140, 235)")

(defn- width [render-context]
  render-context.canvas.width)

(defn- coordinate [index cell-size]
  (* index cell-size))

(defn- color [cell]
  (if (logic.cell/alive? cell)
    alive-color
    dead-color))

(defn- style! [render-context cell]
  (-> (.-fillStyle render-context)
      (set! (color cell))))

(defn- draw! [render-context x y size cell]
  (style! render-context cell)
  (.fillRect render-context x y size size))

(defn- clear! [render-context]
  (let [size (width render-context)]
    (.clearRect render-context 0 0 size size)))

(defn- render-cell!
  [{:keys [row-index cell-index cell world-size render-context]}]
  (let [cell-size (-> (width render-context)
                      (/ world-size))]
    (draw!
     render-context
     (coordinate cell-index cell-size)
     (coordinate row-index cell-size)
     cell-size
     cell)))

(defn- render-row!
  [{:keys [row-index row world-size render-context]}]
  (doseq [cell-index (range world-size)]
    (render-cell! {:row-index      row-index
                   :cell-index     cell-index
                   :cell           (nth row cell-index)
                   :world-size     world-size
                   :render-context render-context})))

(defn render!
  [world render-context]
  (let [world-size (p-world/size world)
        generation (p-world/generation world)
        rows       (partition world-size generation)]
    (clear! render-context)
    (doseq [row-index (range world-size)]
      (render-row! {:row-index      row-index
                    :row            (nth rows row-index)
                    :world-size     world-size
                    :render-context render-context}))))
