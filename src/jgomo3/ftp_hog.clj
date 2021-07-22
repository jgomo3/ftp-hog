(ns jgomo3.ftp-hog
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [org.apache.ftpserver FtpServer FtpServerFactory]
           [org.apache.ftpserver.listener ListenerFactory]
           [org.apache.ftpserver.usermanager PropertiesUserManagerFactory]))

(defn create-server [listener user-manager]
  (-> (doto (FtpServerFactory.)
        (.addListener "default" listener)
        (.setUserManager user-manager))
      (.createServer)))

(defn create-listener [port]
  (-> (doto (ListenerFactory.)
        (.setPort port))
      (.createListener)))

(defn create-user-manager [file]
  (-> (doto (PropertiesUserManagerFactory.)
        (.setFile file))
      (.createUserManager)))

(defn welcome [{:keys [port path user password] :as opts}]
  (let [msj-lines ["ftp-hog: Super simple ftp server so you can code."
                   ""
                   "This server is serving a non isolated folder."
                   "The only security are the limitiations the user you used"
                   "to execute this process has."
                   "Keep that in mind!"
                   ""
                   (str "Currently listening on port " port)
                   (str "And serving the path " path)
                   ""]
        msj (str/join "\n" msj-lines)]
    (println msj)))

(defn run [{:keys [port path user password] :as opts}]
  (do
    (welcome opts)
    (let [user-manager (-> "conf/users.properties"
                           io/resource
                           io/file
                           create-user-manager)
          listener (create-listener port)
          server (create-server listener user-manager)]
      (.start server))))
