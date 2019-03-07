(ns conway.app
  (:require [conway.protocols.world :as p-world]
            [conway.components.game-world :as game]))

(def size 3)

(def seed [0 0 0
           0 0 0
           0 0 0])

(def world (game/empty-world))

(defn init []
  (let [initial-world (p-world/initialize! world size seed)]
    (js/console.log "Initialized world of size " (:size initial-world))
    (doseq [cell (:iteration initial-world)]
      (js/console.log cell))))
