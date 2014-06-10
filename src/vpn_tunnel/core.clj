(ns vpn-tunnel.core
  (:require [pallet.api :refer [lift]]
            [vpn-tunnel.groups.vpn-tunnel :refer [create-vpn-tunnel-group-spec]]
            [vpn-tunnel.config :refer [read-config]])
  (:gen-class))

;; TODO: split up the creation of the boxes from the applying the ipsec configs
(defn -main
  [&args]
  (let [specs (create-vpn-tunnel-group-spec (read-config))
        left-group-spec (:right specs)
        right-group-spec (:left specs)
        left (future  (converge {group 1} left-group-spec))
        right (future)] (converge {group 1} left-group-spec))
  [@left, @right])
