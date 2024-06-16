package com.example.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    /*
       The <code>Item</code> entity defaults to field access, the <code>@Id</code> is on a field.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
       Параметр @Access(AccessType.PROPERTY) в поле name переключает это конкретное свойство для доступа
       во время выполнения JPA-провайдером через геттер/сеттер
    */
    @Access(AccessType.PROPERTY)
    @Column(name = "ITEM_NAME") // Mappings are still expected here!
    private String name;

    @OneToMany(mappedBy = "item",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true) // Includes CascadeType.REMOVE
    private Set<Bid> bids = new HashSet<>();

    @NotNull
    @Basic(fetch = FetchType.LAZY) // Defaults to EAGER
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING) // Defaults to ORDINAL
    private AuctionType auctionType = AuctionType.HIGHEST_BID;

    @Formula(
            "CONCAT(SUBSTR(DESCRIPTION, 1, 12), '...')"
    )
    private String shortDescription;

    @Formula(
            "(SELECT AVG(B.AMOUNT) FROM BID B WHERE B.ITEM_ID = ID)"
    )
    private BigDecimal averageBidAmount;

    @Column(name = "IMPERIALWEIGHT")
    @ColumnTransformer(
            read = "IMPERIALWEIGHT / 2.20462",
            write = "? * 2.20462"
    )
    private double metricWeight;

    @CreationTimestamp
    private LocalDate createdOn;

    @UpdateTimestamp
    private LocalDateTime lastModified;

    @Column(insertable = false)
    @ColumnDefault("1.00")
    @Generated(
            org.hibernate.annotations.GenerationTime.INSERT
    )
    private BigDecimal initialPrice;

    /*
        Hibernate will call <code>getName()</code> and <code>setName()</code> when loading and storing items.
    */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =
                !name.startsWith("AUCTION: ") ? "AUCTION: " + name : name;
    }

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public BigDecimal getAverageBidAmount() {
        return averageBidAmount;
    }

    public double getMetricWeight() {
        return metricWeight;
    }

    public void setMetricWeight(double metricWeight) {
        this.metricWeight = metricWeight;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }
}
