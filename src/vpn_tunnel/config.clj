(ns vpn-tunnel.config
  ^ {:author "Matthew Burns"
     :doc "Top level handler that creates jobs for deploying a vpn"}
    (:require [shoreleave.server-helpers :refer [safe-read]])
    (:gen-class))

(defn read-config
  "Read a config file and return it as Clojure Data.  Usually, this is a hashmap"
  ([]
     (read-config (str (System/getProperty "user.dir") "/resources/config.edn")))
  ([config-loc]
     (safe-read (slurp config-loc))))

(def config (read-config))

(def cfg {:tunnels [{:name "btl-tun-dev"
                     :encryption-key nil
                     :authentication-key nil
                     :lan-A {:network "192.168.1.0/24" :gateway "192.168.1.254" :label "ipsec-ci-dev"}
                     :lan-B {:network "192.168.2.0/24" :gateway "192.168.2.254" :label "ipsec0"}}]})
