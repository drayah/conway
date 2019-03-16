(ns conway.dom.document)

(defn element-by-id! [id]
  (js/document.getElementById id))
