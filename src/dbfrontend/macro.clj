(ns dbfrontend.macro
  (:require
    [dotenv :refer [env app-env]]
    )
  )

(defmacro metadata_url []
  (env :URL_METADATA_MONGODB)
  )

(defmacro review_url []
  (env :URL_MYSQL_REVIEWS)
  )
