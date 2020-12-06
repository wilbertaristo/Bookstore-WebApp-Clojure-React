(ns dbfrontend.reframeevents
  (:require
    [re-frame.core :as rf]
    [dbfrontend.localstorage :as localstorage]
    [dbfrontend.tableutils :as tableutils]))

;;;;;;;;;;;;;;;;;; INITIALIZE STATES ;;;;;;;;;;;;;;;;;;;;;;;;;;;
(rf/reg-sub
 :books
 (fn [db _]
   (:books db)))

(rf/reg-sub
  :selected-book
 (fn [db _]
   (:selected-book db)))

(rf/reg-sub
 :selected-book-reviews
 (fn [db _]
   (:selected-book-reviews db)))

(rf/reg-sub
 :related-books
 (fn [db _]
   (:related-books db)))

(rf/reg-sub
 :modal-state
 (fn [db _]
   (:modal-state db)))

(rf/reg-sub
 :addreview-modal-state
 (fn [db _]
   (:addreview-modal-state db)))

(rf/reg-sub
 :recommendation-modal-state
 (fn [db _]
   (:recommendation-modal-state db)))

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

(rf/reg-sub
 :library-table-loading
 (fn [db _]
   (:library-table-loading db)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;; EVENT DISPATCHER ;;;;;;;;;;;;;;;;;;;;;;;;;;
(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:books nil
    :selected-book nil
    :selected-book-reviews nil
    :related-books nil
    :modal-state false
    :addreview-modal-state false
    :recommendation-modal-state false
    :book-id 13
    :title-search ""
    :author-search ""
    :genre-search ""
    :file-list []
    :library-table-loading true
    }))

(rf/reg-event-db
 :update-books
 (fn [db [_ new-book-list]]
   (assoc db :books new-book-list)))

(rf/reg-event-db
 :update-selected-book
 (fn [db [_ new-book]]
   (assoc db :selected-book new-book)))

(rf/reg-event-db
 :update-selected-book-reviews
 (fn [db [_ review-list]]
   (assoc db :selected-book-reviews review-list)))

(rf/reg-event-db
 :update-related-books
 (fn [db [_ new-book-list]]
   (assoc db :related-books new-book-list)))

(rf/reg-event-db
 :toggle-modal
 (fn [db [_ state]]
   (assoc db :modal-state state)))

(rf/reg-event-db
 :toggle-addreview-modal
 (fn [db [_ state]]
   (assoc db :addreview-modal-state state)))

(rf/reg-event-db
 :toggle-recommendation-modal
 (fn [db [_ state]]
   (assoc db :recommendation-modal-state state)))

(rf/reg-event-db
 :set-table-loading
 (fn [db [_ state]]
   (assoc db :library-table-loading state)))

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
