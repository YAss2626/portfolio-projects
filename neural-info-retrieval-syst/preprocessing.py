import json
import re
import nltk
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
import math
from collections import defaultdict

nltk.download('stopwords')


stop_words = set(stopwords.words('english'))
stemmer = PorterStemmer()

def preprocess_text(text, use_stemming=True):

    # Lowercase
    text = text.lower()

    # Tokenization (keep only alphabetic words)
    tokens = re.findall(r"[a-z]+", text)

    # Stopword removal + short token removal
    filtered_tokens = [
        token for token in tokens
        if token not in stop_words and len(token) > 1
    ]

    if use_stemming:
        filtered_tokens = [stemmer.stem(token) for token in filtered_tokens]
    return filtered_tokens

def process_corpus(corpus_path):
    all_documents_tokens = {}
    with open(corpus_path, 'r', encoding='utf-8') as f:
        for line in f:
                doc = json.loads(line)
                doc_id = doc['_id']
                text = doc.get('title', '') + " " + doc.get('text', '')
                tokens = preprocess_text(text)
                all_documents_tokens[doc_id] = tokens
    return all_documents_tokens


corpus_path = 'corpus.jsonl'
processed_data = process_corpus(corpus_path)


#TEST PREPROCESSING (PART 1)
print(f"Processed {len(processed_data)} documents.")
if len(processed_data) > 0:
    first_id = list(processed_data.keys())[0]
    print(f"Sample tokens for doc {first_id}: {processed_data[first_id][:10]}")