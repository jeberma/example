class OrdersOutputProcessor < ApplicationProcessor

  subscribes_to :orders_output

  def on_message(message)
    logger.debug "OrdersOutputProcessor received: " + message
    id, status = message.split(',')
    begin
      order = Order.find(id)
      order.status = status
      order.save!
    rescue ActiveRecord::RecordNotFound => e
      logger.error e
    end
  end
end