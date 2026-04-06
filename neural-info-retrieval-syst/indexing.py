
from collections import defaultdict
from preprocessing import preprocess_text, process_corpus
import json


corpus_path = 'corpus.jsonl'
processed_data = process_corpus(corpus_path)


def build_inverted_index(corpus_path, use_stemming=True, use_title_only=False):
    index = defaultdict(lambda: defaultdict(int))
    doc_len = {}
    df = defaultdict(int)

    N = 0

    with open(corpus_path, "r", encoding="utf-8") as f:
        for line in f:
            doc = json.loads(line)
            doc_id = doc["_id"]
            if use_title_only:
                text = doc.get("title", "")
            else:
                text = doc.get("title", "") + " " + doc.get("text", "")
            tokens = preprocess_text(text, use_stemming=use_stemming)
            doc_len[doc_id] = len(tokens)

            # Count TF per term in this doc
            term_counts = defaultdict(int)
            for t in tokens:
                term_counts[t] += 1

            # Fill inverted index
            for term, tf in term_counts.items():
                index[term][doc_id] = tf
                df[term] += 1  # term appears in this doc

            N += 1

    return index, doc_len, df, N

#TEST INDEXING (PART 2)

index, doc_len, df, N = build_inverted_index(corpus_path, use_stemming=True)

print("N docs =", N)
print("Vocabulary size =", len(index))
print("doc_len size =", len(doc_len))
print("df size =", len(df))