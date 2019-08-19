package softuni.exam.domain.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String url;
    private List<Team> team;
    private List<Player> player;

    public Picture() {
    }

    @Column(name = "urls", nullable = false)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @OneToMany(targetEntity = Team.class, cascade = CascadeType.ALL, mappedBy = "picture")
    public List<Team> getTeam() {
        return this.team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

    @OneToMany(targetEntity = Player.class, cascade = CascadeType.ALL, mappedBy = "picture")
    public List<Player> getPlayer() {
        return this.player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }
}
