/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.test.jms;

import com.fcastillo.test.jms.ejb.DeliverySender;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSException;

/**
 *
 * @author fcastillo
 */
@Named(value = "testController")
@RequestScoped
public class TestController {

    @Inject
    private DeliverySender sender;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void send() {
        try {
            sender.sendMessage(nombre);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
