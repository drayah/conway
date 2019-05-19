(ns conway.app
  (:require [com.stuartsierra.component :as component]
            [conway.protocols.world :as p-world]
            [conway.components.game-world :as c-game-world]
            [conway.components.canvas :as c-canvas]
            [conway.components.simulation :as c-simulation]
            [conway.game.seeds :as seeds]
            [conway.logic.seed :as logic.seed]
            [conway.js.common :as jsc :refer [log!]]))

(def simple-config (-> seeds/simple-configuration
                       (logic.seed/resize 15)))

(def system (component/system-map
             :world (c-game-world/new-world (:size simple-config) (:seed simple-config))
             :canvas (c-canvas/new-canvas "game-canvas" "2d")
             :simulation (component/using
                          (c-simulation/new-simulation)
                          [:world :canvas])))

(defn- start-system! []
  (log! "Start system")
  (component/start system))

(defn init! []
  (jsc/add-event-listener! jsc/window "DOMContentLoaded" (fn [event] (start-system!))))
