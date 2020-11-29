(ns dbfrontend.core
    (:require 
              [reagent.core :as reagent]
              [reagent.dom :as rd]
              [re-frame.core :as rf]
              [dbfrontend.library :as library]
              [dbfrontend.viewbook :as viewbook]
              [dbfrontend.reframeevents :as reframe-events]
              [dbfrontend.login :as login]
              [dbfrontend.resetpassword :as resetpassword]
              [dbfrontend.signup :as signup]
              [dbfrontend.reviews :as reviews]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"
                          :searchText ""
                          :searchedColumn ""}))

(defn render [page]
  (rd/render [page] (. js/document (getElementById "app"))))

(defonce start-library (do (rf/dispatch-sync [:initialize]) true))

(render viewbook/render)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
