**Big Data - Apache Spark**

This project focuses on designing, implementing, and performance testing a Big Data analysis task using Apache Spark. The goal is to develop a batch-based text search and filtering pipeline that ranks news articles based on user-defined queries while ensuring redundancy filtering. The project involves processing a large dataset, implementing efficient Spark transformations, and evaluating performance.

**Dataset**

The dataset consists of news articles from the Washington Post and a set of queries. There are two versions of the dataset:

* Sample Dataset: Contains 5,000 news articles and three queries, used for local Spark deployments.

* Full Dataset: Comprises approximately 670,000 news articles.

**Query Structure**

Each query consists of:

* originalQuery: The unaltered query text.

* queryTerms: The tokenized, stopword-removed, and stemmed terms.

* queryTermCounts: The frequency of each query term.

**News Article Structure**

Each news article includes:

* id: Unique identifier.

* article_url: URL to the article.

* title: Title of the article.

* author: Author of the article.

* published_date: Unix timestamp of publication.

* contents: List of content elements (e.g., paragraphs, images, headers).

* type: Type of article.

* source: News provider.

**Objectives**

* Text Preprocessing: Tokenize, remove stopwords, and apply stemming to query and article content.

* Relevance Scoring: Implement the DPH ranking model to score documents for each query.

* Redundancy Filtering: Remove overly similar documents based on string distance calculations.

* Efficiency Optimization: Design Spark transformations for scalable execution.

* Evaluation: Measure performance, quality of results, and resource efficiency.

**Implementation Steps**

* Load Data: Read and parse queries and news articles into Spark Datasets.

* Preprocessing: Tokenize, remove stopwords, and stem query terms (provided function).Extract relevant content from news articles (title and first five valid paragraphs).

* Compute DPH Scores: Calculate term frequency for each document.Compute document length, average document length, and corpus-wide term frequencies.Calculate DPH score for each <document, query> pair.

* Redundancy Filtering:Compute textual distance between document titles. Remove documents with a distance < 0.5, keeping the most relevant.
 
* Generate Output: Rank and return the top 10 documents for each query.
