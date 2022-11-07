package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.constants.Tables;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = Tables.SECURITY)
public class SecurityDao {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    @Column(unique=true)
    private String name;
    @OneToMany(mappedBy = "security")
    private List<OrderDao> orders;

    SecurityDao() {}

    public SecurityDao(String name) {
        this.name = name;
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

    public SecurityOutputDto toDto() {
        return new SecurityOutputDto(
            this.getId(), this.getName()
        );
    }

    @Override
    public String toString() {
        return "SecurityDao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
