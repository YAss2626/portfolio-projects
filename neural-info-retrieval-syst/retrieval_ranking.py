import math
from collections import defaultdict
from preprocessing import preprocess_text

def compute_tfidf(tf, df, N, doc_len):
    if df == 0 or doc_len == 0:
        return 0.0
    normalized_tf = tf / doc_len
    idf = math.log2(N / df)
    return normalized_tf * idf

def compute_doc_norms(index, df, N, doc_len):
    doc_sq_sum = defaultdict(float)

    for term, postings in index.items():
        dft = df[term]
        if dft == 0:
            continue
        idf = math.log2(N / dft)

        for doc_id, tf in postings.items():
            L = doc_len[doc_id]
            if L == 0:
                continue
            w = (tf / L) * idf         
            doc_sq_sum[doc_id] += w * w  

    return {doc_id: math.sqrt(s) for doc_id, s in doc_sq_sum.items()}

def cosine_similarity(query_vector, doc_partial_vector, doc_norm_value):

    dot = 0.0
    for term, q_w in query_vector.items():
        dot += q_w * doc_partial_vector.get(term, 0.0)

    q_norm = math.sqrt(sum(w * w for w in query_vector.values()))
    if q_norm > 0 and doc_norm_value > 0:
        return dot / (q_norm * doc_norm_value)
    return 0.0

def retrieve_and_rank(query_text, index, doc_len, df, N, doc_norm, use_stemming=True):
    query_tokens = preprocess_text(query_text, use_stemming=use_stemming)

    query_tf = {}
    for t in query_tokens:
        query_tf[t] = query_tf.get(t, 0) + 1

    # Build query TF-IDF vector
    query_vector = {}
    for term, tf in query_tf.items():
        if term in df:
            query_vector[term] = compute_tfidf(tf, df[term], N, len(query_tokens))

    # Candidate docs: union of postings for all query terms
    candidate_docs = set()
    for term in query_vector:
        if term in index:
            candidate_docs.update(index[term].keys())

    scores = []
    for doc_id in candidate_docs:
        doc_partial = {}
        for term in query_vector:
            if term in index and doc_id in index[term]:
                tf = index[term][doc_id]
                doc_partial[term] = compute_tfidf(tf, df[term], N, doc_len[doc_id])

        score = cosine_similarity(query_vector, doc_partial, doc_norm.get(doc_id, 0.0))
        scores.append((doc_id, score))

    scores.sort(key=lambda x: x[1], reverse=True)
    return scores[:100]