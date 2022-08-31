package com.gestion.mikrotik.utilidades;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.Socket;

public class pruebas {
    public static String num2Ip(long num) {
        return (num >> 24 & 0xFF) + "."
                + ((num >> 16) & 0xFF) + "."
                + ((num >> 8) & 0xFF) + "."
                + (num & 0xFF);
    }


    @SneakyThrows
    public static void main(String[] args) {
        String direccionIP="10.0.0.210";
        InetAddress inet = InetAddress.getByName(direccionIP);

        if (inet.isReachable(300)){
            System.out.println(inet.hashCode());
            System.out.println(num2Ip(inet.hashCode()));
        }else {
            System.out.println(num2Ip(inet.hashCode()));
        }



    }


}
