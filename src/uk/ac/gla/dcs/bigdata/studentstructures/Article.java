package uk.ac.gla.dcs.bigdata.studentstructures;

import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;

import java.io.Serializable;
import java.util.Map;


public class Article implements Serializable{
    private static final long serialVersionUID = 0;

    public String article_id;    //Article ID  
    public String article_title; //Article Title
    public Map<String, Long> QF; //Query Frequency
    public long length;			 //Article Length
    public NewsArticle nA;		 //Current Article

    public Article(String article_id, String article_title, Map<String, Long> QF,
                            long length, NewsArticle nA)
    {
        this.article_id = article_id;
        this.article_title = article_title;
        this.QF= QF;
        this.length = length;
        this.nA = nA;
    }

    public String getId() {
        return article_id;
    }

    public void setId(String id) {
        this.article_id = id;
    }

    public String getTitle() {
        return article_title;
    }

    public void setTitle(String article_title) {
        this.article_title = article_title;
    }

    public Map<String, Long> getQF() {
        return QF;
    }

    public void setQF(Map<String, Long> QF) {this.QF = QF;}

    public long getLength() {
        return length;
    }

    public void setLength(long length)
    {
        this.length = length;
    }

    public NewsArticle getNA() {
        return nA;
    }

    public void setNA(NewsArticle nA) {
        this.nA = nA;
    }

    @Override
    public String toString() {
        return "NewsArticleBrief{" +
                "article_id='" + article_id + '\'' +
                ", article_title='" + article_title + '\'' +
                ", everyQueryFre=" + QF +
                ", lengthAfterPrePro=" + length +
                ", newsArticle=" + nA +
                '}';
    }
}
