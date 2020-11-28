(ns ^:figwheel-hooks dbfrontend.library
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]
    [re-frame.core :as rf]
    [dbfrontend.tableutils :as tableutils]))

(enable-console-print!)

(def random_book {:id 13 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "BANG JAGO" :title "AMPUN BANG" :genre ["Drama"] :review 2 :description "SORRY BANG JAGO AMPUN BANG JAGO"})

(defn render-nav-bar []
   [ant/layout-header {:class-name "d-flex align-items-center"}
    [:a {:href "/"}

     [:img {:src "sutdLogoWhite.png" :alt "SUTD LOGO" :class-name "menu-logo p-1"}]]
    [:div {:style {:width "90vw"}}]])

(defn datatable []
  (let [data (rf/subscribe [:books])]
    (fn []
      [:div
       [ant/table
        {:columns tableutils/columns
         :dataSource @data :pagination tableutils/pagination :row-key "id"
         }]])))

(defn dispatch-addbook [errors values]
  (println (get @(rf/subscribe [:file-list]) 0))
  (println values)
;  (def new-book values)
;  (aset values "genre" (.split (get (js->clj values) "genre") " "))
;  (aset values "id" @(rf/subscribe [:book-id]))
;  (aset values "asin" 12345)
;  (aset values "review" 0)
;  (println new-book)
;  (if (nil? errors)
;    (rf/dispatch [:add-book (js->clj new-book)]))
;  (rf/dispatch [:increment-id])
;  (rf/dispatch [:toggle-modal])
  )

(defn upload-image-button []
  [ant/button {:icon "upload"} "Upload Book Cover Image"])

(defn handle-book-upload-onremove []
  (rf/dispatch [:update-file-list []]))

(defn handle-book-before-upload [file]
  (rf/dispatch [:update-file-list [file]])
  false)

(defn addbook-form []
  (fn [props]
    (let [addbook-antform (ant/get-form)
          submit-handler #(ant/validate-fields addbook-antform dispatch-addbook)]
      [ant/form {:on-submit #(do (.preventDefault %) (submit-handler))}
       [ant/form-item
        (ant/decorate-field addbook-antform "title" {:rules [{:required true :message "Enter title!"}]}
                            [ant/input {:placeholder "Title"}])]
       [ant/form-item
        (ant/decorate-field addbook-antform "author" {:rules [{:required true :message "Enter author!"}]}
                            [ant/input {:placeholder "Author"}])]
       [ant/input-group
        [ant/col {:span 6}
         [ant/form-item
          (ant/decorate-field addbook-antform "genre1"
                              [ant/input {:placeholder "Genre 1"}])]]
        [ant/col {:span 6}
         [ant/form-item
          (ant/decorate-field addbook-antform "genre2"
                              [ant/input {:placeholder "Genre 2"}])]]
        [ant/col {:span 6}
         [ant/form-item
          (ant/decorate-field addbook-antform "genre3"
                              [ant/input {:placeholder "Genre 3"}])]]
        [ant/col {:span 6}
         [ant/form-item
          (ant/decorate-field addbook-antform "genre4"
                              [ant/input {:placeholder "Genre 4"}])]]]
       [ant/form-item
        (ant/decorate-field addbook-antform "description" {:rules [{:required true :message "Enter description!"}]}
                            [ant/input {:placeholder "Description"}])]
       [ant/upload {:before-upload handle-book-before-upload :on-remove handle-book-upload-onremove :accept ".png,.jpeg,.jpg" :file-list @(rf/subscribe [:file-list]) :list-type "picture"} [upload-image-button]]
       [:div.d-flex.justify-content-end
        [ant/button {:type "primary" :html-type "submit" :size "large" :style {:margin-top "20px" :width "150px"}} "Add Book"]
       ]])))

(defn render-addbook-form []
  (ant/create-form (addbook-form)))

(defn addbook-modal []
  [ant/modal {:title "Add Book" :visible @(rf/subscribe [:modal-state])
              :onCancel #(rf/dispatch [:toggle-modal])
              :footer nil}
   [render-addbook-form]])

(defn handle-onchange [e]
  (def field-name (.-name (.-target e)))
  (def field-value (.-value (.-target e)))
  (cond
    (= field-name "title") (rf/dispatch [:update-title-search field-value])
    (= field-name "author") (rf/dispatch [:update-author-search field-value])
    (= field-name "genre") (rf/dispatch [:update-genre-search field-value])
    :else nil)
  )

(defn handle-search []
  (println @(rf/subscribe [:title-search]))
  (println @(rf/subscribe [:author-search]))
  (println @(rf/subscribe [:genre-search]))
  )

(defn filter-form []
   (fn [props]
     (let [filter-antform (ant/get-form)]
       [ant/form
        [:div.d-flex.flex-row.justify-content-around.filter-inputs
         [ant/form-item {:name "title" :label "Title" }
          (ant/decorate-field filter-antform "title"
                              [ant/input-search {:name "title" :placeholder "Title" :enter-button true :on-change handle-onchange :on-search handle-search :on-press-enter handle-search}])]
         [ant/form-item {:name "title" :label "Author"}
          (ant/decorate-field filter-antform "author"
                              [ant/input-search {:name "author" :placeholder "Author" :enter-button true :on-change handle-onchange :on-search handle-search :on-press-enter handle-search}])]
         [ant/form-item {:name "title" :label "Genre"}
          (ant/decorate-field filter-antform "genre"
                              [ant/input-search {:name "genre" :placeholder "Genre" :enter-button true :on-change handle-onchange :on-search handle-search :on-press-enter handle-search}])]]
        ])))

(defn render-filter-form []
  (ant/create-form (filter-form)))

(defn render-filters []
  [:div.d-flex.flex-column.mb-5.p-5 {:style {:background-color "white" :border-radius "10px"}}
   [:div.d-flex.justify-content-between.w-100
    [:div {:style {:width "18%"}}]
    [:h3 {:style {:color "dimgray" :letter-spacing "2px"}} "LIBRARY"]
    [:div.mr-5
     [ant/button {:class-name "mr-3" :size "large" :type "primary" :on-click #(rf/dispatch [:toggle-modal])} "Add Book"]]]
   [ant/divider]
   [render-filter-form]
   ]
  )

(defn render []
  [ant/layout {:class-name "vh-100"}
   [render-nav-bar]
   [:div.w-100.d-flex.justify-content-center.align-items-center {:style {:background-color "#f0f2f5"}}
    [ant/layout-content {:style {:padding "0 80px" :margin "80px 0"}}
     [render-filters]
     [:div.site-layout-content
      [addbook-modal]
      [datatable]]]
   ]])
