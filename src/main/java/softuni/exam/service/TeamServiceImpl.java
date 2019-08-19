package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamDto;
import softuni.exam.domain.dtos.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String FILE_PATH =
            "D:\\Joro\\Desktop\\SoftUni\\JavaDB_Hibernate\\FootballInfo\\src\\main\\resources\\files\\xml\\teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil,
                           XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public String importTeams() throws JAXBException {
        StringBuilder builder = new StringBuilder();

        TeamRootDto teamRootDto = this.xmlParser.importXMl(TeamRootDto.class, FILE_PATH);


        for (TeamDto teamDto : teamRootDto.getTeamDtoList()) {

            Team team = this.modelMapper.map(teamDto, Team.class);
            Picture picture = this.pictureRepository.findByUrl(teamDto.getPictureDto().getUrl());

            team.setPicture(picture);

            if (!this.validatorUtil.isValid(team)){
                builder.append("Invalid team").append(System.lineSeparator());
                continue;
            }

            this.teamRepository.saveAndFlush(team);
            builder.append(String.format("Successfully imported team - %s", team.getName())).append(System.lineSeparator());

        }

        return builder.toString().trim();
    }

    @Override
    public boolean areImported() {

        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {

        return this.fileUtil.readFile(FILE_PATH);
    }
}
