package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.constants.Tables;
import com.six.challenge.tradingplatform.model.api.v1.user.UserOutputDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = Tables.USER)
public class UserDao {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @Column(unique=true)
    private String name;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<BuyOrderDao> buyOrders;
    @OneToMany(mappedBy = "user")
    private List<SellOrderDao> sellOrders;


    UserDao() {}

    public UserDao(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BuyOrderDao> getBuyOrders() {
        return buyOrders;
    }

    public List<SellOrderDao> getSellOrders() {
        return sellOrders;
    }

    public UserOutputDto toDto() {
        return new UserOutputDto(this.getId(), this.getName());
    }
}
