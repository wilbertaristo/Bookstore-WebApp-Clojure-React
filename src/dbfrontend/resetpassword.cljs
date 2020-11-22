(ns dbfrontend.resetpassword
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]))

(defn login-banner []
  [:div {:class-name "login-image-container"}
   [:div {:style {:max-width "34%" :float "right" :padding-top "10vh" :padding-right "7%" :display "none"}}
    [:h1 {:style {:font-size "40px" :color "white" :text-shadow "0px 0px 5px black" :text-align "right"}} "Discover the reading sanctuary."]]])

(defn launch-reset [errors values]
  (if (nil? errors)
    (println (get (js->clj values) "email"))
    (println errors)))

(defn ant-form []
  (fn [props]
    (let [resetpassword-antform (ant/get-form)
          submit-handler #(ant/validate-fields resetpassword-antform launch-reset)]
      [ant/form {:on-submit #(do (.preventDefault %) (submit-handler))}
       [ant/form-item
        (ant/decorate-field resetpassword-antform "email" {:rules [{:required true :message "Please input your email!"}, {:type "email", :message "Invalid email"}]}
                            [ant/input {:placeholder "Email" :size "large" :prefix (reagent/as-element [ant/icon {:type "user"}])}])]
       [ant/button {:type "primary" :html-type "submit" :size "large" :style {:margin-top "10px" :width "150px"}} "Send Email"]])))

(defn login-form []
  (ant/create-form (ant-form))
  )

(defn resetpassword-component []
  [:div {:style {:width "40vw" :background-color "#f5f5f5"}}
   [:div.d-flex.flex-column.align-items-end.pr-4.pt-3 {:style {:height "2%" :background-color "#f5f5f5"}}
    [:span {:class-name "d-flex"} "Not a member?"
     [:a {:class-name "d-flex ml-1" :href "/signup"} "Sign up now"]]
    ]
   [:div.d-flex.align-items-center.justify-content-center {:style {:height "98%"}}
    [:div.d-flex.flex-column {:style {:width "50%"}}
     [:h2 {:style {:font-weight "600" :margin-bottom "-15px"}} "Reset Password"]
     [ant/divider]
     [login-form]]
    ]
   ])

(defn render []
  [:div.vh-100.w-100.d-flex.flex-row
   [login-banner]
   [resetpassword-component]
   ])
