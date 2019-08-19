package softuni.exam.domain.dtos.json;

import com.google.gson.annotations.Expose;

public class PictureJsonDto {

    @Expose
    private String url;

    public PictureJsonDto() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
