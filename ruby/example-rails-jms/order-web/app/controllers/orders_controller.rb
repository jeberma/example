class OrdersController < ApplicationController
  include ActiveMessaging::MessageSender
  
  publishes_to :orders_input
  
  def index
    @orders = Order.find(:all)
  end
  
  def create
    if request.xhr?
      order = Order.new(params[:order])
      order.status = 'PENDING'
      order.save!
      publish(:orders_input, order.id.to_s)
      refresh_orders_view
    end
  end
  
  def delete
    if request.xhr?
      Order.find(params[:id]).destroy
      refresh_orders_view
    end
  end
  
  def refresh_orders_view
    render(:update) do |page|
      page.replace_html('orders_container', :partial=>'orders', :object=>Order.find(:all))
    end
  end
  
end
