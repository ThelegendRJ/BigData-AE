package uk.ac.gla.dcs.bigdata.studentstructures;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Term implements Serializable{
	   private static final long serialVersionUID=0;

	    Map<String, Long> CTF_Map;


	    public Term(Map<String, Long> CTF_Map) {
	        this.CTF_Map= CTF_Map;
	    }

	    public Term combine(Term newMap) {
	        Map<String, Long> combineMap = new HashMap<>(CTF_Map);
	        Set<Map.Entry<String, Long>> entrySet = newMap.getCTF_Map().entrySet();
	        Iterator<Map.Entry<String, Long>> iterator = entrySet.iterator();
	        for (int i = 0; i < entrySet.size(); i++) {
	            Map.Entry<String, Long> entry = iterator.next();
	            String key = entry.getKey();
	            Long value = entry.getValue();
	            combineMap.merge(key, value, Long::sum);  
	        }

	        return new Term(combineMap);
	    }


	    public Map<String, Long> getCTF_Map() {
	        return CTF_Map;
	    }

	    @Override
	    public String toString() {
	        return "CorpusTermFre{" +
	                "corpusTermFreMap=" + CTF_Map +
	                '}';
	    }
}
