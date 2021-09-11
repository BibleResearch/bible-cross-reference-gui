
(defn show-search-results [{:keys [flash] :as request}]
  (let [query (get-in request [:params :query])
        results (if (not-empty query)
                  (get-search-results query)
                  ())]
    (layout/render request "home.html"
                   (merge {:results results
                           :query query}
                          (select-keys flash [:name :message :errors])))))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-formats]}
   ["/" {:get show-search-results
         :post show-search-results}]
   ["/about" {:get about-page}]])
