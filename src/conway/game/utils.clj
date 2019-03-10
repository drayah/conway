(ns conway.game.utils)

(defmacro game-loop
  "A game-loop runs its provided body forever
   while also calculating how long each iteration takes
   to complete."
  [body]
  `(do ~body))
