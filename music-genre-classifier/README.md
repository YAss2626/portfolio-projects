By Yacine Dosso

# Music Genre Classifier
**Author:** Yacine Dosso  
**Stack:** Python, librosa, scikit-learn  
**Dataset:** [GTZAN Music Genre Dataset](https://www.kaggle.com/datasets/andradaolteanu/gtzan-dataset-music-genre-classification)

---

## Overview

This project builds an audio machine learning pipeline to automatically classify music into genres using low-level audio features. It was built as an introduction to Music Information Retrieval (MIR), exploring how well computational methods can capture the characteristics that define a musical genre.

The project also examines a deeper question: **what happens when the training data doesn't represent the full diversity of the world's music?** GTZAN is composed almost entirely of Western genres, no Afrobeat, no Mbalax, no Coupé-Décalé. A model trained on this data would likely fail on music from traditions it has never seen. This cultural blind spot is a known open problem in MIR research, and one of the motivations behind this project.

---

## Dataset

- **GTZAN** — 1,000 audio files, 30 seconds each, across 10 genres
- Genres: blues, classical, country, disco, hiphop, jazz, metal, pop, reggae, rock
- 100 tracks per genre
- Note: `jazz.00054.wav` is a known corrupted file in the dataset and is skipped automatically

---

## Audio Features Extracted

For each audio file, the following features are extracted using **librosa**:

| Feature | Description | Dimensions |
|---|---|---|
| MFCCs (mean + std) | Timbral texture of the sound | 26 |
| Chroma | Harmonic content / notes played | 12 |
| Spectral Contrast | Difference between peaks and valleys in spectrum | 7 |
| Tempo | BPM — rhythmic speed | 1 |
| Zero Crossing Rate | How often the signal crosses zero (percussiveness) | 1 |
| Spectral Rolloff | Frequency below which 85% of energy falls | 1 |
| Spectral Centroid | "Brightness" of the sound | 1 |
| Spectral Bandwidth | Width of the spectral distribution | 1 |

**Total feature vector per song: 50 dimensions**

Each song is represented as a single fixed-size vector, features that are matrices (like MFCCs) are summarized using their mean and standard deviation across time frames.

---

## Pipeline

```
Audio files (.wav)
      ↓
Feature Extraction (librosa)
      ↓
Preprocessing (LabelEncoder + StandardScaler)
      ↓
Train/Test Split (80/20, stratified)
      ↓
Model Training (SVM + Neural Network)
      ↓
Hyperparameter Tuning (GridSearchCV, 5-fold cross-validation)
      ↓
Evaluation (Accuracy, Classification Report, Confusion Matrix)
```

---

## Models

### SVM (Support Vector Machine)
- Kernel: RBF
- Hyperparameters tuned via GridSearchCV: `C`, `gamma`, `kernel`

### Neural Network (MLPClassifier)
- Architecture: fully connected layers
- Hyperparameters tuned via GridSearchCV: `hidden_layer_sizes`, `alpha`

---

## Results

>  

| Model | Accuracy (before tuning) |
|---|---|
| SVM | 77.00% |
| Neural Network | 73.50% |


| Model | Accuracy (after tuning) |
|---|---|
| SVM | 77.00% |
| Neural Network | 72.50% |

**Best performing genres:** jazz (recall: 1.00), classical, metal (~90%+)  
**Most confused genres:** rock, disco, country - rhythmically and tonally similar
---

## Installation

```bash
pip install librosa scikit-learn numpy pandas matplotlib seaborn
```

---

## Usage

```bash
python genre_classifier.py
```

The script will:
1. Extract features from all audio files (~10 min)
2. Train SVM and Neural Network with Grid Search (~20-30 min)
3. Print accuracy, classification report
4. Save `confusion_matrix.png`

---

## Project Structure

```
music_genre_classifier/
│
├── genre_classifier.py       # Main pipeline
├── confusion_matrix.png      # Output visualization
└── README.md
```

---

## Reflections & Limitations

**On cultural representation:**  
GTZAN contains exclusively Western popular music genres. A model trained on this dataset has no exposure to West African rhythmic traditions (like Afrobeat, Highlife, or Coupé-Décalé), Middle Eastern music, or other non-Western styles. This is not just a data problem, it reflects a broader bias in how MIR benchmarks are constructed.

This connects directly to research by Bello et al. (2015) on rhythm similarity, which showed that computational systems can be technically accurate while remaining culturally blind, unable to generalize beyond the musical traditions represented in their training data.

**On genre as a proxy:**  
Genre labels are inherently fuzzy. Rock and country share rhythmic patterns; disco and pop overlap harmonically. Classification accuracy is a useful metric but doesn't fully capture whether a model truly "understands" musical similarity.

---


## Out-of-Distribution Test: African Music Samples

The cultural limitation described above raises a testable hypothesis: if the model 
has never encountered non-Western music during training, how does it behave when it does?

11 audio samples were sourced from Pixabay (CC0 license), representing African genres 
entirely absent from GTZAN: Afrobeat, Amapiano, Highlife, Afro-Trap, Djembe percussion, 
and Swahili chant. The same feature extraction pipeline and trained SVM were used without 
any retraining.

| African Genre | Predicted As (GTZAN) |
|---|---|
| Amapiano | blues |
| Afrobeat | reggae |
| Highlife / Afropop | country |
| Djembe / Percussion | blues |
| Swahili / Chant | reggae |
| Afro-Trap | reggae |
| African Worldbeat | hiphop |
| Accra / Highlife | jazz |
| African Pop | blues |
| Afro Fusion | hiphop |

The misclassifications are not random, they reveal which acoustic proxies the model 
relies on. Amapiano's deep bass maps to blues; Afrobeat's syncopated rhythm maps to 
reggae; Highlife's melodic guitar maps to country. The model is not recognizing these 
genres — it is finding the closest Western acoustic match in a feature space that was 
never designed to represent them.

This confirms that the feature set itself encodes Western musical assumptions. Building 
systems that generalize across the full diversity of human music requires not just more 
data, but different data — and potentially different features.

Sources: [Pixabay](https://pixabay.com/music/) (CC0 license)

## References

- Tzanetakis & Cook (2002) - original GTZAN paper
- Esparza, Bello & Humphrey (2015) -*From Genre Classification to Rhythm Similarity*
- McFee et al. - librosa: Audio and Music Signal Analysis in Python
