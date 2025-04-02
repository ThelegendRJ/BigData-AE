package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.util.LongAccumulator;
import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedutilities.TextPreProcessor;
import uk.ac.gla.dcs.bigdata.studentstructures.Article;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class NewsMap implements FlatMapFunction<NewsArticle, Article> {
	private static final long serialVersionUID = 0;

    private transient TextPreProcessor TextPreProcessor;

    private final LongAccumulator article_LongAccumulator;

    public NewsMap(LongAccumulator article_LongAccumulator) {
        this.article_LongAccumulator = article_LongAccumulator;
    }

    @Override
    public Iterator<Article> call(NewsArticle sample) {
        //Remove stop words

        TextPreProcessor=(TextPreProcessor == null)?new TextPreProcessor():TextPreProcessor;
        //processing paragraph
        List<String> text = sample.getContents().stream()
                //Filtering
                .filter(text1 -> text1 != null && "paragraph".equalsIgnoreCase(text1.getSubtype()))
                .limit(5)
                //using flat map to flatten 
                .flatMap(text1 -> TextPreProcessor.process(text1.getContent()).stream())
                .collect(Collectors.toList());

        //Getting title

        text.addAll(TextPreProcessor.process(sample.getTitle()));

        article_LongAccumulator.add(text.size());

        //Storing each term for DPH calculation
        Map<String, Long> wordFrequency = text.stream()
                .collect(Collectors.groupingBy(n->n,Collectors.counting()));


        // Returning an immutable list
        return Collections.singletonList(
                new Article(
                        sample.getId(),
                        sample.getTitle(),
                        wordFrequency,
                        text.size(),
                        sample
                )
        ).iterator();
    }


}
