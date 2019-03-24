(ns conway.game.loop
  (:require [conway.protocols.world :as p-world]
            [conway.game.surface :as g-surface]
            [conway.js.common :as jsc :refer [log!]]))

(def components (atom {}))

(defn- animate! [timestamp]
  (let [{:keys [world render-context]} @components]
    (jsc/request-animation-frame! animate!)
    (log! (str "LOOP - " timestamp))
    (g-surface/render! world render-context)
    (p-world/tick! world)))

(defn forever! [world render-context]
  (swap! components assoc :world world :render-context render-context)
  (animate! 0))

;(game-loop -> while true
; (render (generation world))
; (tick world)
