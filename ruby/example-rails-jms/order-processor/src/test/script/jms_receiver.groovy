import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.TextMessage

import org.springframework.jms.listener.DefaultMessageListenerContainer
import org.apache.activemq.ActiveMQConnectionFactory

class MessageReceiver implements MessageListener {
    public void onMessage(Message msg) {
        println(((TextMessage)msg).text)
    }
}

def container = new DefaultMessageListenerContainer(
    connectionFactory: new ActiveMQConnectionFactory(brokerURL:'tcp://localhost:61616'),
    messageListener: new MessageReceiver(),
    destinationName: 'orders.input',
    concurrentConsumers: 1,
    sessionTransacted: true
)

println 'Listening for messages'
container.initialize()
container.start()
