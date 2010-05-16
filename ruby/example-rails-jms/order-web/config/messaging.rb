#
# Add your destination definitions here
# can also be used to configure filters, and processor groups
#
ActiveMessaging::Gateway.define do |s|
  s.destination :orders_input, '/queue/orders.input'
  s.destination :orders_output, '/queue/orders.output'
end