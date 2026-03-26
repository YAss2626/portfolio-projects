<!DOCTYPE html>
<html lang="fr">
<?php

session_start();

?>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenue</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: Montserrat, sans-serif;
            font-size : 15px;
            background-image: url('Pictures/Room image.png');
            background-size: cover;
            background-position: center;
            background-attachment: fixed; 
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            color: #000; 
        }
        .container {
            max-width: 1000px;
            padding: 20px;
        }
        .welcome-section {
            padding: 200px;
            text-align: left;
        }
        .welcome-card {
            background-color: rgba(255, 255, 255, 0.7); 
            padding: 20px;
            border-radius: 10px;
        }
        .welcome-message {
            margin-right: 20px;
        }
        .btn-search {
            background-color: #ff5a5f;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            text-decoration: none;
            font-size: 16px;
        }
        .btn-search:hover {
            background-color: #e70007;
        }
        .logo img {
            width: 100px;
            height: auto;
        }
        header {
            background-color: rgba(255, 255, 255, 0.5); 
            padding: 20px;
            text-align: center;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size : 20px;

        }
        footer {
            background-color: rgba(255, 255, 255, 0.5); 
            text-align: center;
            padding: 20px;
            width: 100%;
            left: 0;
            bottom: 0;
            position: fixed;
            font-size : 10px;
        }
        .header-links {
            margin-left: 20px;
        }
        .header-links a {
            color: #333;
            text-decoration: none;
            margin-right: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>

<header>
    <div class="logo">
        <img src="Pictures/logo.png" alt="Ehotel Logo">
    </div>
    <div class="header-links">
        <a href="introduction.php">Accueil</a>
        <?php

        if(isset($_SESSION['client_id'])) {
            $client_id = $_SESSION['client_id'];

            echo "<a href='profile.php?client_id=$client_id'>Profil</a>";
        } else {

            echo "<a href='login.php'>Connexion</a>";
        }
        ?>
        <a href="about.php">À propos</a>
    </div>
</header>


<div class="container">
    <section class="welcome-section">
        <div class="welcome-card">
            <div class="welcome-message">
                <h2>Hôtel de Luxe et Meilleur Resort</h2>
                <p>Nos incroyables hôtels proposent certaines des meilleures chambres. Réservez dès maintenant pour profiter des meilleures offres estivales.</p>
                <?php
                
                if(isset($_SESSION['client_id'])) {
                    $client_id = $_SESSION['client_id'];

                    
                    echo "<a href='Searchroom.php?client_id=$client_id' class='btn-search'>Voir les offres</a>";
                } else {

                    echo "<a href='login.php' class='btn-search'>Connectez-vous pour voir les offres</a>";
                }
                ?>
            </div>
        </div>
    </section>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

</body>
</html>
