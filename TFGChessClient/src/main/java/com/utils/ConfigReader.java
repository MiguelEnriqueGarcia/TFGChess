/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utils;

import java.io.InputStream;
import java.io.ObjectInputFilter.Config;
import java.util.Properties;

/**
 *
 * @author migue
 */

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream input =
                Thread.currentThread()
                      .getContextClassLoader()
                      .getResourceAsStream("chessconfiguration.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró chessconfiguration.properties en el classpath");
            }

            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración", e);
        }
    }

    public static String getHost() {
        return props.getProperty("host");
    }

    public static int getPuerto() {
        return Integer.parseInt(props.getProperty("puerto"));
    }
    
}