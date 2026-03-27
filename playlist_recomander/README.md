# Content-Based Playlist Recommender
**Author:** Yacine Dosso  
**Stack:** Python, librosa, scikit-learn  
**Dataset:** [GTZAN Music Genre Dataset](https://www.kaggle.com/datasets/andradaolteanu/gtzan-dataset-music-genre-classification)

---

## Overview

This project builds a content-based music recommendation system. Given a song, the system finds the 5 most acoustically similar tracks from a dataset of 999 audio files across 10 genres — without relying on user history, play counts, or metadata. Pure audio similarity.

The core idea: represent each song as a vector of audio features, then measure similarity between vectors using cosine similarity. Songs that are close in feature space sound similar.

---

## How It Works

```
Audio file (.wav)
      ↓
Feature Extraction (librosa) → 50-dimensional vector
      ↓
StandardScaler normalization
      ↓
Cosine Similarity against all 999 songs
      ↓
Top 5 most similar songs returned
```

---

## Audio Features

Each song is described by 50 numbers capturing its timbre, harmony, rhythm and spectral characteristics:

| Feature | What it captures | Dimensions |
|---|---|---|
| MFCCs (mean + std) | Timbral texture | 26 |
| Chroma | Harmonic content / notes played | 12 |
| Spectral Contrast | Peaks vs valleys in spectrum | 7 |
| Tempo | BPM | 1 |
| Zero Crossing Rate | Percussiveness | 1 |
| Spectral Rolloff | Frequency energy distribution | 1 |
| Spectral Centroid | Brightness | 1 |
| Spectral Bandwidth | Spectral width | 1 |

---

## Results & Analysis

| Query Song | Top Recommendation | Same Genre? | Max Similarity |
|---|---|---|---|
| blues.00000.wav | rock.00026.wav |  rock | 0.65 |
| classical.00000.wav | classical.00001.wav |  classical | 0.85 |
| metal.00000.wav | metal.00004.wav |  metal | 0.79 |
| hiphop.00000.wav | disco.00066.wav |  disco | 0.83 |
| jazz.00000.wav | jazz.00001.wav |  jazz | 0.80 |

### Key observations

**Classical** performs best (similarity ~0.85, 100% correct genre) — classical music is acoustically very distinct from other genres, making it easy to separate in feature space.

**Blues → Rock**: the system consistently recommends rock for blues tracks. This is not a failure — blues and rock share chord progressions, instrumentation, and rhythmic structure. Rock literally evolved from blues. The system is revealing a real musical relationship that genre labels hide.

**Hiphop → Disco**: the top recommendation for hiphop is a disco track. Both genres share strong beats, prominent basslines, and high energy — a connection that is historically and acoustically grounded.

**Jazz → Classical**: jazz recommendations include classical tracks. Both are acoustic, harmonically complex, and low in distortion — the feature vectors pick up on this structural similarity.

**Metal → Hiphop**: metal occasionally recommends hiphop, likely due to shared high energy and aggressive rhythmic patterns.

### What this tells us

Genre labels are a poor proxy for acoustic similarity. The system reveals cross-genre relationships that are musically meaningful.
---

## Installation

```bash
pip install librosa scikit-learn numpy pandas matplotlib seaborn
```

---

## Usage

```bash
python playlist_recommender.py
```

To recommend songs similar to a specific track, call:
```python
results, genres_rec, files_rec = recommend("jazz.00000.wav")
```

---

## Project Structure

```
playlist_recommender/
│
├── playlist_recommender.py        # Main pipeline
├── recommendations_blues.png      # Bar chart - blues recommendations
├── recommendations_classical.png  # Bar chart - classical recommendations
├── recommendations_metal.png      # Bar chart - metal recommendations
└── README.md
```

---

## Reflections

The most interesting finding here is not the accuracy;it's the mistakes. When the system recommends rock for a blues track, or disco for hiphop, it's not wrong. Blues and rock share the same chord progressions and electric guitar timbre.
Hiphop literally came out of disco culture. The system is picking up on real acoustic connections that genre labels were never designed to capture.
Genre is a commercial and cultural construct. It tells you where a record store would shelve an album, not what it sounds like.
A content-based system that ignores genre labels and listens to the actual audio sometimes understands musical similarity better than the labels do - and sometimes reveals relationships that are historically and culturally meaningful.

The real limitation is not the algorithm. It's the data. Every song in this dataset comes from the same cultural tradition. There's no Coupé-Décalé, no Afrobeat, no Mbalax. A system trained only on Western music can only understand similarity within that world. That's not a technical problem; it's a question of whose music gets represented, and whose doesn't.

 