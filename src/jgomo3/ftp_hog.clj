(ns jgomo3.ftp-hog
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [org.apache.ftpserver FtpServer FtpServerFactory]
           [org.apache.ftpserver.listener ListenerFactory]
           [org.apache.ftpserver.ssl SslConfigurationFactory]
           [org.apache.ftpserver.usermanager PropertiesUserManagerFactory])
  (:gen-class))

(defn create-server [listener user-manager]
  (-> (doto (FtpServerFactory.)
        (.addListener "default" listener)
        (.setUserManager user-manager))
      (.createServer)))

;; (defn create-listener [port]
;;   (-> (doto (ListenerFactory.)
;;         (.setPort port))
;;       (.createListener)))

(defn create-listener [port & {ssl :ssl}]
  (-> (when-let [fact (ListenerFactory.)]
        (.setPort fact port)
        (if (some? ssl)
          (do (.setSslConfiguration fact ssl)
              (.setImplicitSsl fact true)))
        fact)
      (.createListener)))

(defn create-ssl-configuration [file password]
  (-> (doto (SslConfigurationFactory.)
        (.setKeystoreFile file)
        (.setKeystorePassword password))
      (.createSslConfiguration)))

(defn create-user-manager [file]
  (-> (doto (PropertiesUserManagerFactory.)
        (.setFile file))
      (.createUserManager)))

(defn build-server [& {:keys [port path user password ssl] :or {port 2221 path "/tmp" user "anonymous" password "" ssl false}}]
  (let [user-manager (-> "conf/users.properties"
                         io/file
                         create-user-manager)
        ssl-configuration (if ssl (-> "ssl/ftpserver.jks"
                                      io/file
                                      (create-ssl-configuration "password")))
        listener (create-listener port :ssl ssl-configuration)
        server (create-server listener user-manager)]
    server))

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

(defn run [& {:keys [port path user password] :or {port 2221 path "/tmp" user "anonymous" password ""}}]
  (let [opts {:port port :path path :user user :password password}]
    (do
      (welcome opts)
      (let [server (build-server opts)]
        (.start server)
        server))))

(defn- assoc-if-some
  ([m k val] (assoc-if-some m k val identity))
  ([m k val tx]
   (cond-> m
     (some? val) (assoc k (tx val)))))

(defn -main [& args]
  (let [port (first args)
        opts (assoc-if-some {} :port port (fn [val] (Integer/parseInt val)))]
    ;; opts #_
    (run opts)))
