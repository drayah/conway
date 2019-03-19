(ns conway.components.surface
  (:require [conway.protocols.surface :as p-surface]
            [conway.protocols.world :as p-world]))

(def dead-cell 0)

(defn- render-cells [cells context]
  (js/console.log "rendering cells..."))

(defrecord Surface [state]
  p-surface/ISurface

  (render! [_ world]
    (let [cells   (:cells @state)
          diff    (map - (p-world/generation world) cells)
          updated (map + cells diff)]
      (swap! state assoc :cells updated)
      (render-cells (:cells @state) (:render-context @state)))))

(defn dead [context size]
  (let [cell-count (* size size)]
    (->Surface (atom {:render-context context
                      :size           size
                      :cells          (into [] (repeat cell-count dead-cell))}))))
