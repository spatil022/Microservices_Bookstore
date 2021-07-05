package com.microusers.bookservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CouponsDetails implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    UUID CId;

    @ManyToOne
    @JoinColumn(name = "cuoId")
    public Coupons coupons;

    @JsonIgnore
    @Type(type="uuid-char")
    public UUID userId;



    public CouponsDetails(Coupons coupons, UUID userId) {
        this.coupons = coupons;
        this.userId = userId;
    }

}
