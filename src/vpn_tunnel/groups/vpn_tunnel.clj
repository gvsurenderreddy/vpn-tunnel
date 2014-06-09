(ns vpn-tunnel.groups.vpn-tunnel
  (:require
     [pallet.api :refer [node-spec group-spec server-spec plan-fn lift converge]]
     [pallet.crate.automated-admin-user :refer [automated-admin-user]]
     [pallet.configure :refer [compute-service]]
     [pallet.actions :refer [package package-manager]]
     [pallet.actions :refer [package remote-file exec-script*]]
     [vpn-tunnel.crates.ipsec :refer [create-ipsec-spec]]
     [clostache.parser :refer [render render-resource]]
     [vpn-tunnel.config :refer [read-config]]))

(defn create-vpn-tunnel-group-spec
  [cfgs]
  (let [tunnel (nth (:tunnels cfgs) 0)
        grp-name (:name tunnel)
        tmpl (slurp (:template-path tunnel))
        left-spec (create-ipsec-spec {:ipsec-cfgs (:lan-A tunnel) :template tmpl})
        right-spec (create-ipsec-spec {:ipsec-cfgs (:lan-B tunnel) :template tmpl})
        base-server (server-spec :phases {:bootstrap (plan-fn (automated-admin-user))})
        left-group (group-spec grp-name :extends [base-server left-spec])
        right-group (group-spec grp-name :extends [base-server right-spec])
        left (converge {left-group 1} :compute (compute-service :aws))
        right (converge {right-group 1} :compute (compute-service :rs))]
    {:left left-group :right right-group}))



(def tunnel (nth (:tunnels (read-config)) 0) )
(def grp-name (:name tunnel))
(def tmpl (slurp (:template-path tunnel)) )
(def left-spec (create-ipsec-spec {:ipsec-cfgs (:lan-A tunnel) :template tmpl}))
(def right-spec (create-ipsec-spec {:ipsec-cfgs (:lan-B tunnel) :template tmpl}))
(def base-server (server-spec :phases {:bootstrap (plan-fn (automated-admin-user))}))
(def left-group (group-spec grp-name :extends [base-server left-spec]))
(def right-group (group-spec grp-name :extends [base-server right-spec]))
(def aws (pallet.configure/compute-service  :aws))
(def left (pallet.api/converge {left-group 1} :compute aws))
(def rs (pallet.configure/compute-service :rs))
(def right (pallet.api/converge {right-group 1} :compute rs))
(def test-spec (create-vpn-tunnel-group-spec (read-config)))


(def result  (lift (:left test-spec)))
