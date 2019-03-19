(ns conway.app
  (:require [conway.protocols.world :as p-world]
            [conway.components.game-world :as c-game-world]
            [conway.components.surface :as c-surface]
            [conway.game.seeds :as seeds]
            [conway.dom.document :as d]
            [conway.game.loop :as g-loop]))

(defn- render-context! [canvas-id context-type]
  (-> (d/element-by-id! canvas-id)
      (.getContext context-type)))

(defn- run-game! []
  (let [{:keys [size seed]} seeds/simple-configuration
        context             (render-context! "game-canvas" "2d")
        surface             (c-surface/dead context size)
        world               (c-game-world/empty-world)
        _                   (p-world/initialize! world size seed)]
    (js/console.log "Initialized world of size " (p-world/size world))
    (g-loop/forever! world surface)))

(defn init! []
  (js/window.addEventListener "DOMContentLoaded" (fn [event] (run-game!))))
