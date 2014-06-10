;;(ns vpn-tunnel.test.ipsec
;;(:require [vpn-tunnel.test.ipsec :refer :all]
;;        [clojure.test :refer :all]))
;; TODO: look for the ESP header
;;(defn test-connection [connection-name] (format "tcpdump -n -i eth0 host lana.example.com"))


(comment
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
)
