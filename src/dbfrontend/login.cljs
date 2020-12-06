(ns dbfrontend.login
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [re-frame.core :as rf]
    [antizer.reagent :as ant]))

(defn login-banner []
  [:div {:class-name "login-image-container"}
   [:div {:style {:max-width "34%" :float "right" :padding-top "10vh" :padding-right "7%" :display "none"}}
    [:h1 {:style {:font-size "40px" :color "white" :text-shadow "0px 0px 5px black" :text-align "right"}} "Discover the reading sanctuary."]]])

(defn launch-auth [errors values]
  (rf/dispatch [:set-login-error false])
  (rf/dispatch [:set-login-button-loading true])
  (if errors
    (println errors)
    (if (and (= "wilbert.aristo@hotmail.com" (.-email values)) (= "password" (.-password values)))
      (do
        (js/setTimeout #((set! (.. js/window -location -href) "/#/library")) 3500)
        )
      (js/setTimeout #((do (rf/dispatch [:set-login-error true]) (rf/dispatch [:set-login-button-loading false]))) 1500)
      )
    )
  )

(defn ant-form []
  (fn [props]
    (let [login-antform (ant/get-form)
          submit-handler #(ant/validate-fields login-antform launch-auth)]
      [ant/form {:on-submit #(do (.preventDefault %) (submit-handler))}
       [ant/form-item
        (ant/decorate-field login-antform "email" {:rules [{:required true :message "Please input your email!"}, {:type "email", :message "Invalid email"}]}
                            [ant/input {:placeholder "Email" :size "large" :prefix (reagent/as-element [ant/icon {:type "user"}])}])]
       [ant/form-item
        ;; validates that the password field is not empty
        (ant/decorate-field login-antform "password" {:rules [{:required true :message "Please input your password!"}]}
                            [ant/input {:type "password" :placeholder "Password" :size "large" :prefix (reagent/as-element [ant/icon {:type "lock"}])}])]
       [:a {:href "/#/reset-password" :style {:float "right" :margin-top "-10px" :color "gray"}} "Forgot Password?"]
       [ant/button {:type "primary" :html-type "submit" :size "large" :style {:margin-top "20px" :width "150px"} :loading @(rf/subscribe [:login-button-loading])} "Sign In"]])))

(defn login-form []
  (ant/create-form (ant-form))
  )

(defn login-component []
  [:div {:style {:width "40vw" :background-color "#f5f5f5"}}
   [:div.d-flex.flex-column.align-items-end.pr-4.pt-3 {:style {:height "2%" :background-color "#f5f5f5"}}
    [:span {:class-name "d-flex"} "Not a member?"
     [:a {:class-name "d-flex ml-1" :href "/#/signup"} "Sign up now"]]
    ]
   [:div.d-flex.align-items-center.justify-content-center {:style {:height "98%"}}
    [:div.d-flex.flex-column {:style {:width "50%"}}
     [:h2 {:style {:font-weight "600" :margin-bottom "-15px"}} "Sign In"]
     [ant/divider]
     (if @(rf/subscribe [:login-error])
       [ant/alert {:message "Login Error" :description "Incorrect email or password!" :type "error" :class-name "mb-3"}]
       )
     [login-form]]
    ]
   ])

(defn render []
  [:div.vh-100.w-100.d-flex.flex-row
    [login-banner]
    [login-component]
   ])
