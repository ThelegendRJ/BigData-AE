package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.broadcast.Broadcast;
import uk.ac.gla.dcs.bigdata.providedstructures.DocumentRanking;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedstructures.RankedResult;
import uk.ac.gla.dcs.bigdata.providedutilities.DPHScorer;
import uk.ac.gla.dcs.bigdata.studentstructures.Term;
import uk.ac.gla.dcs.bigdata.studentstructures.Article;


import java.util.*;
import java.util.stream.Stream;
 

public class DPHMap implements FlatMapFunction<Article, DocumentRanking> {
	private static final long serialVersionUID = 0;

    Broadcast<List<Query>> qyList;
    //Frequency 
    Broadcast<Term> CTF;
    //Total length
    long Len;
    //Average length 
    long AvgLen;

    public DPHMap() {
    }

    public DPHMap(Broadcast<List<Query>> qyList, Broadcast<Term> CTF, long Len, long AvgLen) {
        this.qyList = qyList;
        this.CTF = CTF;
        this.Len = Len;
        this.AvgLen = AvgLen;
    }

    @Override
    public Iterator<DocumentRanking> call(Article sample) throws Exception {
 
        //Final result
        List<DocumentRanking> sumDph = new ArrayList<>();


        //score of queries
        Map<String, Double> singleDph = new HashMap<>();


        //Frequency of queries 
        Map<String, Long> singleQF = sample.getQF();

        //Article Filtering 
        Stream<Query> qyStream = qyList.getValue().parallelStream()
                .filter(query -> !Collections.disjoint(query.getQueryTerms(), singleQF.keySet()));



        for (Query qy : (Iterable<Query>) qyStream::iterator){
            // Get all queries
            List<String> Terms = qy.getQueryTerms();


            //Calculating the DPH Score
            double sampleDph = 0;
            for (int i=0;i<Terms.size();i++)
            {
                String term =Terms.get(i);
                if (singleDph.containsKey(term)) {
                    sampleDph =sampleDph +singleDph.get(term);
                    continue;
                }
                //Getting the frequency 
             long sumFreq = CTF.getValue().getCTF_Map().getOrDefault(term, 0L);
             long sampleFreq = singleQF.getOrDefault(term, 0L);

                //DPH of current article
                double  dph = DPHScorer.getDPHScore((short) sampleFreq,
                        (int) sumFreq, (int) sample.getLength(), AvgLen,
                        Len);

                //DPHs to 0.
                if (Double.isNaN(dph) || Double.isInfinite(dph))
                    dph = 0.0;

                singleDph.put(term, dph);
                sampleDph += dph;
            }

            //DPH score Average 
           double avgDph = sampleDph / Terms.size();

            List<RankedResult> Ranked = new ArrayList<>(1);
            Ranked.add(new RankedResult(sample.getId(), sample.getNA(), avgDph));

            //Storing all articles

            sumDph.add(new DocumentRanking(qy, Ranked));
        }

        return sumDph.iterator();
    }
}
