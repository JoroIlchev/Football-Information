package softuni.exam.domain.dtos.json;


import com.google.gson.annotations.Expose;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;

import java.math.BigDecimal;

public class PlayersJsonDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer number;
    @Expose
    private BigDecimal salary;
    @Expose
    private Position position;
    @Expose
    private PictureJsonDto picture;
    @Expose
    private TeamJsonDto team;

    public PlayersJsonDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PictureJsonDto getPicture() {
        return this.picture;
    }

    public void setPicture(PictureJsonDto picture) {
        this.picture = picture;
    }

    public TeamJsonDto getTeam() {
        return this.team;
    }

    public void setTeam(TeamJsonDto team) {
        this.team = team;
    }
}
