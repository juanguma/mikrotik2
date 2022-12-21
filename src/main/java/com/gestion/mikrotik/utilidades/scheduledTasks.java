package com.gestion.mikrotik.utilidades;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.VlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static com.gestion.mikrotik.utilidades.pruebas.num2Ip;
@Component
public class scheduledTasks {

    @Autowired
    public IpAddressService ipService;
    @Autowired
    public VlanService vlanService;

    @Scheduled(cron="@daily")
    public void findIps () throws IOException {
        List<Vlan> vlanList = this.vlanService.getAllVlan();
        for ( Vlan vlan:vlanList){
            for (int i = vlan.getNetworkHashcode(); i < vlan.getNetworkHashcode()+255 ; i++) {
                String direccionIP=num2Ip(i);
                if(InetAddress.getByName(direccionIP).isReachable(2000)){
                        if(this.ipService.findIpAddress(direccionIP)){
                            System.out.println("la direccion existe ");

                        }else{
                            IpAddress newIP= new IpAddress(vlan,i,direccionIP);

                            this.ipService.addAddress(newIP);
                            System.out.println("la direccion "+direccionIP+" inseratada");
                        }

                    }else{
                        System.out.println("la direccion "+direccionIP+" Responde NO  Ping");
                }
            }

        }

    }
}
