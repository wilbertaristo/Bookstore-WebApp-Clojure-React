(ns dbfrontend.tableutils
  (:require
    [reagent.core :as reagent]
    [reagent.dom :as rd]
    [antizer.reagent :as ant]))

(def form-style {:label-col {:span 6}
                 :wrapper-col {:span 13}})

(def pagination {:show-size-changer true
                 :page-size-options ["5" "10" "20"]
                 :show-total #(str "Total: " % " books")})

(defn comparison [data1 data2 field]
  (compare (get (js->clj data1 :keywordize-keys true) field)
           (get (js->clj data2 :keywordize-keys true) field)))

(defn genre-display [genres]
  (.sort genres)
  (for [i (range (count genres))] (reagent/as-element [ant/tag {:key (aget genres i)} (aget genres i)])))

(defn review-display [review]
  [ant/rate {:allowHalf true :disabled true :default-value review}])

(defn cover-display [url book]
  (def book_asin (get (js->clj book) "asin"))
  [:a {:href (str "/view-book?asin=" book_asin)}
   [:img {:class-name "book-covers" :src url :width "100px"}]
   ]
  )

(defn action-display [asin]
  [:a {:href (str "/view-book?asin=" asin)} "Write a review"])

(def description
  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aliquam ultrices sagittis orci a scelerisque. Libero volutpat sed cras ornare arcu dui vivamus arcu felis. Erat nam at lectus urna duis convallis. Arcu ac tortor dignissim convallis aenean et tortor. Amet mauris commodo quis imperdiet massa tincidunt nunc pulvinar. Lectus nulla at volutpat diam ut. Elementum tempus egestas sed sed risus pretium quam. Sit amet venenatis urna cursus eget nunc scelerisque viverra. Sem et tortor consequat id.
  Sit amet consectetur adipiscing elit pellentesque. Adipiscing enim eu turpis egestas pretium. Vulputate dignissim suspendisse in est. Dolor sit amet consectetur adipiscing. Justo donec enim diam vulputate ut pharetra sit amet aliquam. Vitae justo eget magna fermentum iaculis eu non diam. Aliquam sem et tortor consequat id porta nibh venenatis. Nunc pulvinar sapien et ligula ullamcorper malesuada proin. A lacus vestibulum sed arcu. Lacus suspendisse faucibus interdum posuere lorem ipsum dolor. Aliquam ultrices sagittis orci a scelerisque purus semper eget duis. Vitae justo eget magna fermentum iaculis eu non diam. Phasellus faucibus scelerisque eleifend donec. Dui vivamus arcu felis bibendum ut. Tristique senectus et netus et malesuada fames. Dui faucibus in ornare quam viverra.
  Ultrices dui sapien eget mi proin. Sagittis aliquam malesuada bibendum arcu vitae elementum curabitur. Neque convallis a cras semper auctor neque vitae. Faucibus et molestie ac feugiat sed lectus vestibulum mattis ullamcorper. Viverra accumsan in nisl nisi scelerisque eu ultrices vitae auctor. Pharetra pharetra massa massa ultricies mi quis hendrerit dolor. Ut morbi tincidunt augue interdum velit euismod. Suspendisse ultrices gravida dictum fusce ut placerat orci. Et malesuada fames ac turpis. Nisl condimentum id venenatis a condimentum vitae. Consectetur libero id faucibus nisl.
  Velit euismod in pellentesque massa placerat duis ultricies. Orci eu lobortis elementum nibh tellus molestie. Urna et pharetra pharetra massa massa. Vitae congue mauris rhoncus aenean vel elit scelerisque. Sit amet porttitor eget dolor morbi non arcu risus quis. Sed elementum tempus egestas sed sed risus pretium quam vulputate. At auctor urna nunc id cursus metus aliquam. Molestie a iaculis at erat pellentesque adipiscing commodo elit. Velit aliquet sagittis id consectetur purus ut faucibus pulvinar. Nam libero justo laoreet sit. Integer enim neque volutpat ac tincidunt. Tellus elementum sagittis vitae et leo duis ut.")

(def columns [{:title "Cover" :width 150 :dataIndex "image" :render #(reagent/as-element (cover-display % %2))}
              {:title "Author" :dataIndex "author" :sorter #(comparison %1 %2 :author)}
              {:title "Title" :dataIndex "title" :sorter #(comparison %1 %2 :title)}
              {:title "Genre" :dataIndex "genre" :render #(reagent/as-element (genre-display %))}
              {:title "Review" :dataIndex "review" :sorter #(comparison %1 %2 :review) :render #(reagent/as-element (review-display %))}
              {:title "Action" :dataIndex "asin" :render #(reagent/as-element (action-display %))}
              ])

(def books [{:id 1 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Tracey Davidson" :title "Paris In The Rain" :genre ["Romance" "Horror"] :review 5 :description description}
             {:id 2 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Pierre de Wiles" :title "Phantom of The Penthouse" :genre ["Drama" "Sci-Fi"] :review 2.5 :description description}
             {:id 3 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Lydia Weaver" :title "Blue Moon" :genre ["Romance"] :review 3 :description description}
             {:id 4 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Willie Reynolds" :title "The Blood Seeker" :genre ["Horror"] :review 3.5 :description description}
             {:id 5 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Richard Perelman" :title "Afraid of The Dark" :genre ["Thriller"] :review 4 :description description}
             {:id 6 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Srinivasa Ramanujan" :title "The Conjuring" :genre ["Horror"] :review 1.5 :description description}
             {:id 7 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Zoe Cruz" :title "Ready Player Two" :genre ["Sci-Fi"] :review 1 :description description}
             {:id 8 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Adam Turing" :title "Pacific Rim" :genre ["Sci-Fi"] :review 2.5 :description description}
             {:id 9 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Adam Turing" :title "Underwater Missile" :genre ["Sci-Fi"] :review 3 :description description}
             {:id 10 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Adam Turing" :title "Hurricane Katrina" :genre ["Documentary"] :review 4.5 :description description}
             {:id 11 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Willie Reynolds" :title "Don't Look Up" :genre ["Thriller"] :review 3 :description description}
             {:id 12 :asin 12345 :image "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png" :author "Zoe Cruz" :title "Club 33" :genre ["Drama"] :review 4 :description description}])
