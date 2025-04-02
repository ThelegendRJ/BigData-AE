It is a batch-based text search and filtering pipeline in Apache Spark. The core goal of 
this pipeline is to take in a large set of text documents and a set of user defined queries, then for 
each query, rank the text documents by relevance for that query, as well as filter out any overly 
similar documents in the final ranking. The top 10 documents for each query should be returned as 
output. Each document and query should be processed to remove stopwords (words with little 
discriminative value, e.g. ‘the’) and apply stemming (which converts each word into its ‘stem’, a 
shorter version that helps with term mismatch between documents and queries). Documents should 
be scored using the DPH ranking model. As a final stage, the ranking of documents for each query 
should be analysed to remove unneeded redundancy (near duplicate documents), if any pairs of 
documents are found where their titles have a textual distance (using a comparison function 
provided) less than 0.5 then you should only keep the most relevant of them (based on the DPH 
score). Note that there should be 10 documents returned for each query, even after redundancy 
filtering. 
