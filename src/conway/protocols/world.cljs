(ns conway.protocols.world)

(defprotocol IWorld
  (initialize! [this size seed])
  (tick! [this])
  (current [this]))
