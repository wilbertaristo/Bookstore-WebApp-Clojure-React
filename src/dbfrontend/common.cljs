(ns dbfrontend.common)

(def form-style {:label-col {:span 6}
                 :wrapper-col {:span 13}})

(def pagination {:show-size-changer true
                 :page-size-options ["5" "10" "20"]
                 :show-total #(str "Total: " % " users")})

(defn comparison [data1 data2 field]
  (compare (get (js->clj data1 :keywordize-keys true) field)
           (get (js->clj data2 :keywordize-keys true) field)))

(def columns [{:title "Author" :dataIndex "author" :sorter #(comparison %1 %2 :author)}
              {:title "Title" :dataIndex "title" :sorter #(comparison %1 %2 :title)}
              {:title "Genre" :dataIndex "genre" :sorter #(comparison %1 %2 :genre) :render #()}
              {:title "Review" :dataIndex "review" :sorter #(comparison %1 %2 :review)}])

(def people [{:id 1 :author "Tracey Davidson" :title "Paris In The Rain" :genre "Romance" :review 5}
             {:id 2 :author "Pierre de Wiles" :title "Phantom of The Penthouse" :genre "Drama" :review 2}
             {:id 3 :author "Lydia Weaver" :title "Blue Moon" :genre "Romance" :review 7}
             {:id 4 :author "Willie Reynolds" :title "The Blood Seeker" :genre "Horror" :review 9}
             {:id 5 :author "Richard Perelman" :title "Afraid of The Dark" :genre "Thriller" :review 4}
             {:id 6 :author "Srinivasa Ramanujan" :title "The Conjuring" :genre "Horror" :review 8}
             {:id 7 :author "Zoe Cruz" :title "Ready Player Two" :genre "Sci-Fi" :review 6}
             {:id 8 :author "Adam Turing" :title "Pacific Rim" :genre "Sci-Fi" :review 10}
             {:id 9 :author "Adam Turing" :title "Underwater Missile" :genre "Sci-Fi" :review 8}
             {:id 10 :author "Adam Turing" :title "Hurricane Katrina" :genre "Documentary" :review 5}
             {:id 11 :author "Willie Reynolds" :title "Don't Look Up" :genre "Thriller" :review 6}
             {:id 12 :author "Zoe Cruz" :title "Club 33" :genre "Drama" :review 6}])
