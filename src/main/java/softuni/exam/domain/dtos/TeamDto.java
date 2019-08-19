package softuni.exam.domain.dtos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDto {

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "picture")
    private PictureDto pictureDto;

    public TeamDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureDto getPictureDto() {
        return this.pictureDto;
    }

    public void setPictureDto(PictureDto pictureDto) {
        this.pictureDto = pictureDto;
    }
}
