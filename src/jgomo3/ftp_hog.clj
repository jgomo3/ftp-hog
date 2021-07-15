(ns jgomo3.ftp-hog
  (:require [clojure.java.io :as io])
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


(defn run [opts]
  (let [user-manager (-> "conf/users.properties"
                         io/resource
                         io/file
                         create-user-manager)
        listener (create-listener 2221)
        server (create-server listener user-manager)]
    (.start server)))
