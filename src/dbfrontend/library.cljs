(ns dbfrontend.library
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]
    [dbfrontend.common :as common]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"
                          :searchText ""
                          :searchedColumn ""}))

(defn render-nav-bar []
  [:div
   [ant/layout-header {:class-name "d-flex align-items-center"}
    [:a {:href "/"}

     [:img {:src "sutdLogoWhite.png" :alt "SUTD LOGO" :class-name "menu-logo p-1"}]]
    [:div {:style {:width "90vw"}}]]])

(defn add-actions-column [columns data-atom]
  (conj columns
        {:title "Actions"
         :render
         #(reagent/as-element
           [ant/button {:icon "delete" :type "danger"
                        :on-click
                        (fn []
                          (reset! data-atom
                                  (remove (fn [d] (= (get (js->clj %2) "id")
                                                     (:id d))) @data-atom)))}])}))

(defn datatable []
  (let [data (reagent/atom common/people)]
    (fn []
      [:div
       [ant/table
        {:columns (add-actions-column common/columns data)
         :dataSource @data :pagination common/pagination :row-key "id"
         :row-selection
         {:on-change
          #(let [selected (js->clj %2 :keywordize-keys true)]
            (ant/message-info (str "You have selected: " (map :name selected))))}}]])))

(defn render []
  [:div
   [render-nav-bar]
   [datatable]])
