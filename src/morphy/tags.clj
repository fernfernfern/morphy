(ns morphy.tags)

(defn generate-index-pages [{{:keys [site/tags]} :site/model :as context}]
  (-> context
      ;; for each tag
      ;; add a page to templatable pages for each tag
      ;; assume /tag/tag-name path
      ;; assume no layout?
      ;;   maybe a default tag layout?
      ;;   maybe look for layout in /tags/_layout.mustache
      ;;   this could use the same layout, just replacing the content with a partial

      ))
