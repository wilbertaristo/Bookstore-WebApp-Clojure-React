(ns dbfrontend.signup
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]))

(defn login-banner []
  [:div {:class-name "login-image-container"}
   [:div {:style {:max-width "34%" :float "right" :padding-top "10vh" :padding-right "7%" :display "none"}}
    [:h1 {:style {:font-size "40px" :color "white" :text-shadow "0px 0px 5px black" :text-align "right"}} "Discover the reading sanctuary."]]])

(defn launch-signup [errors values]
  (if (nil? errors)
    (println (get (js->clj values) "email"))
    (println errors)))

(defn ant-form []
  (fn [props]
    (let [signup-antform (ant/get-form)
          submit-handler #(ant/validate-fields signup-antform launch-signup)]
      [ant/form {:on-submit #(do (.preventDefault %) (submit-handler))}
       [ant/form-item
        (ant/decorate-field signup-antform "email" {:rules [{:required true :message "Please input your email"}, {:type "email", :message "Invalid email"}]}
                            [ant/input {:placeholder "Email" :size "large" :prefix (reagent/as-element [ant/icon {:type "user"}])}])]
       [ant/form-item
        ;; validates that the password field is not empty
        (ant/decorate-field signup-antform "password" {:rules [{:required true :message "Please input your password!"}]}
                            [ant/input {:type "password" :placeholder "Password" :size "large" :prefix (reagent/as-element [ant/icon {:type "lock"}])}])]
       [ant/form-item
        ;; validates that the password field is not empty
        (ant/decorate-field signup-antform "confirmpassword" {:rules [{:required true :message "Please input your password again!"}]}
                            [ant/input {:type "password" :placeholder "Confirm Password" :size "large" :prefix (reagent/as-element [ant/icon {:type "lock"}])}])]
       [ant/button {:type "primary" :html-type "submit" :size "large" :style {:margin-top "10px" :width "150px"}} "Sign Up"]])))

(defn login-form []
  (ant/create-form (ant-form))
  )

(defn login-component []
  [:div {:style {:width "40vw" :background-color "#f5f5f5"}}
   [:div.d-flex.flex-column.align-items-end.pr-4.pt-3 {:style {:height "2%" :background-color "#f5f5f5"}}
    [:span {:class-name "d-flex"} "Already a member?"
     [:a {:class-name "d-flex ml-1" :href "/login"} "Sign in"]]
    ]
   [:div.d-flex.align-items-center.justify-content-center {:style {:height "98%"}}
    [:div.d-flex.flex-column {:style {:width "50%"}}
     [:h2 {:style {:font-weight "600" :margin-bottom "-15px"}} "Sign Up"]
     [ant/divider]
     [login-form]]
    ]
   ])

(defn render []
  [:div.vh-100.w-100.d-flex.flex-row
   [login-banner]
   [login-component]
   ])
