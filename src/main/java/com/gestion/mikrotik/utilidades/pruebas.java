package com.gestion.mikrotik.utilidades;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.UserRole;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.UserAppService;
import com.gestion.mikrotik.services.UserRoleService;
import com.gestion.mikrotik.services.VlanService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class pruebas {

    @Autowired
    UserAppService userAppService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    IpAddressService    ipAddressService;
    public static String num2Ip(long num) {
        return (num >> 24 & 0xFF) + "."
                + ((num >> 16) & 0xFF) + "."
                + ((num >> 8) & 0xFF) + "."
                + (num & 0xFF);
    }


    @SneakyThrows
    public static void main(String[] args) {
        String direccionIP="10.10.10.0";
        InetAddress inet = InetAddress.getByName(direccionIP);

        System.out.println(inet.hashCode());
        

        if (inet.isReachable(300)){
           //si responde ping la agrego



        }else {

        }

        Vlan nuevaVlan= new Vlan("13","Ortega","190.90.193.0");

        for (int i = nuevaVlan.getNetworkHashcode(); i < nuevaVlan.getNetworkHashcode()+255; i++) {
            direccionIP=num2Ip(i);
            if(InetAddress.getByName(direccionIP).isReachable(2000)){
                System.out.println("la direccion "+direccionIP+" Responde Ping");
            }else{
                //System.out.println("la direccion "+direccionIP+" Responde NO  Ping");
            }


        }






    }


}
