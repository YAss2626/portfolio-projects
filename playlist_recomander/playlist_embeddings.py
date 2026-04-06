"""
Embedding-Based Playlist Recommender- Extension
--------------------------------------------------
This script extends the content-based playlist recommender by comparing
two approaches to audio similarity:

1. HANDCRAFTED FEATURES (baseline): MFCCs, chroma, spectral contrast, tempo, etc.
   → 50-dimensional vector per song, manually engineered

2. LEARNED EMBEDDINGS (openl3): deep audio embeddings from a pre-trained model
   → 512-dimensional vector per song, learned from large-scale audio/visual data

The goal is to test whether learned embeddings capture genre nuance that
standard feature vectors miss; particularly for acoustically ambiguous genres
like disco, rock, and country that the baseline struggles with.

Author: Yacine Dosso
"""

import os
import numpy as np
import openl3
import soundfile as sf
import librosa
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
import warnings
warnings.filterwarnings('ignore')

X_scaled = np.load("X_scaled.npy")
filenames = np.load("filenames.npy", allow_pickle=True)
labels = np.load("labels.npy", allow_pickle=True)


# CONFIGURATION


DATASET_PATH = r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original"

# Songs to compare both approaches on, pick one per genre for variety
TEST_SONGS = [
    "blues.00000.wav",
    "classical.00000.wav",
    "metal.00000.wav",
    "hiphop.00000.wav",
    "jazz.00000.wav",
    "disco.00000.wav",   # disco is often confused, interesting test case
    "rock.00000.wav",    # same for rock
]


# LOAD DATASET FILE LIST


print("Loading dataset file list...")
genres_list = os.listdir(DATASET_PATH)
filenames = []
labels = []

for g in genres_list:
    genre_path = os.path.join(DATASET_PATH, g)
    for a in os.listdir(genre_path):
        if a.endswith('.wav'):
            filenames.append(a)
            labels.append(g)

filenames = np.array(filenames)
labels = np.array(labels)
print(f"Found {len(filenames)} files across {len(genres_list)} genres")



# EXTRACT OPENL3 EMBEDDINGS

# openl3 loads a pre-trained deep neural network (trained on AudioSet + visual data)
# and extracts a 512-dimensional embedding for each audio file.
# We average the embeddings across time to get one fixed-size vector per song.

print("\nExtracting openl3 embeddings... (this takes ~15-20 min for 999 files)")
print("openl3 uses a pre-trained CNN- no training needed, just inference.\n")

embeddings = []
valid_mask = []

if os.path.exists("embeddings_clean.npy"):
    print("Loading saved embeddings...")
    embeddings_clean = np.load("embeddings_clean.npy")
    filenames_clean = np.load("filenames_clean.npy", allow_pickle=True)
    labels_clean = np.load("labels_clean.npy", allow_pickle=True)
else:
    for i, (fname, genre) in enumerate(zip(filenames, labels)):
        fpath = os.path.join(DATASET_PATH, genre, fname)
        try:
            audio, sr = sf.read(fpath)
            if audio.ndim > 1:
                audio = np.mean(audio, axis=1)
            audio = audio.astype(np.float32)
            emb, ts = openl3.get_audio_embedding(
                audio, sr,
                content_type='music',
                embedding_size=512,
                hop_size=0.5,
                verbose=False
            )
            embeddings.append(np.mean(emb, axis=0))
            valid_mask.append(True)
            if (i + 1) % 100 == 0:
                print(f"  Processed {i+1}/{len(filenames)} files...")
        except Exception as e:
            print(f"  Skipped {fname}: {e}")
            embeddings.append(None)
            valid_mask.append(False)

    # Clean up and save results
    valid_mask = np.array(valid_mask)
    embeddings_clean = np.array([e for e in embeddings if e is not None])
    filenames_clean = filenames[valid_mask]
    labels_clean = labels[valid_mask]

    print(f"\nDone! {len(embeddings_clean)} embeddings extracted successfully")
    
    np.save("embeddings_clean.npy", embeddings_clean)
    np.save("filenames_clean.npy", filenames_clean)
    np.save("labels_clean.npy", labels_clean)
    print("Embeddings saved!")


# RECOMMENDATION FUNCTIONS
 

# Scale embeddings
scaler_emb = StandardScaler()
embeddings_scaled = scaler_emb.fit_transform(embeddings_clean)


def recommend_by_embeddings(filename, n=5):
    """Recommend using openl3 learned embeddings."""
    idx = np.where(filenames_clean == filename)[0]
    if len(idx) == 0:
        print(f"File {filename} not found in embedding set.")
        return [], [], []
    idx = idx[0]
    target = embeddings_scaled[idx]
    sims = cosine_similarity([target], embeddings_scaled)[0]
    sorted_idx = np.argsort(sims)[::-1]
    # Exclude the query song itself
    top_idx = [i for i in sorted_idx if i != idx][:n]
    return (
        [(i, sims[i]) for i in top_idx],
        [labels_clean[i] for i in top_idx],
        [filenames_clean[i] for i in top_idx]
    )


def recommend_by_features(filename, X_scaled, filenames_all, labels_all, n=5):
    """Recommend using handcrafted features (baseline)."""
    idx = np.where(filenames_all == filename)[0]
    if len(idx) == 0:
        return [], [], []
    idx = idx[0]
    target = X_scaled[idx]
    sims = cosine_similarity([target], X_scaled)[0]
    sorted_idx = np.argsort(sims)[::-1]
    top_idx = [i for i in sorted_idx if i != idx][:n]
    return (
        [(i, sims[i]) for i in top_idx],
        [labels_all[i] for i in top_idx],
        [filenames_all[i] for i in top_idx]
    )


# COMPARING BOTH APPROACHES

print("COMPARISON: Handcrafted Features vs. Learned Embeddings")


for song in TEST_SONGS:
    genre = song.split('.')[0]
    print(f"\nQuery: {song} [{genre.upper()}]")

    # Features-based
    res_f, genres_f, files_f = recommend_by_features(
        song, X_scaled, filenames, labels
    )
    print("  Handcrafted Features:")
    for i in range(min(5, len(files_f))):
        match = "OK" if genres_f[i] == genre else "X"
        print(f"    {match} {files_f[i]} [{genres_f[i]}] - sim: {res_f[i][1]:.4f}")

    # Embedding-based
    res_e, genres_e, files_e = recommend_by_embeddings(song)
    print("  OpenL3 Embeddings:")
    for i in range(min(5, len(files_e))):
        match = "OK" if genres_e[i] == genre else "X"  # uppercase x for embeddings to differentiate
        print(f"    {match} {files_e[i]} [{genres_e[i]}] — sim: {res_e[i][1]:.4f}")



# VISUALIZATION 

def plot_comparison(song):
    genre = song.split('.')[0]

    res_f, genres_f, files_f = recommend_by_features(
        song, X_scaled, filenames, labels
    )
    res_e, genres_e, files_e = recommend_by_embeddings(song)

    fig, axes = plt.subplots(1, 2, figsize=(14, 5))
    fig.suptitle(
        f'Recommendation Comparison for {song} [{genre.upper()}]',
        fontsize=13, fontweight='bold'
    )

    for ax, results, genre_list, file_list, title in [
        (axes[0], res_f, genres_f, files_f, "Handcrafted Features (50-dim)"),
        (axes[1], res_e, genres_e, files_e, "OpenL3 Embeddings (512-dim)")
    ]:
        labels_plot = [f"{file_list[i]}\n[{genre_list[i]}]" for i in range(len(file_list))]
        sims = [results[i][1] for i in range(len(results))]
        colors = ['#2ecc71' if genre_list[i] == genre else '#e74c3c'
                  for i in range(len(genre_list))]

        ax.bar(labels_plot, sims, color=colors)
        ax.set_title(title, fontsize=11)
        ax.set_ylabel('Cosine Similarity')
        ax.set_ylim(0, 1)
        ax.axhline(y=0.7, color='gray', linestyle='--', alpha=0.5)
        ax.tick_params(axis='x', rotation=30, labelsize=7)

        # Legend
        from matplotlib.patches import Patch
        legend = [Patch(color='#2ecc71', label='Same genre'),
                  Patch(color='#e74c3c', label='Different genre')]
        ax.legend(handles=legend, fontsize=8)

    plt.tight_layout()
    fname = f"comparison_{genre}.png"
    plt.savefig(fname, dpi=150)
    plt.show()
    print(f"Saved: {fname}")


print("\nGenerating comparison visualizations...")
for song in TEST_SONGS:
    plot_comparison(song)

print("\nAll done!")
print("Green bars = same genre as query | Red bars = different genre")
print("Compare left vs right to see where embeddings outperform features.")