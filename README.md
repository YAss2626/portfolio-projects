Author: Yacine Dosso
# Portfolio Projects

Collection of my academic and personal projects in computer science, showcasing my skills in programming, algorithms, web development, machine learning, and interactive technologies.

---

## 🎵 Music Information Retrieval

**Music Genre Classifier**  
Stack: Python, librosa, scikit-learn  
A machine learning pipeline classifying music genres using audio features (MFCCs, chroma, spectral contrast, tempo). Trained and compared SVM and Neural Network models with GridSearch tuning. Includes an out-of-distribution test on African music samples (Afrobeat, Amapiano, Highlife) to expose the cultural blind spots of models trained exclusively on Western data.

**Content-Based Playlist Recommender**  
Stack: Python, librosa, scikit-learn, openl3  
A recommendation system finding acoustically similar songs using cosine similarity. Compares two approaches: handcrafted audio features (50-dim) vs. learned OpenL3 embeddings (512-dim). Results show embeddings significantly outperform handcrafted features on acoustically ambiguous genres like blues  and rock, while both struggle equally on jazz vs. classical, a distinction  that may be more cultural than acoustic.

**Neural Information Retrieval System**  
Stack: Python, NLTK, sentence-transformers, TensorFlow Hub  
Full IR pipeline on the SciFact scientific corpus: TF-IDF baseline + neural reranking with MPNet (MAP: 0.6327, +19.6% over baseline) and USE (MAP: 0.3426). The USE vs. MPNet gap reveals that domain matters more than architecture, a finding that maps directly onto audio retrieval and motivates the move from handcrafted MIR features to learned audio embeddings.

---

## 🕹 Game & Interactive Projects

### Python Card Games x2
Two implementations of popular card games (Le Pouilleux / XOXO) showcasing object-oriented programming, game logic, and GUI design.

### Bohanza Card Game (C++)
Efficient C++ implementation of the Bohanza card game, focusing on data structures, dynamic memory management, and game mechanics.

---

## 🌐 Web & Software Projects

### e-Hotels Website
**Stack:** PHP & SQL  
A full-featured hotel booking web application demonstrating backend development, database management, and API integration.

### KNN in Java
Optimized k-Nearest Neighbors implementation for classification tasks, demonstrating object-oriented principles and efficient data analysis.
