(ns conway.app
  (:require [conway.protocols.world :as p-world]
            [conway.components.game-world :as game]
            [conway.game.seeds :as seeds]
            [conway.game.loop :as g-loop]))

(defn init []
  (let [{:keys [size seed]} seeds/simple-configuration
        world               (game/empty-world)
        _                   (p-world/initialize! world size seed)]
    (js/console.log "Initialized world of size " (p-world/size world))

    (doseq [cell (p-world/generation world)]
      (js/console.log cell))

    (g-loop/forever! world nil)))
