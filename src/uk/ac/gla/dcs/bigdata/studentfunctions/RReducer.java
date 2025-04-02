package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.ReduceFunction;
import uk.ac.gla.dcs.bigdata.providedstructures.DocumentRanking;
import uk.ac.gla.dcs.bigdata.providedstructures.RankedResult;
import uk.ac.gla.dcs.bigdata.providedutilities.TextDistanceCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RReducer implements ReduceFunction<DocumentRanking> {
	 private static final long serialVersionUID = 0;

	    @Override
	    public DocumentRanking call(DocumentRanking DR1, DocumentRanking DR2)
	    {
	        // Joining the RankedResult
	        List<RankedResult> samplelist = new ArrayList<>();
	        samplelist.addAll(DR1.getResults());
	        samplelist.addAll(DR2.getResults());

	        //Sorting
	        List<RankedResult> Results = samplelist.stream()
	                .sorted(Comparator.comparing(RankedResult::getScore).reversed())
	                .collect(Collectors.toList());

	        List<RankedResult> FinalResults = new ArrayList<>();
	        for (int i = 0; i < Results.size(); i++) {

	            //Comparing the articles
	            if (FinalResults.isEmpty()) {
	                FinalResults.add(Results.get(i));
	            }
	            else {
	                for (int n = 0; n < FinalResults.size(); )
	                {
	                    //Filtering redundancy
	                    if (TextDistanceCalculator.similarity
	                            (FinalResults.get(n).getArticle().getTitle(), Results.get(i).getArticle().getTitle()) < 0.5)
	                    {
	                        break;
	                    }
	                    else
	                    {
	                        FinalResults.add(Results.get(i));
	                        break;
	                    }
	                }
	            }
	            if (FinalResults.size() >= 10) {
	                break;
	            }
	        }

	        // To get the top 10 result
	        DR1.setResults(FinalResults);
	        return DR1;
	    }

}
