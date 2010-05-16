require 'rubygems'
require 'stomp'

client = Stomp::Client.new('root','','localhost', 61613)
client.send('/queue/orders.input', '1')
