(ns conway.components.canvas
  (:require [com.stuartsierra.component :as component]
            [conway.js.common :as jsc :refer [log!]]))

(defn- render-context! [canvas-id context-type]
  (-> (jsc/element-by-id! canvas-id)
      (.getContext context-type)))

(defrecord Canvas [canvas-id context-type render-context]
  component/Lifecycle

  (start [this]
    (log! (str "Start " context-type " RenderContext for canvas [" canvas-id "]"))
    (assoc this :render-context (render-context! canvas-id context-type)))

  (stop [this]
    (assoc this :render-context nil)))

(defn new-canvas [canvas-id context-type]
  (map->Canvas {:canvas-id    canvas-id
                :context-type context-type}))
