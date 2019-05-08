(ns conway.logic.helpers)

(defn- ->coordinate [[cell x y]]
  {:cell cell
   :x    x
   :y    y})

(defn coords [& params]
  (map ->coordinate params))
