(ns file-list.core
  (:require [fs.core :as fs]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:import java.util.Date
           java.io.File))

(import java.io.File)

(def path "/etc")

(defn get-file-info [file]
    (list (.getPath file) (Date. (.lastModified file)) (.getName file)))

(defn list-files [dir]
  (for [file (.listFiles (io/file dir)) :when (not (nil? file))]
    (if (.isFile file)
      (get-file-info file)
      (flatten (list-files file)))))

(list-files "/etc")

(defn -main
  [path-string]
  (with-open [out-file (io/writer "out-file.csv")]
    (csv/write-csv out-file
                   (list-files path-string))))
