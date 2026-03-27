import os
import numpy as np
import librosa
import pandas as pd
from sklearn.preprocessing import StandardScaler

from sklearn.metrics.pairwise import cosine_similarity

import matplotlib.pyplot as plt
import seaborn as sns
import warnings
warnings.filterwarnings('ignore')

def extract_features(file_path): 
    #Extract features from a single audio file

    #charge audio file y- signal audio sample (resolution)- sr-sample rate (frequence- ftableau de nombre)
    y, sr = librosa.load(file_path, duration= 30 )
    
    #feature 1 - timbre 
    mfccs= librosa.feature.mfcc (y=y, sr=sr, n_mfcc=13)
 
    #calculate mean to have 1 number instead of matrix
    mfccs_mean = np.mean(mfccs, axis=1).flatten()
    mfccs_std = np.std(mfccs, axis=1).flatten()

    #feature 2- harmony using chroma 
    chroma= librosa.feature.chroma_stft(y=y, sr=sr)
    chroma_mean = np.mean(chroma, axis=1).flatten()

    #feature 3- tempo/ rythm 
    tempo, _ = librosa.beat.beat_track(y=y, sr=sr)
    #no need for mean calcualtion because tempo is already a number 
    #print(tempo.shape)

    #special contrast
    contrast = librosa.feature.spectral_contrast(y=y, sr=sr) 
    contrast_mean = np.mean(contrast, axis=1).flatten() 
    #print(contrast_mean.shape)
    # spectral centroid
    centroid = librosa.feature.spectral_centroid(y=y, sr=sr)
    centroid_mean = np.mean(centroid)

    # spectral bandwidth
    bandwidth = librosa.feature.spectral_bandwidth(y=y, sr=sr)
    bandwidth_mean = np.mean(bandwidth)

    # zero crossing rate
    zcr = librosa.feature.zero_crossing_rate(y)
    zcr_mean = np.mean(zcr)

    # Spectral rolloff 
    rolloff = librosa.feature.spectral_rolloff(y=y, sr=sr)
    rolloff_mean = np.mean(librosa.feature.spectral_rolloff(y=y, sr=sr))

 
    #regroup everything in one veactor to have all of it in one sequence de chiffre (use concatenate function)
    feature_vector = np.concatenate([
        mfccs_mean,   # 13 chiffres - timbre
        mfccs_std,    # 13 chiffres -variation du timbre
        chroma_mean,  # 12 chiffres -harmonie
        contrast_mean, # 7 chiffres - contraste spectral
        np.array([float(tempo[0])]),       # 1 chiffre  - rythme
        np.array([zcr_mean]),     # 1 chiffre - rythme
        np.array([rolloff_mean]), # 1 chiffre - spectral rolloff

        np.array([centroid_mean]), # 1 chiffre - centroid spectral
        np.array([bandwidth_mean]), # 1 chiffre - bandwidth spectral  
        
       
    ])
    return feature_vector


print("Extracting features... (this takes ~5-10 min)")
#call extract_featurs on all the files of dataset
genres= os.listdir(r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original")
features= []
label=[]
filenames=[]
for g  in genres:
    path2= os.path.join(r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original", g)
    audio_list= os.listdir(path2)
    for a in audio_list:
        path= os.path.join(r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original", g, a)
        if a.endswith('.wav'):
            try:
                features.append(extract_features(path))
                label.append(g)
                filenames.append(a)

            except Exception as e:
                print(f"Skipped {a}: {e}")
                
print(f"\nDone! files processed: {len(features)}")


# PREPROCESSING  

# 1. convertir en arrays
X = np.array(features)
y = np.array(label)
filenames = np.array(filenames)

# supprimer les lignes avec NaN
mask = ~np.isnan(X).any(axis=1)
X = X[mask]
y = y[mask]
filenames = filenames[mask]
print(f"Après nettoyage: {len(X)} fichiers valides")


# w. scaling
scaler = StandardScaler()
X_scaled= scaler.fit_transform(X) 

def recommend(filename, n=5): 
    print(f"Recommending songs similar to {filename}...")
    print(f"In progress..")
    index = np.where(filenames == filename)[0][0]
    feature_vector_target=X_scaled[index]
    cosine_tab=[]
    ind_cosine_tab=[]
    for i in range (len(X_scaled)):
        sim = cosine_similarity([feature_vector_target], [X_scaled[i]])[0][0]
        cosine_tab.append(sim)
    sorted_list = sorted(enumerate(cosine_tab), key=lambda x: x[1], reverse=True)
    top5 = sorted_list[1:6]
    top5_genre=[]
    top5_file=[]
    for i in range (5):
        top5_genre.append(y[top5[i][0]])
        top5_file.append(filenames[top5[i][0]])


    return top5, top5_genre, top5_file


# Test1 - blues (genre avec des rythmes syncopés et des progressions d'accords spécifiques)
results, genres_rec, files_rec = recommend("blues.00000.wav")

print("\nRecommendations for blues.00000.wav:")
for i in range(5):
    print(f"{i+1}. {files_rec[i]} | genre: {genres_rec[i]} | similarity: {results[i][1]:.4f}")
            
#test 2 - classical (genre avec des structures complexes et une grande variété d'instruments)
results, genres_rec, files_rec = recommend("classical.00000.wav")
print("\nRecommendations for classical.00000.wav:")
for i in range(5):
    print(f"{i+1}. {files_rec[i]} | genre: {genres_rec[i]} | similarity: {results[i][1]:.4f}")

# Test 3 - metal (genre très distinct)
results, genres_rec, files_rec = recommend("metal.00000.wav")
print("\nRecommendations for metal.00000.wav:")
for i in range(5):
    print(f"{i+1}. {files_rec[i]} | genre: {genres_rec[i]} | similarity: {results[i][1]:.4f}")

# Test 4 - hiphop (rythme fort, est-ce qu'il trouve du reggae?)
results, genres_rec, files_rec = recommend("hiphop.00000.wav")
print("\nRecommendations for hiphop.00000.wav:")
for i in range(5):
    print(f"{i+1}. {files_rec[i]} | genre: {genres_rec[i]} | similarity: {results[i][1]:.4f}")

# Test 5 - jazz (genre avec beaucoup d'improvisation)
results, genres_rec, files_rec = recommend("jazz.00000.wav")
print("\nRecommendations for jazz.00000.wav:")
for i in range(5):
    print(f"{i+1}. {files_rec[i]} | genre: {genres_rec[i]} | similarity: {results[i][1]:.4f}")


#  VISUALISATION 1: Bar chart pour une chanson donnée 
def plot_recommendations(filename):
    results, genres_rec, files_rec = recommend(filename)
    
    labels = [f"{files_rec[i]}\n({genres_rec[i]})" for i in range(5)]
    similarities = [results[i][1] for i in range(5)]
    colors = ['#2ecc71' if genres_rec[i] == filename.split('.')[0] else '#e74c3c' for i in range(5)]
    
    plt.figure(figsize=(10, 5))
    bars = plt.bar(labels, similarities, color=colors)
    plt.title(f'Top 5 recommendations for {filename}', fontsize=14, fontweight='bold')
    plt.ylabel('Cosine Similarity')
    plt.ylim(0, 1)
    plt.axhline(y=0.7, color='gray', linestyle='--', alpha=0.5, label='Similarity threshold 0.7')
    plt.legend()
    plt.tight_layout()
    plt.savefig(f'recommendations_{filename.split(".")[0]}.png', dpi=150)
    plt.show()

#  VISUALISATION 2: Heatmap similarité entre genres 
def plot_genre_heatmap():
    genres_list = sorted(set(y))
    n = len(genres_list)
    similarity_matrix = np.zeros((n, n))
    
    for i, g1 in enumerate(genres_list):
        for j, g2 in enumerate(genres_list):
            # moyenne des features de chaque genre
            vec1 = np.mean(X_scaled[y == g1], axis=0)
            vec2 = np.mean(X_scaled[y == g2], axis=0)
            similarity_matrix[i][j] = cosine_similarity([vec1], [vec2])[0][0]
    
    plt.figure(figsize=(10, 8))
    sns.heatmap(similarity_matrix, annot=True, fmt='.2f', cmap='Blues',
                xticklabels=genres_list, yticklabels=genres_list)
    plt.title('Average Genre Similarity Matrix', fontsize=14, fontweight='bold')
    plt.tight_layout()
    plt.savefig('genre_similarity_heatmap.png', dpi=150)
    plt.show()

# ── TEST ──
plot_recommendations("blues.00000.wav")
plot_recommendations("classical.00000.wav")
plot_recommendations("metal.00000.wav")
plot_recommendations("hiphop.00000.wav")
plot_recommendations("jazz.00000.wav")
plot_genre_heatmap()