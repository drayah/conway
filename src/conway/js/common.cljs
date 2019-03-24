(ns conway.js.common)

(def console js/console)
(def document js/document)
(def window js/window)

(defn log! [message]
  (-> console
      (.log message)))

(defn element-by-id! [id]
  (-> document
      (.getElementById id)))

(defn add-event-listener! [target event-type callback-fn]
  (-> target
      (.addEventListener event-type callback-fn)))

(defn request-animation-frame! [animate-fn]
  (-> window
      (.requestAnimationFrame animate-fn)))
