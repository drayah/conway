(ns conway.game.surface
  (:require [conway.protocols.world :as p-world]))

(defn- render-row! [row-index rows]
  (let [cells (nth rows row-index)]
    (js/console.log (str "row: " row-index ", cells: " (clj->js cells)))))

(defn render! [world render-context]
  (let [size       (p-world/size world)
        generation (p-world/generation world)
        rows       (partition size generation)]
    (doseq [row-index (range size)]
      (render-row! row-index rows))))
