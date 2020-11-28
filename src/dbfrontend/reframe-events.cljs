(ns dbfrontend.reframeevents
  (:require
    [re-frame.core :as rf]
    [dbfrontend.tableutils :as tableutils]))

;;;;;;;;;;;;;;;;;; INITIALIZE STATES ;;;;;;;;;;;;;;;;;;;;;;;;;;;
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

(rf/reg-sub
 :title-search
 (fn [db _]
   (:title-search db)))

(rf/reg-sub
 :author-search
 (fn [db _]
   (:author-search db)))

(rf/reg-sub
 :genre-search
 (fn [db _]
   (:genre-search db)))

(rf/reg-sub
 :file-list
 (fn [db _]
   (:file-list db)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;; EVENT DISPATCHER ;;;;;;;;;;;;;;;;;;;;;;;;;;
(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:books tableutils/books
    :modal-state false
    :book-id 13
    :title-search ""
    :author-search ""
    :genre-search ""
    :file-list []
    }))

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

(rf/reg-event-db
 :update-title-search
 (fn [db [_ new-title]]
   (assoc db :title-search new-title)))

(rf/reg-event-db
 :update-author-search
 (fn [db [_ new-author]]
   (assoc db :author-search new-author)))

(rf/reg-event-db
 :update-genre-search
 (fn [db [_ new-genre]]
   (assoc db :genre-search new-genre)))

(rf/reg-event-db
 :update-file-list
 (fn [db [_ image-file]]
   (assoc db :file-list image-file)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
