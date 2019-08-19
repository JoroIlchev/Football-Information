package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    private String firstName;
    private String lastName;
    private Integer number;
    private BigDecimal salary;
    private Position position;
    private Picture picture;
    private Team team;

    public Player() {
    }

    @Column(name = "first_name",nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name",nullable = false)
    @Size(min = 3, max = 15)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Column(name = "number", nullable = false)
    @Min(1)
    @Max(99)
    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    @Column(name = "salary", nullable = false)
    @DecimalMin("0.00")
    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false )
    @NotNull
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    @NotNull
    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @NotNull
    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
