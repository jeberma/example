require 'rubygems'
require 'stomp'

DESTINATION = '/queue/orders.input'

received = nil

client = Stomp::Client.new('root','','localhost',61613)
client.subscribe(DESTINATION) {|msg| received=msg}
client.send(DESTINATION, Time.now.to_s)

sleep(0.01) until received

puts received.body
