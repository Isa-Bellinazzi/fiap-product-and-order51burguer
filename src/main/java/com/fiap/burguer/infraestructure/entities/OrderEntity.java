package com.fiap.burguer.infraestructure.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity(name = "`order`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "time_waiting_order", nullable = false)
    private Integer timeWaitingOrder;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemsList;

    @Column(name = "client_id", nullable = true)
    private int idClient;

}
