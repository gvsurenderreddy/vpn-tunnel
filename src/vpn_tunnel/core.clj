(ns vpn-tunnel.core
  (:require [pallet.api :refer [lift]]
            [vpn-tunnel.groups.vpn-tunnel :refer [create-vpn-tunnel-group-spec]]
            [vpn-tunnel.config :refer [read-config]])
  (:gen-class))

(defn -main [&args] (lift (create-vpn-tunnel-group-spec (read-config))))
