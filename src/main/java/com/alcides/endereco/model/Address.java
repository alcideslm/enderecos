package com.alcides.endereco.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter @Setter
@Where(clause = "deleted=false")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 200) @NotBlank
    @Column(nullable = false)
    private String streetName;

    @Size(max = 10) @NotBlank
    @Column(nullable = false)
    private String number;

    private String complement;

    @Size(max = 100) @NotBlank
    @Column(nullable = false)
    private String neighbourhood;

    @Size(max = 100) @NotBlank
    @Column(nullable = false)
    private String city;
    
    @Size(max = 100) @NotBlank
    @Column(nullable = false)
    private String state;
    
    @Size(max = 50) @NotBlank
    @Column(nullable = false)
    private String country;
    
    @Size(max = 20) @NotBlank
    @Column(nullable = false)
    private String zipcode;
    
    private String latitude;
    
    private String longitude;  

    @Column(nullable = false)
    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    public boolean hasLatLng(){
        return StringUtils.hasText(this.latitude) && StringUtils.hasText(this.longitude);
    }

    public boolean sameLatLng(String lat, String lng){
        if (!hasLatLng()) 
            return false;
        
        return this.latitude.equals(lat) && this.longitude.equals(lng);
    }

    public boolean sameAddress(Address address){
        if (Objects.isNull(address))
            return false;

        if (!this.number.equalsIgnoreCase(address.getNumber())
            || !this.streetName.equalsIgnoreCase(address.getStreetName())
            || !this.neighbourhood.equalsIgnoreCase(address.getNeighbourhood())
            || !this.city.equalsIgnoreCase(address.getCity())
            || !this.state.equalsIgnoreCase(address.getState())
            || !this.country.equalsIgnoreCase(address.getCountry()))
            return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}