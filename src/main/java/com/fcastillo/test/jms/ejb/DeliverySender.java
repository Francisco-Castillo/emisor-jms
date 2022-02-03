/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.test.jms.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Stateless
public class DeliverySender {

    @Resource(mappedName = "jms/DemoFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/Demo")
    private Queue queue;

    public void sendMessage(String text) throws JMSException {

        Connection conn = null;
        Session session = null;
        try {
            conn = connectionFactory.createConnection();
            conn.start();

            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Productor vinculado al destino
            MessageProducer messageProducer = session.createProducer(queue);

            // Creamos un mensaje y recibo parametro
            TextMessage txtMessage = session.createTextMessage(text);

            // Enviamos mensaje
            messageProducer.send(txtMessage);
        } finally {
            if (session != null) {
                session.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
