(ns status-im.network.events
  (:require [re-frame.core :as re-frame]
            [status-im.utils.handlers :as handlers]
            [status-im.network.net-info :as net-info]))

(re-frame/reg-fx
  ::listen-to-network-status
  (fn [handler]
    (net-info/init handler)
    (net-info/add-listener handler)))

(handlers/register-handler-fx
  :listen-to-network-status
  (fn []
    {::listen-to-network-status #(re-frame/dispatch [::update-network-status %])}))

(handlers/register-handler-db
  ::update-network-status
  [re-frame/trim-v]
  (fn [db [is-connected?]]
    (assoc db :network-status (if is-connected? :online :offline))))
