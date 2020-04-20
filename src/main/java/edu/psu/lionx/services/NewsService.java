package edu.psu.lionx.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.psu.lionx.Exceptions.LionXEmptyEnvException;
import edu.psu.lionx.domain.gson.Article;
import edu.psu.lionx.domain.gson.NewsArticles;
import edu.psu.lionx.utils.UrlUtil;
import edu.psu.lionx.utils.dao.HttpRequestSender;
import edu.psu.lionx.utils.dao.RequestSender;
import edu.psu.lionx.utils.http.Request;
import edu.psu.lionx.utils.http.Response;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NewsService {
    private String NEWS_API_KEY;
    public static String BASE_URL = "https://newsapi.org/v2/everything";

    public NewsService() throws LionXEmptyEnvException {
        Dotenv dotenv = Dotenv.load();
        NEWS_API_KEY = dotenv.get("NEWS_API_KEY");
        if ( NEWS_API_KEY == null || NEWS_API_KEY.isEmpty() )
            throw new LionXEmptyEnvException("No value found for NEWS_API_KEY. Please ensure you have added this " +
                    "to your .env file");
    }

    public List<Article> getNews(HashMap<String, String> params) throws IOException {
        params.put("apiKey", NEWS_API_KEY);

        RequestSender sender = new HttpRequestSender();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Request request = new Request(UrlUtil.buildUrlQuery(BASE_URL, params));
        Response response = sender.sendRequest(request);
        NewsArticles articles = gson.fromJson(response.getBody(), NewsArticles.class);
        return articles.getArticles();
    }


    public enum ENDPOINT {
        TOP_HEADLINES,
        EVERYTHING,
        SOURCES
    }

    public static class NewsSearchBuilder {
        ENDPOINT endPoint = ENDPOINT.TOP_HEADLINES;
        String language;
        String country;
        String sources;
        String query;

        public NewsSearchBuilder() {

        }

        public NewsSearchBuilder endPoint(NewsService.ENDPOINT endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public NewsSearchBuilder language(String language) {
            this.language = language;
            return this;
        }

        public NewsSearchBuilder country(String country) {
            this.country = country;
            return this;
        }

        public NewsSearchBuilder sources(String sources) {
            this.sources = sources;
            return this;
        }

        public NewsSearchBuilder query(String query) {
            this.query = query.trim().replaceAll(" ", "");
            return this;
        }

        public HashMap<String, String> build() {
            HashMap<String, String> params = new HashMap<>();
            buildQuery(params);
            return params;
        }

        private void buildQuery(HashMap<String, String> params) {
            if (this.query != null)
                params.put("q", query);
        }

        private String buildCountry() {
            return this.country == null ? "" : "country=" + this.country;
        }

        private String buildLanguage() {
            return this.language == null ? "" : "language=" + this.language;
        }

        private String buildEndPoint() {
            switch (this.endPoint) {
                case SOURCES:
                    return "sources";
                case EVERYTHING:
                    return "everything";
                default:
                    return "top-headlines";
            }
        }
    }
}
