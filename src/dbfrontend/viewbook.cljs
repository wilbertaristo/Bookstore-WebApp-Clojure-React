(ns dbfrontend.viewbook
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]
    [dbfrontend.library :as library]
    [dbfrontend.tableutils :as tableutils]
    [re-frame.core :as rf]))

(def selected_book {:id 13 :asin 12345 :image "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/action-thriller-book-cover-design-template-3675ae3e3ac7ee095fc793ab61b812cc_screen.jpg?ts=1588152105" :author "Nora Barrett" :title "The King Of Drugs" :genre ["Documentary", "Mystery"] :review 4.5 :total-reviews 1874 :description tableutils/description})

(defn render-recommendation []
  [:div.d-flex.flex-wrap
   (for [book tableutils/image-urls]
      [:a {:href (str "/view-book?asin=" (:asin book))}
       [:img {:class-name "book-covers mr-2 mb-2" :src (:imurl book) :width "140px"}]
       ]
     )
   ]
  )

(defn recommendation-modal []
  [ant/modal {:title "Similar Books" :visible @(rf/subscribe [:recommendation-modal-state])
              :onCancel #(rf/dispatch [:toggle-recommendation-modal])
              :footer nil
              :width 650
              }
   [render-recommendation]])

(defn render-metadatas []
  [:div.book-metadatas
   [:div.d-flex.flex-row.w-100.justify-content-between
    [:h1 {:style {:font-weight 600 :color "#303030"}} (:title selected_book)]
    [:div.d-flex.justify-content-end
     [ant/button {:class-name "mt-3" :type "secondary" :on-click #(rf/dispatch [:toggle-recommendation-modal])} "View Similar Books"]]
    ]
   [:div.d-flex.align-items-center.mb-3
    [ant/rate {:disabled true :allow-half true :default-value (:review selected_book)}]
    [:span {:class-name "ant-rate-text pt-1" :style {:color "gray"}} (str (:review selected_book) " out of 5 from " (:total-reviews selected_book) " reviews")]
    ]
   [:div.d-flex.align-items-center.mb-2
    [:span {:style {:color "#303030" :font-weight 500 :margin-right "1%"}} "Author:"]
    [:span {:style {:color "gray"}} (:author selected_book)]
    ]
   [:div.d-flex.align-items-center.mb-2
    [:span {:style {:color "#303030" :font-weight 500 :margin-right "1%"}} "Genre:"]
    (for [i (range (count (:genre selected_book)))] (reagent/as-element [ant/tag {:key (get (:genre selected_book) i)} (get (:genre selected_book) i)]))
    ]
   [:span {:style {:color "#303030" :font-weight 500}} "Description:"]
   [ant/input-text-area {:class-name "mt-2" :autosize {:min-rows 22 :max-rows 22} :default-value (:description selected_book) :disabled true}]
   ]
  )

(defn render-review-description [item]
  [:div.d-flex.align-items-center
   [ant/rate {:disabled true :allow-half true :default-value (.-overall item)}]
   [:span {:class-name "ant-rate-text pt-1" :style {:color "gray"}} (str "from " (.-reviewerName item))]
   ]
  )

(defn render-review-item [item]
  (reagent/as-element
   [ant/list-item
    [ant/list-item-meta {:avatar (reagent/as-element [ant/avatar {:src (.-avatar item )}]) :title (.-summary item) :description (reagent/as-element (render-review-description item))}]
    [ant/input-text-area {:autosize {:min-rows 4 :max-rows 4} :default-value (.-reviewText item) :disabled true}]
    ]
   )
  )

(defn render-reviews []
  [ant/list {:itemLayout "vertical" :pagination {:page-size 3} :dataSource tableutils/reviews :render-item (reagent/as-element render-review-item)}])

(defn dispatch-addreview [errors values]
  (println values)
  )

(defn addreview-form []
  (fn [props]
    (let [addreview-antform (ant/get-form)
          submit-handler #(ant/validate-fields addreview-antform dispatch-addreview)]
      [ant/form {:on-submit #(do (.preventDefault %) (submit-handler)) :class-name "add-review-form"}
       [:div.d-flex.flex-row
        [:div.d-flex.flex-column.mr-3
         [:img {:src (:image selected_book) :style {:object-fit "cover" :width "242px"}}]
         ]
        [:div.d-flex.flex-column.w-100
         [:h3 {:style {:font-weight 600 :color "#303030" :margin-bottom 0}} (:title selected_book)]
         [ant/form-item
          (ant/decorate-field addreview-antform "rating" {:rules [{:required true :message "Enter rating!"}]}
                              [ant/rate {:allow-half true}])]
         [ant/form-item {:class-name "mb-2"}
          (ant/decorate-field addreview-antform "summary" {:rules [{:required true :message "Enter review summary!"}]}
                              [ant/input {:placeholder "Review Summary"}])]
         [ant/form-item
          (ant/decorate-field addreview-antform "description" {:rules [{:required true :message "Enter review description!"}]}
                              [ant/input-text-area {:autosize {:min-rows 12 :max-rows 12} :placeholder "Review Description"}])]
         ]
        ]
       [:div.d-flex.justify-content-end
        [ant/button {:type "primary" :html-type "submit" :size "large" :style {:margin-top "20px" :width "150px"}} "Add Review"]
        ]]))
  )

(defn render-addbook-form []
  (ant/create-form (addreview-form)))

(defn addreview-modal []
  [ant/modal {:title "Add Review" :visible @(rf/subscribe [:addreview-modal-state])
              :onCancel #(rf/dispatch [:toggle-addreview-modal])
              :footer nil
              :width 700
              }
   [render-addbook-form]])

(defn render []
  [ant/layout {:class-name "vh-100" :style {:background-color "#f0f2f5"}}
    [library/render-nav-bar]
    [ant/layout-content {:style {:margin "60px" :background-color "#fff"} :class-name "d-flex flex-row"}
     [:div.p-5.d-flex.flex-row {:style {:width "65%"}}
      [:div.pt-2.d-flex.flex-column
       [:img {:src (:image selected_book) :width "410px"}]
       ]
      [:div.d-flex.flex-column.w-100.ml-5
       [render-metadatas]
       [recommendation-modal]
       ]
      ]
     [:div.pt-3.pb-3
      [ant/divider {:type "vertical" :class-name "h-100 m-0"}]
      ]
     [:div.p-5.d-flex.flex-row.h-100 {:style {:width "35%"}}
      [:div.d-flex.flex-column.w-100.review-section
       [:div.d-flex.flex-row.w-100.justify-content-between
        [:h3 {:style {:color "#303030" :font-weight 500}} "Reviews"]
        [:div.d-flex.justify-content-end
         [ant/button {:class-name "mt-1" :type "primary" :on-click #(rf/dispatch [:toggle-addreview-modal])} "Add Review"]]
        ]
       [ant/divider {:class-name "mt-2 mb-2"}]
       [addreview-modal]
       [render-reviews]
       ]]
     ]
   ])
