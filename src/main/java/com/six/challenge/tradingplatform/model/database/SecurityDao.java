package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.model.api.v1.SecurityDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "SECURITY")
public class SecurityDao {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;
    @Column(unique=true)
    private String name;

    SecurityDao() {}

    SecurityDao(String name) {
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

    public SecurityDto toDto() {
        return new SecurityDto(
            this.getId(), this.getName()
        );
    }
}
