(ns conway.game.loop
  (:require [cljs.core.async :refer [<! timeout]]
            [conway.protocols.world :as p-world])
  (:require-macros [cljs.core.async :refer [go-loop]]))

(def wait-ms 1000)

(defn forever! [world surface]
  (go-loop [counter 1]
    (<! (timeout wait-ms))
    (->> (p-world/size world)
         (str "LOOP-" counter": world size = ")
         js/console.log)
    (recur (inc counter))))

;(game-loop -> while true
; (render (generation world))
; (tick world)