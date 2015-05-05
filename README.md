# 2015_tw_nccu_wearable_api

這個專案包括兩個部分

(1)MessageExchangeAgent
有含兩個api，MessageSender負責送訊息到rabbitmq server；MessageReceiver負責從rabbitmq server接收訊息。

(2)SensorDataHandler
SensorDataRetriver接收BLE device的mac address後，將收到的ble訊息印出來

範例: https://github.com/brucechang123/2015_ble_to_rmq/
