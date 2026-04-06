"""
Evaluation: computes MAP and P@10 from a TREC-format results file.
Usage: python evaluate.py <results_file>
Example: python evaluate.py Results
         python evaluate.py Results_crossencoder_rerank.txt
"""
import sys
from collections import defaultdict

def load_qrels(qrels_path):
    qrels = defaultdict(set)
    with open(qrels_path, "r", encoding="utf-8") as f:
        f.readline()  # skip header
        for line in f:
            parts = line.strip().split("\t")  
            if len(parts) >= 3:
                qid = int(parts[0])
                doc_id = parts[1]
                relevance = int(parts[2])
                if relevance > 0:
                    qrels[qid].add(doc_id)
    return qrels

def load_results(results_path):
    results = defaultdict(list)
    with open(results_path, "r", encoding="utf-8") as f:
        for line in f:
            parts = line.strip().split()
            if len(parts) >= 6:
                qid, doc_id, rank = int(parts[0]), parts[2], int(parts[3])
                results[qid].append((doc_id, rank))
    for qid in results:
        results[qid].sort(key=lambda x: x[1])
    return results

def compute_ap(ranked_docs, relevant_set):
    if not relevant_set: return 0.0
    hits, precision_sum = 0, 0.0
    for rank_idx, (doc_id, _) in enumerate(ranked_docs, 1):
        if doc_id in relevant_set:
            hits += 1
            precision_sum += hits / rank_idx
    return precision_sum / len(relevant_set)

def compute_p_at_k(ranked_docs, relevant_set, k=10):
    if not relevant_set: return 0.0
    hits = sum(1 for doc_id, _ in ranked_docs[:k] if doc_id in relevant_set)
    return hits / k

def evaluate(results_path, qrels_path="qrels/test.tsv"):
    qrels = load_qrels(qrels_path)
    results = load_results(results_path)
    all_qids = sorted(qrels.keys())
    ap_scores, p10_scores = [], []

    print(f"\n{'='*60}")
    print(f"Evaluating: {results_path}")
    print(f"{'QID':<10} {'AP':>10} {'P@10':>10} {'#Rel':>8} {'#Ret':>8}")
    print("-"*50)

    for qid in all_qids:
        relevant = qrels[qid]
        ranked = results.get(qid, [])
        ap = compute_ap(ranked, relevant)
        p10 = compute_p_at_k(ranked, relevant)
        ap_scores.append(ap)
        p10_scores.append(p10)
        print(f"{qid:<10} {ap:>10.4f} {p10:>10.4f} {len(relevant):>8} {len(ranked):>8}")

    map_score = sum(ap_scores) / len(ap_scores) if ap_scores else 0.0
    mean_p10 = sum(p10_scores) / len(p10_scores) if p10_scores else 0.0
    print("-"*50)
    print(f"{'MAP':<10} {map_score:>10.4f}")
    print(f"{'P@10':<10} {mean_p10:>10.4f}")
    return map_score, mean_p10

if __name__ == "__main__":
    path = sys.argv[1] if len(sys.argv) > 1 else "Results"
    evaluate(path)