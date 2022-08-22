package com.gestion.mikrotik.entities;

import javax.persistence.*;

@Entity
@Table(name="equipos_mikrotik3")
public class Mikrotik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="direccion_ip")
    private String direccion_ip;
    @Column(name="vlanid")
    private int vlanid;
    @Column(name="comentario")
    private String comentario;
    @Column(name="wir_mode")
    private int wir_mode;
    @Column(name="scrpt_config")
    private int scrpt_config;
    @Column(name="scrpt_backup")
    private int scrpt_backup;
    @Column(name="referencia")
    private String referencia;
    @Column(name="serial_mkt")
    private String serial_mkt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion_ip() {
        return direccion_ip;
    }

    public void setDireccion_ip(String direccion_ip) {
        this.direccion_ip = direccion_ip;
    }

    public int getVlanid() {
        return vlanid;
    }

    public void setVlanid(int vlanid) {
        this.vlanid = vlanid;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getWir_mode() {
        return wir_mode;
    }

    public void setWir_mode(int wir_mode) {
        this.wir_mode = wir_mode;
    }

    public int getScrpt_config() {
        return scrpt_config;
    }

    public void setScrpt_config(int scrpt_config) {
        this.scrpt_config = scrpt_config;
    }

    public int getScrpt_backup() {
        return scrpt_backup;
    }

    public void setScrpt_backup(int scrpt_backup) {
        this.scrpt_backup = scrpt_backup;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSerial_mkt() {
        return serial_mkt;
    }

    public void setSerial_mkt(String serial_mkt) {
        this.serial_mkt = serial_mkt;
    }

    public Mikrotik() {
    }
}
