package br.com.jvbarbosa.store.config;

import br.com.jvbarbosa.store.model.*;
import br.com.jvbarbosa.store.model.enums.OrderStatus;
import br.com.jvbarbosa.store.repository.*;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class SeedConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User();
        u1.setName("João Barbosa");
        u1.setEmail("jv@example.com.br");
        u1.setPassword("123Jv");
        u1.setSignUpDate(LocalDateTime.now());

        User u2 = new User();
        u2.setName("Sophia Salvador");
        u2.setEmail("soso@example.com.br");
        u2.setPassword("123Soso");
        u2.setSignUpDate(LocalDateTime.now());

        User u3 = new User();
        u3.setName("Renato Neto");
        u3.setEmail("renato@example.com.br");
        u3.setPassword("123Re");
        u3.setSignUpDate(LocalDateTime.now());

        User u4 = new User();
        u4.setName("Amauri Silveira");
        u4.setEmail("maumau@example.com.br");
        u4.setPassword("123Mau");
        u4.setSignUpDate(LocalDateTime.now());

        User u5 = new User();
        u5.setName("Diogo Garcia");
        u5.setEmail("dioguin@example.com.br");
        u5.setPassword("123Diogo");
        u5.setSignUpDate(LocalDateTime.now());

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));

        Category c1 = new Category();
        c1.setName("Eletrônicos");

        Category c2 = new Category();
        c2.setName("Computadores");

        Category c3 = new Category();
        c3.setName("Livros");

        categoryRepository.saveAll(Arrays.asList(c1, c2, c3));

        Product p1 = new Product();
        p1.setName("The Lord of the Rings");
        p1.setDescription("The Lord of the Rings Deluxe Edition: The Fellowship of the Ring, the Two Towers," +
                " the Return of the King");
        p1.setPrice(BigDecimal.valueOf(179.72));
        p1.setStock(300);

        Product p2 = new Product();
        p2.setName("Samsung Smart TV Crystal");
        p2.setDescription("43'' UHD 4K U8100F 2025");
        p2.setPrice(BigDecimal.valueOf(1632.18));
        p2.setStock(120);

        Product p3 = new Product();
        p3.setName("Apple 2024 MacBook Pro");
        p3.setDescription("14'' Processador M4 da Apple com CPU 10‑core e GPU 10‑core, " +
                "16GB Memória unificada, 512 GB) - Prateado");
        p3.setPrice(BigDecimal.valueOf(15899.20));
        p3.setStock(60);

        Product p4 = new Product();
        p4.setName("Java para leigos");
        p4.setDescription("Este livro é dedicado para programadores iniciantes e experientes e, até mesmo, " +
                "pessoas curiosas que querem entender um pouco mais a respeito do tema.");
        p4.setPrice(BigDecimal.valueOf(78.90));
        p4.setStock(355);

        Product p5 = new Product();
        p5.setName("Notebook VAIO FE16");
        p5.setDescription("AMD Ryzen 5-5625U Windows 11 Home 32GB RAM 512GB SSD Wi-Fi 6 Tela 16'' " +
                "IPS WUXGA Antirreflexo - Cinza Grafite");
        p5.setPrice(BigDecimal.valueOf(3371.00));
        p5.setStock(100);

        p1.getCategories().add(c3);
        p2.getCategories().add(c1);
        p3.getCategories().add(c2);
        p4.getCategories().add(c3);
        p5.getCategories().add(c2);

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        Order o1 = new Order();
        o1.setMoment(Instant.parse("2025-06-20T19:53:07Z"));
        o1.setOrderStatus(OrderStatus.PAID);
        o1.setClient(u2);

        Order o2 = new Order();
        o2.setMoment(Instant.parse("2025-06-20T19:53:07Z"));
        o2.setOrderStatus(OrderStatus.PAID);
        o2.setClient(u3);

        Order o3 = new Order();
        o3.setMoment(Instant.parse("2025-06-20T19:53:07Z"));
        o3.setOrderStatus(OrderStatus.PAID);
        o3.setClient(u4);

        Order o4 = new Order();
        o4.setMoment(Instant.parse("2025-06-20T19:53:07Z"));
        o4.setOrderStatus(OrderStatus.PAID);
        o4.setClient(u5);

        orderRepository.saveAll(Arrays.asList(o1, o2, o3, o4));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice().doubleValue());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice().doubleValue());
        OrderItem oi3 = new OrderItem(o2, p3, 1, p3.getPrice().doubleValue());
        OrderItem oi4 = new OrderItem(o3, p5, 1, p5.getPrice().doubleValue());
        OrderItem oi5 = new OrderItem(o3, p2, 1, p2.getPrice().doubleValue());
        OrderItem oi6 = new OrderItem(o3, p5, 1, p5.getPrice().doubleValue());
        OrderItem oi7 = new OrderItem(o4, p4, 1, p4.getPrice().doubleValue());

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4, oi5, oi6, oi7));

    }



}
