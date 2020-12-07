(ns dbfrontend.core
  (:require-macros [dbfrontend.macro])
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [re-frame.core :as rf]
    [reitit.frontend :as reit]
    [reitit.frontend.easy :as rfe]
    [reitit.coercion.spec :as rss]
    [spec-tools.data-spec :as ds]
    [dbfrontend.localstorage :as localstorage]
    [dbfrontend.reframeevents :as reframe-events]
    [dbfrontend.library :as library]
    [dbfrontend.viewbook :as viewbook]
    [dbfrontend.login :as login]
    [dbfrontend.resetpassword :as resetpassword]
    [dbfrontend.signup :as signup]
    [dbfrontend.reviews :as reviews]))

(enable-console-print!)

(println (dbfrontend.macro/metadata_url))
(println (dbfrontend.macro/review_url))

(defonce start-library (do (rf/dispatch-sync [:initialize]) true))

(defonce match (reagent/atom nil))

(defn current-page []
   (if @match
     (let [view (:view (:data @match))]
       [view @match]))
  )

(def routes
  [
    ["/" {:name ::defaultpage
          :view login/render}]
    ["/login" {:name ::login
               :view login/render}]
    ["/reset-password" {:name ::resetpassword
                        :view resetpassword/render}]
    ["/signup" {:name ::signup
                :view signup/render}]
    ["/library" {:name ::library
                 :view library/render}]
    ["/view-book/:asin" {:name ::viewbook
                         :parameters {:path {:asin string?}
                                      :query {(ds/opt :review) boolean?}}
                         :view viewbook/render}]
    ]
  )

(defn init! []
  (rfe/start!
   (reit/router routes {:data {:coercion rss/coercion}})
   (fn [m] (reset! match m))
   ;; set to false to enable HistoryAPI
   {:use-fragment true})
  (rd/render [current-page] (.getElementById js/document "app")))

(init!)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
