"""
Out-of-Distribution Test: African Music Genres
-----------------------------------------------
This script tests a pre-trained genre classifier (trained on GTZAN - Western genres only)
on African music samples from Pixabay (Afrobeat, Amapiano, Highlife, Djembe, Afro-trap).

The goal is to demonstrate the cultural blind spot of models trained exclusively
on Western music datasets. A model that performs well on GTZAN has never seen
the rhythmic, timbral, and harmonic patterns common in West African music traditions.

Author: Yacine Dosso
"""

import os
import numpy as np
import librosa
import matplotlib.pyplot as plt
import seaborn as sns
import pickle
from sklearn.preprocessing import LabelEncoder, StandardScaler



#  (mp3 files from Pixabay)
AFRICAN_SAMPLES_DIR = "./african_samples"


AFRICAN_SAMPLES = {
    "djovan-the-feet-know-461544.mp3":                          "Afro Fusion",
    "afrobeat_burnaboy.mp3": "Afrobeat",
    "jumpingbunny-djembe-rumba-cumbia-handpan-296538.mp3":      "Djembe / Percussion",
    "kontraa-unlock-me-amapiano-music-149058.mp3":              "Amapiano",
    "royaltyfreebeats-afrobeat-gitaar-beat-energieke-highlife-afropop-type-beat-mizik-316230.mp3": "Highlife / Afropop",
    "sonican-funky-afrobeat-music-feelgood-fashion-474483.mp3": "Afrobeat",
    "soundsbyamelia-uplifting-african-worldbeat-djembe-shakers-amp-voices-422975.mp3": "African Worldbeat",
    "u_98673jp944-accra-breeze-344789.mp3":                     "Accra / Highlife",
    "u_98673jp944-african-city-lights-470768.mp3":              "African Pop",
    "vjgalaxy-african-music-kilimanjaro-chant-estilo-swahili-02-438823.mp3": "Swahili / Chant",
    "yellowbirdbeats-afro-trap-x-guitar-x-banger-afrobeat-music-clear-a-path-318753.mp3": "Afro-Trap",
}

# GTZAN genres (what the model knows)
GTZAN_GENRES = ['blues', 'classical', 'country', 'disco',
                'hiphop', 'jazz', 'metal', 'pop', 'reggae', 'rock']


# FEATURE EXTRACTION (same as training pipeline)


def extract_features(file_path):
    y, sr = librosa.load(file_path, duration=30)

    mfccs = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=13)
    mfccs_mean = np.mean(mfccs, axis=1).flatten()
    mfccs_std  = np.std(mfccs, axis=1).flatten()

    chroma = librosa.feature.chroma_stft(y=y, sr=sr)
    chroma_mean = np.mean(chroma, axis=1).flatten()

    tempo, _ = librosa.beat.beat_track(y=y, sr=sr)

    contrast = librosa.feature.spectral_contrast(y=y, sr=sr)
    contrast_mean = np.mean(contrast, axis=1).flatten()

    centroid  = librosa.feature.spectral_centroid(y=y, sr=sr)
    centroid_mean = np.mean(centroid)

    bandwidth = librosa.feature.spectral_bandwidth(y=y, sr=sr)
    bandwidth_mean = np.mean(bandwidth)

    zcr = librosa.feature.zero_crossing_rate(y)
    zcr_mean = np.mean(zcr)

    rolloff = librosa.feature.spectral_rolloff(y=y, sr=sr)
    rolloff_mean = np.mean(rolloff)

    feature_vector = np.concatenate([
        mfccs_mean,
        mfccs_std,
        chroma_mean,
        contrast_mean,
        np.array([float(tempo[0])]),
        np.array([zcr_mean]),
        np.array([rolloff_mean]),
        np.array([centroid_mean]),
        np.array([bandwidth_mean]),
    ])
    return feature_vector




#  loading saved model 
 
with open("best_model.pkl", "rb") as f:
     best_model = pickle.load(f)
with open("scaler.pkl", "rb") as f:
     scaler = pickle.load(f)





 
print("OUT-OF-DISTRIBUTION TEST: African Music Samples")
print("Model trained on: GTZAN (Western genres only)")
 

results = []

for filename, true_label in AFRICAN_SAMPLES.items():
    filepath = os.path.join(AFRICAN_SAMPLES_DIR, filename)
    if not os.path.exists(filepath):
        print(f"[MISSING] {filename}")
        continue
    try:
        features = extract_features(filepath)
        features_scaled = scaler.transform([features])
        predicted_genre = GTZAN_GENRES[best_model.predict(features_scaled)[0]]

        # Get confidence scores if model supports it
        if hasattr(best_model, "predict_proba"):
            proba = best_model.predict_proba(features_scaled)[0]
            confidence = max(proba) * 100
        elif hasattr(best_model, "decision_function"):
            scores = best_model.decision_function(features_scaled)[0]
            confidence = None  # SVM without proba
        else:
            confidence = None

        results.append({
            "file": true_label,
            "predicted": predicted_genre,
            "confidence": confidence
        })

        conf_str = f"(confidence: {confidence:.1f}%)" if confidence else ""
        print(f"  [{true_label}]")
        print(f"    Predicted as: {predicted_genre.upper()} {conf_str}\n")
    except Exception as e:
        print(f"  [ERROR] {filename}: {e}\n")


# VISUALIZATION: What did the model predict?

if results:
    labels       = [r["file"] for r in results]
    predictions  = [r["predicted"] for r in results]

    # Count how many times each GTZAN genre was predicted
    from collections import Counter
    pred_counts = Counter(predictions)

    fig, axes = plt.subplots(1, 2, figsize=(14, 6))

    # Plot 1: prediction distribution
    axes[0].bar(pred_counts.keys(), pred_counts.values(), color='steelblue', edgecolor='black')
    axes[0].set_title("GTZAN Genres Predicted for African Music Samples", fontsize=12)
    axes[0].set_xlabel("Predicted Genre (GTZAN)")
    axes[0].set_ylabel("Number of African Samples")
    axes[0].tick_params(axis='x', rotation=45)

    # Plot 2: per-sample prediction table
    axes[1].axis('off')
    table_data = [[r["file"], r["predicted"]] for r in results]
    table = axes[1].table(
        cellText=table_data,
        colLabels=["African Sample", "Predicted As (GTZAN)"],
        cellLoc='left',
        loc='center'
    )
    table.auto_set_font_size(False)
    table.set_fontsize(9)
    table.scale(1.2, 1.5)
    axes[1].set_title("Per-Sample Predictions", fontsize=12, pad=20)

    plt.suptitle(
        "Out-of-Distribution Test: A model trained on Western music\nmisclassifies African genres it has never seen",
        fontsize=13, fontweight='bold', y=1.02
    )
    plt.tight_layout()
    plt.savefig("ood_african_test.png", dpi=150, bbox_inches='tight')
    plt.show()
    print("\nVisualization saved as ood_african_test.png")

    # Summary
    print("SUMMARY")
    print("="*60)
    print(f"  Samples tested : {len(results)}")
    print(f"  Genres in model: {', '.join(GTZAN_GENRES)}")
    print(f"  African genres tested: Afrobeat, Amapiano, Highlife,")
    print(f"                         Djembe, Afro-Trap, Swahili Chant")
    print(f"\n  The model forced every African sample into a Western")
    print(f"  genre category, demonstrating that GTZAN-trained models")
    print(f"  have no framework to recognize non-Western musical traditions.")
  