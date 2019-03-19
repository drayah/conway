(ns conway.game.loop
  (:require [cljs.core.async :refer [<! timeout]]
            [conway.protocols.world :as p-world]
            [conway.game.surface :as g-surface])
  (:require-macros [cljs.core.async :refer [go-loop]]))

(def wait-ms 1000)

(defn forever! [world render-context]
  (js/console.log render-context)

  (go-loop [frame 1]
    (<! (timeout wait-ms))
    (-> (str "LOOP-" frame)
        js/console.log)
    (g-surface/render! world render-context)
    (p-world/tick! world)
    (recur (inc frame))))

;(game-loop -> while true
; (render (generation world))
; (tick world)
