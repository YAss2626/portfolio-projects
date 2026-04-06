import json
from indexing import build_inverted_index
from retrieval_ranking import retrieve_and_rank, compute_doc_norms

CORPUS_PATH = "corpus.jsonl"
QUERIES_PATH = "queries.jsonl"
QRELS_PATH = "qrels/test.tsv"

def load_test_qids_from_qrels(qrels_path):
    qids = set()
    with open(qrels_path, "r", encoding="utf-8") as f:
        header = f.readline()  
        for line in f:
            parts = line.strip().split()
            if len(parts) >= 1:
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

def run_experiment(use_title_only, output_path, run_name):
    print(f"\nBuilding inverted index (Title Only = {use_title_only})")

    index, doc_len, df, N = build_inverted_index(
        CORPUS_PATH,
        use_stemming=True,
        use_title_only=use_title_only
    )

    doc_norm = compute_doc_norms(index, df, N, doc_len)
    print("doc_norm size =", len(doc_norm), "out of N =", N)

    print("Loading test queries")
    queries = load_test_queries(QUERIES_PATH, QRELS_PATH)
    

    print("Running retrieval")
    with open(output_path, "w", encoding="utf-8") as out:
        for qid, query_text in queries:
            results = retrieve_and_rank(query_text, index, doc_len, df, N, doc_norm, use_stemming=True)

            for rank, (doc_id, score) in enumerate(results, start=1):
                out.write(f"{qid} Q0 {doc_id} {rank} {score:.6f} {run_name}\n")

    print(f"Finished. Results written to {output_path}")

def main():
    # # RUN 1: TITLE ONLY
    # run_experiment(
    #     use_title_only=True,
    #     output_path="Results_TitleOnly.txt",
    #     run_name="tfidf_title_only"
    # )

    run_experiment(
        use_title_only=False,
        output_path="Results_baseline_tfidf.txt",
        run_name="tfidf_title_plus_text"
    )

if __name__ == "__main__":
    main()