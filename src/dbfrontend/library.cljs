(ns ^:figwheel-hooks dbfrontend.library
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]
    [re-frame.core :as rf]
    [dbfrontend.tableutils :as tableutils]))

(enable-console-print!)

(def random_book {:id 13 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "BANG JAGO" :title "AMPUN BANG" :genre ["Drama"] :review 2 :description "SORRY BANG JAGO AMPUN BANG JAGO"})

(rf/reg-event-db
  :initialize
  (fn [_ _]
  {:books tableutils/books :modal-state false :book-id 13}))

(rf/reg-event-db
  :add-book
 (fn [db [_ new-book]]
  (assoc db :books (conj (db :books) new-book))))

(rf/reg-event-db
  :toggle-modal
 (fn [db]
   (assoc db :modal-state (not (db :modal-state)))))

(rf/reg-event-db
  :increment-id
 (fn [db]
   (update db :book-id inc)))

(rf/reg-sub
  :books
  (fn [db _]
   (:books db)))

(rf/reg-sub
  :modal-state
 (fn [db _]
   (:modal-state db)))

(rf/reg-sub
  :book-id
 (fn [db _]
   (:book-id db)))

(defn render-nav-bar []
   [ant/layout-header {:class-name "d-flex align-items-center"}
    [:a {:href "/"}

     [:img {:src "sutdLogoWhite.png" :alt "SUTD LOGO" :class-name "menu-logo p-1"}]]
    [:div {:style {:width "90vw"}}]])

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
  (let [data (rf/subscribe [:books])]
    (fn []
      [:div
       [ant/table
        {:columns (add-actions-column tableutils/columns data)
         :dataSource @data :pagination tableutils/pagination :row-key "id"
         }]])))

(defn dispatch-addbook [errors values]
  (def new-book values)
  (aset values "genre" (.split (get (js->clj values) "genre") " "))
  (aset values "id" @(rf/subscribe [:book-id]))
  (aset values "asin" 12345)
  (aset values "review" 0)
  (println new-book)
  (if (nil? errors)
    (rf/dispatch [:add-book (js->clj new-book)]))
  (rf/dispatch [:increment-id])
  (rf/dispatch [:toggle-modal]))

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
       [ant/form-item
        (ant/decorate-field addbook-antform "genre" {:rules [{:required true :message "Enter genre!"}]}
                            [ant/input {:placeholder "Genre"}])]
       [ant/form-item
        (ant/decorate-field addbook-antform "description" {:rules [{:required true :message "Enter description!"}]}
                            [ant/input {:placeholder "Description"}])]
       [ant/form-item
        (ant/decorate-field addbook-antform "image" {:rules [{:required true :message "Enter cover image!"}]}
                            [ant/input {:placeholder "Cover Image"}])]
       [:div.d-flex.justify-content-end
        [ant/button {:type "primary" :html-type "submit" :size "large" :style {:margin-top "20px" :width "150px"}} "Add Book"]
       ]])))

(defn render-addbook-form []
  (ant/create-form (addbook-form)))

(defn addbook-modal []
  [ant/modal {:title "Add Book" :visible @(rf/subscribe [:modal-state])
              :onOk #(rf/dispatch [:toggle-modal])
              :onCancel #(rf/dispatch [:toggle-modal])
              :okButtonProps {:style {:display "none"}}
              :cancelButtonProps {:style {:display "none"}}}
   [render-addbook-form]])

(defn dispatch-filterbook []
  (println "HELLO"))

(defn filter-form []
   (fn [props]
     (let [filter-antform (ant/get-form)
           submit-handler #(ant/validate-fields filter-antform dispatch-filterbook)]
       [ant/form {:on-submit #(do (.preventDefault %) (submit-handler))}
        [:div.d-flex.flex-row.justify-content-around.filter-inputs
         [ant/form-item {:name "title" :label "Title" }
          (ant/decorate-field filter-antform "title"
                              [ant/input {:placeholder "Title"}])]
         [ant/form-item {:name "title" :label "Author"}
          (ant/decorate-field filter-antform "author"
                              [ant/input {:placeholder "Author"}])]
         [ant/form-item {:name "title" :label "Genre"}
          (ant/decorate-field filter-antform "genre"
                              [ant/input {:placeholder "Genre"}])]]
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

