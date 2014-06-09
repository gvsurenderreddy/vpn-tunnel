(ns vpn-tunnel.crates.ipsec
  (:require
     [pallet.api :refer [server-spec plan-fn]]
     [pallet.actions :refer [package package-manager remote-file exec-script*]]
     [pallet.crate :refer [defplan]]
     [clostache.parser :refer [render]]))

(defplan create-ipsec-spec
  "install ipsec package and use its tools to create and startup the tunnel connection"
  [{:keys [template ipsec-cfgs] :as args}]
  (let [tmpl-path (format "%s/%s" (System/getProperty "user.dir") template)
        cfg-contents (render template ipsec-cfgs)]
    (server-spec :phases {:configure (plan-fn
                                       (package-manager :update)
                                       (package "ipsec-tools")
                                       (remote-file "/etc/sysctrl.conf" :content cfg-contents :owner "root")
                                       (exec-script*  "sysctl -p /etc/sysctl.conf"))})))
