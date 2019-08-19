package softuni.exam.domain.dtos.json;

import com.google.gson.annotations.Expose;

public class TeamJsonDto {
    @Expose
    private String name;
    @Expose
    private PictureJsonDto pictureJsonDto;

    public TeamJsonDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureJsonDto getPictureJsonDto() {
        return this.pictureJsonDto;
    }

    public void setPictureJsonDto(PictureJsonDto pictureJsonDto) {
        this.pictureJsonDto = pictureJsonDto;
    }
}
