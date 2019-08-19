package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.json.PlayersJsonDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private static final String FILE_PATH =
            "D:\\Joro\\Desktop\\SoftUni\\JavaDB_Hibernate\\FootballInfo\\src\\main\\resources\\files\\json\\players.json";

    private final PlayerRepository playerRepository;
    private final PictureRepository pictureRepository;
    private final TeamRepository teamRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;


    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PictureRepository pictureRepository, TeamRepository teamRepository,
                             FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.playerRepository = playerRepository;
        this.pictureRepository = pictureRepository;
        this.teamRepository = teamRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder builder = new StringBuilder();

        PlayersJsonDto[] playersJsonDtos = this.gson.fromJson(readPlayersJsonFile(), PlayersJsonDto[].class);

        for (PlayersJsonDto playersJsonDto : playersJsonDtos) {

            Player player = this.modelMapper.map(playersJsonDto,Player.class);
            Picture pic = this.modelMapper.map(playersJsonDto.getPicture(), Picture.class);
            Team teamFromDto = this.modelMapper.map(playersJsonDto.getTeam(), Team.class);
            Position position = playersJsonDto.getPosition();
//            Picture picture = this.pictureRepository.findByUrl(playersJsonDto.getPicture().getUrl());
//            Team team = this.teamRepository.findByName(playersJsonDto.getTeam().getName());

            Picture picture = this.pictureRepository.findByUrl(pic.getUrl());
            Team team = this.teamRepository.findByName(teamFromDto.getName());
            player.setPosition(position);
            player.setPicture(picture);
            player.setTeam(team);

            if (!this.validatorUtil.isValid(player) || picture == null || team == null){
                builder.append("Invalid player").append(System.lineSeparator());
                continue;
            }

            this.playerRepository.saveAndFlush(player);
            builder.append(String.format("Successfully imported player: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator());
        }

        return builder.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder builder = new StringBuilder();
        List<Player> players = this.playerRepository.exportPlayersWhereSalaryBiggerThan();

        for (Player player : players) {
            builder.append(String.format("Player name: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator())
            .append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator())
            .append(String.format("\tSalary: %s", player.getSalary())).append(System.lineSeparator());
            Team team = this.teamRepository.findById(player.getTeam().getId()).orElse(null);
            builder.append(String.format("\tTeam name: %s",team.getName())).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {

        StringBuilder builder = new StringBuilder();
        List<Player> players = this.playerRepository.exportPlayersInATeam();

        builder.append("Team: North Hub").append(System.lineSeparator());

        for (Player player : players) {
            builder.append(String.format("\tPlayer name:  %s %s - %s",
                    player.getFirstName(),player.getLastName(),player.getPosition() )).append(System.lineSeparator())
                    .append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
