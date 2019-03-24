(ns conway.protocols.world)

(defprotocol IWorld
  (initialize! [this])
  (tick! [this])
  (size [this])
  (generation [this]))
