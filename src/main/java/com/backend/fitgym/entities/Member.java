package com.backend.fitgym.entities;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Column(name = "email", unique = true, length = 150)
    @Email(message = "Introduce un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(name = "telefono", length = 15)
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Column(name = "direccion", length = 255)
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Column(name = "nacimiento")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate nacimiento;

    @Column(name = "emergencia_nombre", length = 100)
    @NotBlank(message = "El contacto de emergencia es obligatorio")
    private String emergenciaNombre;

    @Column(name = "emergencia_numero", length = 15)
    @NotBlank(message = "El número de emergencia es obligatorio")
    private String emergenciaNumero;

    @Column(name = "imagen_perfil", length = 500)
    private String imagenPerfil;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @NotNull(message = "El plan es obligatorio")
    private Plan plan;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "ultimo_pago")
    private LocalDate ultimoPago;

    @Column(name = "siguiente_pago")
    private LocalDate siguientePago;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public Member() {
    }

    public Member(Long id, String nombre, String email, String telefono, String direccion, LocalDate nacimiento, String emergenciaNombre, String emergenciaNumero, String imagenPerfil, Plan plan, String estado, LocalDate fechaRegistro, LocalDate ultimoPago, LocalDate siguientePago, List<Payment> payments, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nacimiento = nacimiento;
        this.emergenciaNombre = emergenciaNombre;
        this.emergenciaNumero = emergenciaNumero;
        this.imagenPerfil = imagenPerfil;
        this.plan = plan;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.ultimoPago = ultimoPago;
        this.siguientePago = siguientePago;
        this.payments = payments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
