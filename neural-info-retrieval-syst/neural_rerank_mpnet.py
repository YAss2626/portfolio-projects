"""
Neural Reranker: MPNet Bi-Encoder
Model: sentence-transformers/all-mpnet-base-v2
General-purpose sentence encoder fine-tuned on semantic similarity.
Uses cosine similarity for reranking.
Pipeline: TF-IDF top-100 → MPNet cosine rerank
"""
import json
import numpy as np
from sentence_transformers import SentenceTransformer
from indexing import build_inverted_index
from retrieval_ranking import retrieve_and_rank, compute_doc_norms

CORPUS_PATH = "corpus.jsonl"
QUERIES_PATH = "queries.jsonl"
QRELS_PATH = "qrels/test.tsv"

def load_corpus_texts(corpus_path):
    doc_text = {}
    with open(corpus_path, "r", encoding="utf-8") as f:
        for line in f:
            d = json.loads(line)
            doc_id = d["_id"]
            doc_text[doc_id] = (d.get("title", "") + " " + d.get("text", "")).strip()
    return doc_text

def load_test_qids_from_qrels(qrels_path):
    qids = set()
    with open(qrels_path, "r", encoding="utf-8") as f:
        f.readline()
        for line in f:
            parts = line.strip().split()
            if parts:
                qids.add(int(parts[0]))
    return qids

def load_test_queries(queries_path, qrels_path):
    valid_qids = load_test_qids_from_qrels(qrels_path)
    queries = []
    with open(queries_path, "r", encoding="utf-8") as f:
        for line in f:
            q = json.loads(line)
            qid = int(q["_id"])
            if qid in valid_qids:
                queries.append((qid, q["text"]))
    queries.sort(key=lambda x: x[0])
    return queries

def cosine_sim_matrix(q_emb, d_embs):
    q = q_emb / (np.linalg.norm(q_emb) + 1e-12)
    D = d_embs / (np.linalg.norm(d_embs, axis=1, keepdims=True) + 1e-12)
    return D @ q  # shape: (num_docs,)

def main():
    print("Building TF-IDF index...")
    index, doc_len, df, N = build_inverted_index(CORPUS_PATH, use_stemming=True, use_title_only=False)
    doc_norm = compute_doc_norms(index, df, N, doc_len)
    doc_text = load_corpus_texts(CORPUS_PATH)
    queries = load_test_queries(QUERIES_PATH, QRELS_PATH)

    print("Loading MPNet model...")
    model = SentenceTransformer("sentence-transformers/all-mpnet-base-v2")

    out_path = "Results_mpnet_rerank.txt"
    run_name = "mpnet_rerank"

    print(f"Reranking {len(queries)} queries with MPNet...")
    with open(out_path, "w", encoding="utf-8") as out:
        for i, (qid, query_text) in enumerate(queries):
            if i % 10 == 0:
                print(f"  {i+1}/{len(queries)}: qid={qid}")

            base_results = retrieve_and_rank(query_text, index, doc_len, df, N, doc_norm, use_stemming=True)
            cand_doc_ids = [doc_id for doc_id, _ in base_results]
            cand_texts = [doc_text[d] for d in cand_doc_ids]

            q_emb = model.encode(query_text, convert_to_numpy=True, normalize_embeddings=False)
            d_embs = model.encode(cand_texts, convert_to_numpy=True, normalize_embeddings=False,
                                  batch_size=32, show_progress_bar=False)

            scores = cosine_sim_matrix(q_emb, d_embs)
            reranked = sorted(zip(cand_doc_ids, scores), key=lambda x: x[1], reverse=True)

            for rank, (doc_id, score) in enumerate(reranked, start=1):
                out.write(f"{qid} Q0 {doc_id} {rank} {float(score):.6f} {run_name}\n")

    print(f"Done. Written to: {out_path}")

if __name__ == "__main__":
    main()