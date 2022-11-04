package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.constants.Tables;
import com.six.challenge.tradingplatform.model.api.v1.security.SecurityOutputDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = Tables.SECURITY)
public class SecurityDao {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @Column(unique=true)
    private String name;

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
}
