import os
import numpy as np
import librosa
import pandas as pd
from sklearn.model_selection import GridSearchCV, train_test_split
from sklearn.svm import SVC
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.metrics import classification_report, confusion_matrix, accuracy_score
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

    #add more features if needed

    return feature_vector
 
print("Extracting features... (this takes ~5-10 min)")
#call extract_featurs on all the files of dataset
genres= os.listdir(r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original")
features= []
label=[]
for g  in genres:
    path2= os.path.join(r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original", g)
    audio_list= os.listdir(path2)
    for a in audio_list:
        path= os.path.join(r"C:\Users\yacin\Downloads\archive (2)\Data\genres_original", g, a)
        if a.endswith('.wav'):
            try:
                features.append(extract_features(path))
                label.append(g)
            except Exception as e:
                print(f"Skipped {a}: {e}")
                
print(f"\nDone! files processed: {len(features)}")


# PREPROCESSING  

# 1. convertir en arrays
X = np.array(features)
y = np.array(label)

# 2. encode labels
le = LabelEncoder()
y = le.fit_transform(y)

# 3. split
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.20, random_state=42, stratify=y
)

# 4. scaling
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)


# TRAINING 1st MODEL - SVM WITH GRID SEARCH
print("\nTraining SVM with Grid Search (this takes ~15 min)...")
param_grid_svm = {
    'C': [0.1, 1, 10, 100],
    'gamma': ['scale', 'auto'],
    'kernel': ['rbf', 'linear']
}
grid_svm = GridSearchCV(SVC(), param_grid_svm, cv=5, verbose=1)
grid_svm.fit(X_train, y_train)
print(f"Best SVM params: {grid_svm.best_params_}")
svm_preds = grid_svm.best_estimator_.predict(X_test)
svm_acc = accuracy_score(y_test, svm_preds)
print(f"SVM Accuracy: {svm_acc:.2%}")


# TRAINING 2nd MODEL - NEURAL NET WITH GRID SEARCH
print("\nTraining Neural Network with Grid Search...")
param_grid_mlp = {
    'hidden_layer_sizes': [(256, 128), (128, 64), (512, 256, 128)],
    'max_iter': [500],
    'alpha': [0.0001, 0.001]  # regularization
}
grid_mlp = GridSearchCV(MLPClassifier(random_state=42), param_grid_mlp, cv=5, verbose=1)
grid_mlp.fit(X_train, y_train)
print(f"Best MLP params: {grid_mlp.best_params_}")
mlp_preds = grid_mlp.best_estimator_.predict(X_test)
mlp_acc = accuracy_score(y_test, mlp_preds)
print(f"Neural Net Accuracy: {mlp_acc:.2%}")



# RESULT COMPARSON
best_model = grid_svm.best_estimator_ if svm_acc >= mlp_acc else grid_mlp.best_estimator_
best_preds = svm_preds if svm_acc >= mlp_acc else mlp_preds
best_name = "SVM" if svm_acc >= mlp_acc else "Neural Network"

print(f"\nBest model: {best_name} ({max(svm_acc, mlp_acc):.2%})")
print("\nClassification Report:")
print(classification_report(y_test, best_preds, target_names=le.classes_))

# RESULT VISUALIZATION - CONFUSION MATRIX
cm = confusion_matrix(y_test, best_preds)
plt.figure(figsize=(10, 8))
sns.heatmap(cm, annot=True, fmt='d', cmap='Blues',
            xticklabels=le.classes_, yticklabels=le.classes_)
plt.title(f'Confusion Matrix — {best_name} ({max(svm_acc, mlp_acc):.2%} accuracy)')
plt.ylabel('True Genre')
plt.xlabel('Predicted Genre')
plt.tight_layout()
plt.savefig('confusion_matrix.png', dpi=150)
plt.show()
print("\nConfusion matrix saved as confusion_matrix.png")