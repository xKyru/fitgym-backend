package com.backend.fitgym.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MemberDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Email(message = "Introduce un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    @NotBlank(message = "La dirección es obligatorio")
    private String direccion;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate nacimiento;
    @NotBlank(message = "El contacto de emergencia es obligatorio")
    private String emergenciaNombre;
    @NotBlank(message = "El número de emergencia es obligatorio")
    private String emergenciaNumero;
    private String imagenPerfil;
    @NotNull(message = "El plan es obligatorio")
    private Long planId;
    private String estado;
    private LocalDate fechaRegistro;
    private LocalDate ultimoPago;
    private LocalDate siguientePago;

    public MemberDTO() {}

    public MemberDTO(Long id, String nombre, String email, String telefono, String direccion, LocalDate nacimiento, String emergenciaNombre, String emergenciaNumero, String imagenPerfil, Long planId, String estado, LocalDate fechaRegistro, LocalDate ultimoPago, LocalDate siguientePago) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nacimiento = nacimiento;
        this.emergenciaNombre = emergenciaNombre;
        this.emergenciaNumero = emergenciaNumero;
        this.imagenPerfil = imagenPerfil;
        this.planId = planId;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.ultimoPago = ultimoPago;
        this.siguientePago = siguientePago;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getEmergenciaNombre() {
        return emergenciaNombre;
    }

    public void setEmergenciaNombre(String emergenciaNombre) {
        this.emergenciaNombre = emergenciaNombre;
    }

    public String getEmergenciaNumero() {
        return emergenciaNumero;
    }

    public void setEmergenciaNumero(String emergenciaNumero) {
        this.emergenciaNumero = emergenciaNumero;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(LocalDate ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public LocalDate getSiguientePago() {
        return siguientePago;
    }

    public void setSiguientePago(LocalDate siguientePago) {
        this.siguientePago = siguientePago;
    }
}