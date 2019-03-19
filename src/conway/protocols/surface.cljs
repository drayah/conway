(ns conway.protocols.surface)

(defprotocol ISurface
  (render! [this world]))
