(ns conway.app
  (:require [conway.protocols.world :as p-world]
            [conway.components.game-world :as c-game-world]
            [conway.game.seeds :as seeds]
            [conway.js.common :as jsc :refer [log!]]
            [conway.game.loop :as g-loop]))

(defn- render-context! [canvas-id context-type]
  (-> (jsc/element-by-id! canvas-id)
      (.getContext context-type)))

(defn- run-game! []
  (let [{:keys [size seed]} seeds/simple-configuration
        render-context      (render-context! "game-canvas" "2d")
        world               (c-game-world/empty-world)
        _                   (p-world/initialize! world size seed)]
    (log! (str "Initialized world of size " (p-world/size world)))
    (g-loop/forever! world render-context)))

(defn init! []
  (jsc/add-event-listener! jsc/window "DOMContentLoaded" (fn [event] (run-game!))))
