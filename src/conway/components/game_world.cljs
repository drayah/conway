(ns conway.components.game-world
  (:require [conway.protocols.world :as p-world]
            [conway.validations.world :refer [valid? explain seed?]]))

(defrecord GameWorld [state]
  p-world/IWorld

  (initialize! [_ size seed]
    (let [params {:size size
                  :seed seed}]
      (or (valid? seed? params)
          (throw (ex-info
                  "World initialization failed!"
                  {:error (explain seed? params)})))))

  (tick! [_])

  (current [_]))

(defn create-game-world []
  (->GameWorld (atom [])))
