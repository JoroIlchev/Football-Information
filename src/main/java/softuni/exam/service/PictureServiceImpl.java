package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureDto;
import softuni.exam.domain.dtos.PictureRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String FILE_PATH =
            "D:\\Joro\\Desktop\\SoftUni\\JavaDB_Hibernate\\FootballInfo\\src\\main\\resources\\files\\xml\\pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;


    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws JAXBException {

        StringBuilder builder = new StringBuilder();

        PictureRootDto pictureRootDto = this.xmlParser.importXMl(PictureRootDto.class, FILE_PATH);

        for (PictureDto pictureDto : pictureRootDto.getPictureDtos()) {

            Picture picture = this.modelMapper.map(pictureDto, Picture.class);

            if (!this.validatorUtil.isValid(picture)){

                builder.append("Invalid picture").append(System.lineSeparator());
                continue;
            }

            this.pictureRepository.saveAndFlush(picture);
            builder.append(String.format("Successfully imported picture - %s", picture.getUrl())).append(System.lineSeparator());
        }

        return builder.toString().trim();

    }

    @Override
    public boolean areImported() {

        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return this.fileUtil.readFile(FILE_PATH);
    }

}
