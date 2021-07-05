package com.microusers.bookservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetails {

    @Id
    @GeneratedValue(generator = "uuid2",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    public UUID cartId;



    @OneToMany(mappedBy = "cartDetails")
    @Where(clause = "order_status=true")
    public List<BookCartDetails> bookCartDetails;

    @JsonIgnore
    @Type(type="uuid-char")
    public UUID userId;


}
