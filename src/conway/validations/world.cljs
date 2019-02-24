(ns conway.validations.world
  (:require [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [conway.logic.world :as l-world]))

(defn valid? [spec form]
  (s/valid? spec form))

(defn explain [spec form]
  (expound/expound-str spec form))

(def seed? (s/def ::seed l-world/valid-seed?))
