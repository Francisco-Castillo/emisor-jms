/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.test.jms.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless
public class DeliverySender {

    @Resource(mappedName = "jms/NewDemoFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/NewDemo")
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
