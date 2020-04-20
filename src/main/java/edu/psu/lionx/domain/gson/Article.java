package edu.psu.lionx.domain.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Article {
    @SerializedName("source")
    private Source source;
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("content")
    private String content;

    private transient ImageView image;

    public void buildImage() {
        String url = this.urlToImage == null
                ? "https://prolifecampaign.ie/main/wp-content/uploads/2012/03/placeholder2-300x156.jpg"
                : this.urlToImage;
        Image image = new Image(url, true);
        this.image = new ImageView(image);
        this.image.setFitWidth(100);
        this.image.setFitHeight(100);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setSource(Source source){
        this.source = source;
    }
    public Source getSource(){
        return this.source;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setUrlToImage(String urlToImage){
        this.urlToImage = urlToImage;
    }
    public String getUrlToImage(){
        return this.urlToImage;
    }
    public void setPublishedAt(String publishedAt){
        this.publishedAt = publishedAt;
    }
    public String getPublishedAt(){
        return this.publishedAt;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
}
