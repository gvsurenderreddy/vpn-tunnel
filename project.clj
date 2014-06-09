(defproject vpn-tunnel "0.1.0"
  :description "Pallet project for creating the hosts endpoints for an ipsec vpn-tunnel connecting two networks"
  :min-lein-version "2.0.0"
;;  :aot :all
  :main vpn-tunnel.core
  :local-repo-classpath true
  :repositories {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"}
  :dependencies [[org.clojure/clojure "1.5.1"]  ;; Lisp on the jvm
                 [com.palletops/pallet "0.8.0-RC.9"] ;; dev ops framework
                 [com.palletops/pallet-jclouds "1.7.3"] ;; cloud provider abstraction layer pallet uses
                 [com.palletops/pallet-docker "0.1.0"]
                 [com.palletops/pallet-aws "0.2.3"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [org.apache.jclouds/jclouds-allblobstore "1.7.2"]
                 [org.apache.jclouds/jclouds-allcompute "1.7.2"]
                 [org.apache.jclouds.driver/jclouds-slf4j "1.7.2" :exclusions [org.slf4j/slf4j-api]]
                 [org.apache.jclouds.driver/jclouds-sshj "1.7.2"] ;; ssh client functionality
                 [shoreleave "0.3.0"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [shoreleave/shoreleave-remote-ring "0.3.0"]
                 [ch.qos.logback/logback-classic "1.0.9"]]
  :profiles {:dev
             {:dependencies [[com.palletops/pallet "0.8.0-RC.9" :classifier "tests"]]
              :plugins [[com.palletops/pallet-lein "0.8.0-alpha.1"]]}
             :leiningen/reply
             {:dependencies [[org.slf4j/jcl-over-slf4j "1.7.2"]]
              :exclusions [commons-logging]}})
