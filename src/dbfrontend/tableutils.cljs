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

(def review-text "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur mollis tortor sem, at sagittis nunc eleifend vel. Mauris laoreet, odio nec mattis venenatis, mauris felis efficitur urna, et eleifend ipsum dui vel ligula. Fusce vitae scelerisque magna, a convallis odio. ")

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

(def reviews [{:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 4, :summary "Well written Book", :reviewerName "Daryll Wong", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 3.5, :summary "It was okay", :reviewerName "Zachary Teo", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 5, :summary "Very good book", :reviewerName "Dana White", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 4.5, :summary "Get a copy!", :reviewerName "Sam Ojer", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 2, :summary "Underwhelming", :reviewerName "Hakeem Olajuwon", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 4, :summary "Very insightful book", :reviewerName "Kobe Bryant", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 3, :summary "It's mediocre", :reviewerName "James Song", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 3.5, :summary "Not bad", :reviewerName "Kim Dae Woon", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 2.5, :summary "Not really good", :reviewerName "Son Jung", :reviewText review-text, :unixReviewTime "1399038400"}
              {:avatar "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png", :overall 5, :summary "Book of the century", :reviewerName "Peter Smith", :reviewText review-text, :unixReviewTime "1399038400"}
              ])

(def image-urls [{:asin 12345 :imurl "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/contemporary-fiction-night-time-book-cover-design-template-1be47835c3058eb42211574e0c4ed8bf_screen.jpg?ts=1594616847"}
                 {:asin 12312 :imurl "https://static-cse.canva.com/blob/142533/Red-and-Beige-Cute-Illustration-Young-Adult-Book-Cover.jpg"}
                 {:asin 11145 :imurl "https://www.designforwriters.com/wp-content/uploads/2017/10/design-for-writers-book-cover-tf-2-a-million-to-one.jpg"}
                 {:asin 14242 :imurl "https://marketplace.canva.com/EAD7YH8bebE/1/0/251w/canva-white-bold-text-thriller-mystery-book-cover-CejxvxrTCyg.jpg"}
                 {:asin 13512 :imurl "https://marketplace.canva.com/EAD7WdjmU7I/1/0/251w/canva-wolf-eye-photo-thriller-mystery-book-cover-Zj3QkObDyKs.jpg"}
                 {:asin 12332 :imurl "https://www.designwizard.com/wp-content/uploads/2019/07/20-Conviction-Kelly-Loy-Gilbert-Book-Cover-Ideas.jpg"}
                 {:asin 15321 :imurl "https://marketplace.canva.com/EAD7WVju_K0/1/0/251w/canva-red-and-black-outline-romance-chick-lit-book-cover-v3hygqoyuXc.jpg"}
                 {:asin 11423 :imurl "https://bukovero.com/wp-content/uploads/2016/07/Harry_Potter_and_the_Cursed_Child_Special_Rehearsal_Edition_Book_Cover.jpg"}
                 {:asin 13412 :imurl "https://e3t6q7b4.stackpathcdn.com/wp-content/uploads/2018/09/five-feet-apart-9781534437333_hr-679x1024.jpg"}
                 {:asin 21231 :imurl "https://lithub.com/wp-content/uploads/sites/3/2019/07/lady-in-the-lake1.jpg"}
                 ])
