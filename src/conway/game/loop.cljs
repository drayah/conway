(ns conway.game.loop
  (:require [cljs.core.async :refer [<! timeout]]
            [conway.protocols.world :as p-world]
            [conway.protocols.surface :as p-surface])
  (:require-macros [cljs.core.async :refer [go-loop]]))

(def wait-ms 1000)

(defn forever! [world surface]
  (go-loop [frame 1]
    (<! (timeout wait-ms))
    (p-surface/render! surface world)
    (->> (p-world/generation world)
         clj->js
         (str "LOOP-" frame ": ")
         js/console.log)
    (recur (inc frame))))

;(game-loop -> while true
; (render (generation world))
; (tick world)
