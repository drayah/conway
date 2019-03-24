(ns conway.components.game-world
  (:require [com.stuartsierra.component :as component]
            [conway.protocols.world :as p-world]
            [conway.validations.world :refer [valid? explain seed?]]
            [conway.js.common :refer [log!]]))

(defn- initialize-game-world!
  [{:keys [size seed] :as params}
   state]
  (and (valid? seed? params)
       (swap! state (constantly {:size       size
                                 :generation seed}))))

(defrecord GameWorld [size seed state]
  p-world/IWorld

  (initialize! [_]
    (let [params {:size size
                  :seed seed}]
      (or (initialize-game-world! params state)
          (throw (ex-info
                  "World initialization failed!"
                  {:error (explain seed? params)})))))

  (tick! [_]) ;calculate new generation

  (size [_] (:size @state))

  (generation [_] (:generation @state))

  component/Lifecycle

  (start [this]
    (log! "Initialize game world")
    (p-world/initialize! this)
    this)

  (stop [this] this))

(defn new-world [size seed]
  (map->GameWorld {:size  size
                   :seed  seed
                   :state (atom {})}))
