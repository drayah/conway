(ns conway.components.game-world
  (:require [conway.protocols.world :as p-world]
            [conway.validations.world :refer [valid? explain seed?]]))

(defn- initialize-game-world!
  [{:keys [size seed] :as params}
   state]
  (and (valid? seed? params)
       (swap! state (constantly {:size      size
                                 :iteration seed}))))

(defrecord GameWorld [state]
  p-world/IWorld

  (initialize! [_ size seed]
    (let [params {:size size
                  :seed seed}]
      (or (initialize-game-world! params state)
          (throw (ex-info
                  "World initialization failed!"
                  {:error (explain seed? params)})))))

  (tick! [_])

  (current [_]))

(defn create-game-world []
  (->GameWorld (atom {})))
