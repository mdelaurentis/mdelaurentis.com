(ns com.mdelaurentis.site
  (:require [clojure.java.io :as io])
  (:import [com.amazonaws.auth PropertiesCredentials]
           [com.amazonaws.services.s3.transfer TransferManager]))

(def credentials-file
  (io/file "/Users/mdelaurentis/.aws_creds_personal.properties"))

(defn credentials []
  (PropertiesCredentials. credentials-file))

(def site-directory (io/file "site"))

(def bucket "mdelaurentis.com")

(defn transfer-manager []
  (TransferManager. (credentials)))

(defn deploy-site []
  (let [mgr (transfer-manager)
        xfer (.uploadDirectory mgr
                               bucket nil site-directory true)]
    (.waitForCompletion xfer)))

(deploy-site)
