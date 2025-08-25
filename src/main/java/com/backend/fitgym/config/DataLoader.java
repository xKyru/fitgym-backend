package com.backend.fitgym.config;

import com.backend.fitgym.entities.Member;
import com.backend.fitgym.entities.Payment;
import com.backend.fitgym.entities.Plan;
import com.backend.fitgym.repositories.MemberRepository;
import com.backend.fitgym.repositories.PaymentRepository;
import com.backend.fitgym.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Solo cargar datos si no existen
        if (planRepository.count() == 0) {
            System.out.println("⏳ Cargando datos de prueba...");
            loadPlans();
            loadMembers();
            loadPayments();
            System.out.println("✅ Datos de prueba cargados exitosamente!");
        } else {
            System.out.println("ℹ️  La base de datos ya contiene datos, omitiendo carga inicial.");
        }
    }

    private void loadPlans() {
        Plan plan1 = new Plan();
        plan1.setName("Básico");
        plan1.setPrice(new BigDecimal("800.00"));

        Plan plan2 = new Plan();
        plan2.setName("Premium");
        plan2.setPrice(new BigDecimal("1200.00"));

        Plan plan3 = new Plan();
        plan3.setName("Familiar");
        plan3.setPrice(new BigDecimal("1500.00"));

        planRepository.saveAll(Arrays.asList(plan1, plan2, plan3));
        System.out.println("📋 Planes cargados: 3 planes insertados");
    }

    private void loadMembers() {
        List<Plan> plans = planRepository.findAll();
        Plan planBasico = plans.get(0); // ID 1 - Básico
        Plan planPremium = plans.get(1); // ID 2 - Premium
        Plan planFamiliar = plans.get(2); // ID 3 - Familiar

        Member member1 = new Member();
        member1.setNombre("Ana García");
        member1.setEmail("ana@email.com");
        member1.setTelefono("4491234567");
        member1.setDireccion("Calle Principal 123, Col. Centro");
        member1.setNacimiento(LocalDate.of(1990, 5, 15));
        member1.setEmergenciaNombre("Carlos García");
        member1.setEmergenciaNumero("4495647890");
        member1.setImagenPerfil("https://ejemplo.com/fotos/ana.jpg");
        member1.setPlan(planPremium);
        member1.setEstado("Activo");
        member1.setFechaRegistro(LocalDate.of(2024, 1, 15));
        member1.setUltimoPago(LocalDate.of(2025, 1, 1));
        member1.setSiguientePago(LocalDate.of(2025, 2, 1));

        Member member2 = new Member();
        member2.setNombre("Luis Martínez");
        member2.setEmail("luis.m@empresa.com");
        member2.setTelefono("4492345678");
        member2.setDireccion("Avenida Revolución 456, Col. Moderna");
        member2.setNacimiento(LocalDate.of(1988, 7, 22));
        member2.setEmergenciaNombre("María Martínez");
        member2.setEmergenciaNumero("4497654321");
        member2.setImagenPerfil("https://ejemplo.com/fotos/luis.jpg");
        member2.setPlan(planBasico);
        member2.setEstado("Activo");
        member2.setFechaRegistro(LocalDate.of(2024, 2, 20));
        member2.setUltimoPago(LocalDate.of(2025, 1, 5));
        member2.setSiguientePago(LocalDate.of(2025, 2, 5));

        Member member3 = new Member();
        member3.setNombre("María Rodríguez");
        member3.setEmail("maria.rdz@gmail.com");
        member3.setTelefono("4493456789");
        member3.setDireccion("Calle Juárez 789, Col. Histórica");
        member3.setNacimiento(LocalDate.of(1992, 11, 30));
        member3.setEmergenciaNombre("Pedro Rodríguez");
        member3.setEmergenciaNumero("4498765432");
        member3.setImagenPerfil("https://ejemplo.com/fotos/maria.jpg");
        member3.setPlan(planPremium);
        member3.setEstado("Inactivo");
        member3.setFechaRegistro(LocalDate.of(2023, 11, 10));
        member3.setUltimoPago(LocalDate.of(2024, 12, 15));
        member3.setSiguientePago(null);

        Member member4 = new Member();
        member4.setNombre("Carlos Sánchez");
        member4.setEmail("carlos.s@outlook.com");
        member4.setTelefono("4494567890");
        member4.setDireccion("Boulevard Universidad 101, Col. Estudiantil");
        member4.setNacimiento(LocalDate.of(1985, 3, 18));
        member4.setEmergenciaNombre("Laura Sánchez");
        member4.setEmergenciaNumero("4499876543");
        member4.setImagenPerfil("https://ejemplo.com/fotos/carlos.jpg");
        member4.setPlan(planFamiliar);
        member4.setEstado("Activo");
        member4.setFechaRegistro(LocalDate.of(2024, 3, 5));
        member4.setUltimoPago(LocalDate.of(2025, 1, 10));
        member4.setSiguientePago(LocalDate.of(2025, 2, 10));

        Member member5 = new Member();
        member5.setNombre("Sofía López");
        member5.setEmail("sofia.lp@yahoo.com");
        member5.setTelefono("4495678901");
        member5.setDireccion("Callejón del Arte 234, Col. Bohemia");
        member5.setNacimiento(LocalDate.of(1995, 9, 5));
        member5.setEmergenciaNombre("Ricardo López");
        member5.setEmergenciaNumero("4490987654");
        member5.setImagenPerfil("https://ejemplo.com/fotos/sofia.jpg");
        member5.setPlan(planBasico);
        member5.setEstado("Pendiente");
        member5.setFechaRegistro(LocalDate.of(2025, 1, 12));
        member5.setUltimoPago(null);
        member5.setSiguientePago(null);

        Member member6 = new Member();
        member6.setNombre("Jorge Hernández");
        member6.setEmail("jorge.hdz@empresa.com");
        member6.setTelefono("4496789012");
        member6.setDireccion("Avenida Tecnológico 567, Col. Innovación");
        member6.setNacimiento(LocalDate.of(1987, 12, 12));
        member6.setEmergenciaNombre("Elena Hernández");
        member6.setEmergenciaNumero("4491098765");
        member6.setImagenPerfil("https://ejemplo.com/fotos/jorge.jpg");
        member6.setPlan(planPremium);
        member6.setEstado("Activo");
        member6.setFechaRegistro(LocalDate.of(2023, 9, 18));
        member6.setUltimoPago(LocalDate.of(2025, 1, 15));
        member6.setSiguientePago(LocalDate.of(2025, 2, 15));

        Member member7 = new Member();
        member7.setNombre("Patricia Ramírez");
        member7.setEmail("paty.rmz@gmail.com");
        member7.setTelefono("4497890123");
        member7.setDireccion("Calle Primavera 890, Col. Jardines");
        member7.setNacimiento(LocalDate.of(1993, 4, 25));
        member7.setEmergenciaNombre("Juan Ramírez");
        member7.setEmergenciaNumero("4492109876");
        member7.setImagenPerfil("https://ejemplo.com/fotos/patricia.jpg");
        member7.setPlan(planFamiliar);
        member7.setEstado("Activo");
        member7.setFechaRegistro(LocalDate.of(2024, 5, 22));
        member7.setUltimoPago(LocalDate.of(2025, 1, 20));
        member7.setSiguientePago(LocalDate.of(2025, 2, 20));

        Member member8 = new Member();
        member8.setNombre("Fernando Torres");
        member8.setEmail("fer.torres@outlook.com");
        member8.setTelefono("4498901234");
        member8.setDireccion("Paseo de los Deportes 345, Col. Atlética");
        member8.setNacimiento(LocalDate.of(1991, 8, 8));
        member8.setEmergenciaNombre("Silvia Torres");
        member8.setEmergenciaNumero("4493210987");
        member8.setImagenPerfil("https://ejemplo.com/fotos/fernando.jpg");
        member8.setPlan(planBasico);
        member8.setEstado("Inactivo");
        member8.setFechaRegistro(LocalDate.of(2024, 7, 30));
        member8.setUltimoPago(LocalDate.of(2024, 12, 1));
        member8.setSiguientePago(null);

        Member member9 = new Member();
        member9.setNombre("Adriana Morales");
        member9.setEmail("adri.mrl@email.com");
        member9.setTelefono("4499012345");
        member9.setDireccion("Calle del Comercio 678, Col. Mercantil");
        member9.setNacimiento(LocalDate.of(1989, 1, 20));
        member9.setEmergenciaNombre("José Morales");
        member9.setEmergenciaNumero("4494321098");
        member9.setImagenPerfil("https://ejemplo.com/fotos/adriana.jpg");
        member9.setPlan(planPremium);
        member9.setEstado("Activo");
        member9.setFechaRegistro(LocalDate.of(2024, 4, 10));
        member9.setUltimoPago(LocalDate.of(2025, 1, 25));
        member9.setSiguientePago(LocalDate.of(2025, 2, 25));

        Member member10 = new Member();
        member10.setNombre("Roberto Jiménez");
        member10.setEmail("roberto.j@empresa.com");
        member10.setTelefono("4490123456");
        member10.setDireccion("Avenida Central 901, Col. Principal");
        member10.setNacimiento(LocalDate.of(1986, 6, 14));
        member10.setEmergenciaNombre("Lucía Jiménez");
        member10.setEmergenciaNumero("4495432109");
        member10.setImagenPerfil("https://ejemplo.com/fotos/roberto.jpg");
        member10.setPlan(planBasico);
        member10.setEstado("Activo");
        member10.setFechaRegistro(LocalDate.of(2024, 6, 15));
        member10.setUltimoPago(LocalDate.of(2025, 1, 30));
        member10.setSiguientePago(LocalDate.of(2025, 2, 28));

        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5,
                member6, member7, member8, member9, member10);

        memberRepository.saveAll(members);
        System.out.println("👥 Miembros cargados: " + members.size() + " miembros insertados");
    }

    private void loadPayments() {
        List<Member> members = memberRepository.findAll();
        List<Plan> plans = planRepository.findAll();

        // Crear pagos
        Payment payment1 = createPayment(members.get(0), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2025, 1, 1), "Tarjeta de crédito", "Completado", "FAC-001-2025");

        Payment payment2 = createPayment(members.get(0), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2024, 12, 1), "Transferencia bancaria", "Completado", "FAC-012-2024");

        Payment payment3 = createPayment(members.get(1), plans.get(0), new BigDecimal("800.00"),
                LocalDate.of(2025, 1, 5), "Efectivo", "Completado", "FAC-002-2025");

        Payment payment4 = createPayment(members.get(2), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2024, 12, 15), "Tarjeta de débito", "Completado", "FAC-011-2024");

        Payment payment5 = createPayment(members.get(2), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2024, 11, 15), "Tarjeta de crédito", "Completado", "FAC-010-2024");

        Payment payment6 = createPayment(members.get(3), plans.get(2), new BigDecimal("1500.00"),
                LocalDate.of(2025, 1, 10), "Transferencia bancaria", "Completado", "FAC-003-2025");

        Payment payment7 = createPayment(members.get(3), plans.get(2), new BigDecimal("1500.00"),
                LocalDate.of(2024, 12, 10), "Efectivo", "Completado", "FAC-013-2024");

        Payment payment8 = createPayment(members.get(5), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2025, 1, 15), "Tarjeta de crédito", "Completado", "FAC-004-2025");

        Payment payment9 = createPayment(members.get(5), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2024, 12, 15), "Tarjeta de crédito", "Completado", "FAC-014-2024");

        Payment payment10 = createPayment(members.get(6), plans.get(2), new BigDecimal("1500.00"),
                LocalDate.of(2025, 1, 20), "Transferencia bancaria", "Completado", "FAC-005-2025");

        Payment payment11 = createPayment(members.get(7), plans.get(0), new BigDecimal("800.00"),
                LocalDate.of(2024, 12, 1), "Efectivo", "Completado", "FAC-015-2024");

        Payment payment12 = createPayment(members.get(8), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2025, 1, 25), "Tarjeta de débito", "Completado", "FAC-006-2025");

        Payment payment13 = createPayment(members.get(8), plans.get(1), new BigDecimal("1200.00"),
                LocalDate.of(2024, 12, 25), "Tarjeta de crédito", "Completado", "FAC-016-2024");

        Payment payment14 = createPayment(members.get(9), plans.get(0), new BigDecimal("800.00"),
                LocalDate.of(2025, 1, 30), "Efectivo", "Completado", "FAC-007-2025");

        List<Payment> payments = Arrays.asList(payment1, payment2, payment3, payment4, payment5,
                payment6, payment7, payment8, payment9, payment10,
                payment11, payment12, payment13, payment14);

        paymentRepository.saveAll(payments);
        System.out.println("💰 Pagos cargados: " + payments.size() + " pagos insertados");

        // Calcular y mostrar resumen
        BigDecimal totalIngresos = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("📊 Resumen financiero: $" + totalIngresos + " MXN en ingresos totales");
    }

    private Payment createPayment(Member member, Plan plan, BigDecimal amount, LocalDate date,
                                  String method, String status, String invoiceNumber) {
        Payment payment = new Payment();
        payment.setMember(member);
        payment.setPlan(plan);
        payment.setAmount(amount);
        payment.setDate(date);
        payment.setMethod(method);
        payment.setStatus(status);
        payment.setInvoiceNumber(invoiceNumber);

        return payment;
    }
}