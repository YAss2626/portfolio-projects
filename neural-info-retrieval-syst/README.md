# Neural Information Retrieval System
**Author:** Yacine Dosso  
**Stack:** Python, NLTK, sentence-transformers, TensorFlow Hub  
**Dataset:** [SciFact](https://github.com/beir-cellar/beir) : scientific claim verification corpus

---

## Overview

This project implements a full Information Retrieval pipeline on the SciFact dataset, progressing from a classical TF-IDF baseline to neural reranking using two pre-trained sentence embedding models.

The system indexes the SciFact corpus, retrieves candidate documents using TF-IDF cosine similarity, and reranks the top-100 results using dense neural embeddings. The goal is to compare how well keyword-based retrieval and semantic retrieval handle scientific language; where relevant documents often use different vocabulary than the query itself.

This project also served as a direct bridge to my interest in Music Information Retrieval. The core pipeline,indexing, retrieval, and reranking; maps directly onto audio search problems: instead of matching query text to scientific documents, the same architecture can match a query song to a corpus of audio tracks. Understanding how neural embeddings outperform keyword matching in text IR raises the same question for audio: can learned audio embeddings capture musical similarity better than handcrafted features like MFCCs?

---

## Pipeline

```
corpus.jsonl + queries.jsonl
        ↓
Preprocessing (tokenization, stopwords, stemming)
        ↓
Inverted Index (TF per term per doc, DF, doc lengths)
        ↓
TF-IDF Retrieval → Top-100 candidates per query
        ↓
Neural Reranking (MPNet or USE)
        ↓
Evaluation (MAP, P@10 via trec_eval)
```

---

## System Components

### preprocessing.py
Each document and query is processed through:
- Lowercasing
- Tokenization (alphabetic tokens only, via regex)
- Stopword removal (NLTK English stopword list)
- Porter stemming

This ensures consistent term matching between queries and documents, and reduces vocabulary noise.

### indexing.py
Builds an inverted index mapping each term to a dictionary of document IDs and their term frequencies. Also stores:
- `doc_len` - number of tokens per document
- `df` -document frequency per term
- `N` - total number of documents

Vocabulary size: **20,866 unique terms** across the SciFact corpus.

### retrieval_ranking.py - TF-IDF Baseline
For each query:
1. Builds a TF-IDF weighted query vector
2. Restricts candidate documents to those containing at least one query term (efficient pruning)
3. Computes cosine similarity between the query vector and each candidate document vector
4. Returns the top-100 ranked documents

TF-IDF weighting formula used:
```
w(t, d) = (tf / doc_len) * log2(N / df)
```

### neural_rerank_mpnet.py - MPNet Reranker
Reranks the TF-IDF top-100 using `sentence-transformers/all-mpnet-base-v2`:
- Encodes query and candidate documents into **768-dimensional dense vectors**
- Computes cosine similarity between query and document embeddings
- MPNet was fine-tuned on semantic similarity tasks,  it captures meaning beyond keyword overlap

### neural_rerank_USE.py - USE Reranker
Reranks the TF-IDF top-100 using Google's Universal Sentence Encoder (v4):
- Produces **512-dimensional sentence embeddings**
- Trained on web text and conversational data
- Cosine similarity used for reranking

### evaluate.py
Computes standard IR metrics from TREC-format results files:
- **MAP** (Mean Average Precision) - measures ranking quality across all queries
- **P@10** (Precision at 10) - measures how many of the top-10 results are relevant

---

## Results

| System | MAP | P@10 |
|---|---|---|
| TF-IDF baseline (run 1) | 0.5250 | 0.0778 |
| TF-IDF baseline (run 2) | 0.5290 | 0.0833 |
| **MPNet reranker** | **0.6327** | **0.0940** |
| USE reranker | 0.3426 | 0.0560 |

The submitted `Results` file contains the MPNet reranker output, as it achieved the best overall performance.

---

## Discussion

**TF-IDF baseline** achieved a MAP of 0.5290, a reasonable result for a keyword-based system, but limited by its inability to handle vocabulary mismatch. A query asking about "neural plasticity" may miss a relevant document that uses "brain adaptation" instead.

**MPNet reranker** improved MAP by 19.6% over the baseline (0.6327 vs 0.5290). MPNet's strength comes from its fine-tuning on semantic similarity tasks; it understands sentence meaning rather than relying on term overlap. This is particularly valuable in scientific retrieval where vocabulary varies widely between queries and documents.

**USE reranker** performed worse than all systems (MAP: 0.3426). Despite being a strong general-purpose encoder, USE was trained primarily on conversational and web data, making it poorly suited for scientific language. This highlights a key insight: not all embeddings are equal, domain matters. A model trained on music audio would likely outperform a general-purpose audio encoder on music-specific retrieval tasks, just as MPNet outperforms USE on scientific text.

---

## Connection to Music Information Retrieval

Building this system made the parallel to audio retrieval concrete. The same architectural decisions apply:

| Text IR | Audio IR (MIR) |
|---|---|
| Inverted index over terms | Inverted index over audio features or tags |
| TF-IDF query vector | Feature vector (MFCCs, chroma, tempo) |
| Neural sentence embeddings | Neural audio embeddings (e.g. openl3, CLAP) |
| Cosine similarity | Cosine similarity |
| MAP / P@10 evaluation | MAP / P@10 evaluation |

The key question this project raised for me: if MPNet's semantic embeddings outperform TF-IDF on text, do learned audio embeddings outperform handcrafted features like MFCCs on music retrieval? This is exactly the question I explore in the playlist recommender project, and it is the broader research question I want to pursue at NYU.

---

## How to Run

### Requirements
```bash
pip install nltk sentence-transformers tensorflow tensorflow-hub
```

### 1. Run TF-IDF baseline
```bash
python runthesystem.py
```
Generates `Results_baseline_tfidf.txt`

### 2. Run neural rerankers
```bash
python neural_rerank_mpnet.py
python neural_rerank_USE.py
```
Generates `Results_mpnet_rerank.txt` and `Results_use_rerank.txt`

### 3. Evaluate
```bash
python evaluate.py Results
python evaluate.py Results_mpnet_rerank.txt
python evaluate.py Results_use_rerank.txt
```

---

## Project Structure

```
Neural_info_retrieval_syst/
├── preprocessing.py          # Tokenization, stopwords, stemming
├── indexing.py               # Inverted index builder
├── retrieval_ranking.py      # TF-IDF retrieval
├── runthesystem.py           # Full baseline pipeline
├── neural_rerank_mpnet.py    # MPNet reranker
├── neural_rerank_USE.py      # USE reranker
├── evaluate.py               # MAP and P@10 evaluation
├── Results                   # Submitted results (MPNet)
├── Results_mpnet_rerank.txt
├── Results_use_rerank.txt
├── qrels/
│   └── test.tsv
├── queries.jsonl
└── corpus.jsonl
```

---

## References

- Tzanetakis & Cook (2002): Musical Genre Classification of Audio Signals
- Reimers & Gurevych (2019):  Sentence-BERT: Sentence Embeddings using Siamese BERT-Networks
- Cer et al. (2018): Universal Sentence Encoder
- Thakur et al. (2021):  BEIR: A Heterogeneous Benchmark for Zero-shot Evaluation of Information Retrieval Models
- NLTK:  stopwords and Porter Stemmer
- sentence-transformers/all-mpnet-base-v2 (HuggingFace)
- Universal Sentence Encoder v4 (TensorFlow Hub)
