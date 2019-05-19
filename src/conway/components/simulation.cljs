(ns conway.components.simulation
  (:require [com.stuartsierra.component :as component]
            [conway.protocols.world :as p-world]
            [conway.game.surface :as g-surface]
            [conway.js.common :as jsc :refer [log!]]))

(defn- animate! [world canvas timestamp]
  (let [animate-fn (partial animate! world canvas)]
    (jsc/request-animation-frame! animate-fn)
    ;(log! (str "LOOP - " timestamp))
    (g-surface/render! world (:render-context canvas))
    (p-world/tick! world)))

(defrecord Simulation [world canvas]
  component/Lifecycle

  (start [this]
    (log! "Start simulation")
    (animate! world canvas 0)
    this)

  (stop [this] this))

(defn new-simulation []
  (map->Simulation {}))
