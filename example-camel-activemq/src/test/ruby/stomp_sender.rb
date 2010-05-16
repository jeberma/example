# NOTE: this script requires the stomp client gem:
# gem install stomp

require 'rubygems'
require 'stomp'

client = Stomp::Client.new('root','','localhost', 61613)
client.send('/queue/esb.log', 'HELLO STOMP!')
