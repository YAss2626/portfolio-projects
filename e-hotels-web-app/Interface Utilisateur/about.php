<!DOCTYPE html>
<html lang="fr">
<?php
session_start();
?>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>À propos de E-Hotel</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: Montserrat, sans-serif;
            font-size : 15px;
            background-color : white;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            color: #000;
        }
        .container {
            max-width: 1000px;
            padding: 20px;
        }
        .about-section {
            padding: 50px;
            text-align: left;
            background-color: rgba(255, 255, 255, 0.7); 
            border-radius: 10px;
            display: flex;
            align-items: center; 
            width: 1700px;
        }
        .about-text {
            margin-right: 200px;
            line-height: 1.6;
            flex: 1; 
        }
        .about-image {
            flex: 1; 
            height: auto;
            max-width: 100%; 
            border-radius: 10px; 
        }
        .logo img {
            width: 100px;
            height: auto;
        }
        header {
        background-image: url('Pictures/header.jpg');
        background-size: cover; 
        background-position: center;
        padding: 40px;
        text-align: center;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 20px;
        border: 1px solid #ccc;
        }

        footer {
            background-color: rgba(255, 255, 255, 0.7); 
            text-align: center;
            padding: 20px;
            width: 100%;
            left: 0;
            bottom: 0;
            position: fixed;
            font-size : 10px;
            border: 1px solid #ccc;
        }
        .header-links {
            margin-left: 20px;
        }
        .header-links a {
            color: white;
            text-decoration: none;
            margin-right: 20px;
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
    <section class="about-section">
        <div class="about-text">
            <h2>À propos de E-Hotel</h2>
            <p>
                Bienvenue sur E-Hotel, votre destination de choix pour des séjours luxueux et inoubliables. Chez E-Hotel, nous nous engageons à offrir à nos clients une expérience de première classe dans nos hôtels et resorts haut de gamme.
            </p>
            <h3>Notre Mission</h3>
            <p>
                Notre mission est de fournir un service exceptionnel et une hospitalité chaleureuse à chaque client qui franchit nos portes. Nous nous efforçons de créer des souvenirs durables en offrant des chambres élégantes, des installations de classe mondiale et un personnel dévoué prêt à répondre à tous vos besoins.
            </p>
            <h3>Nos Services</h3>
            <ul>
                <li><strong>Chambres de Luxe:</strong> Découvrez nos chambres élégantes et confortables, conçues pour vous offrir un séjour relaxant et revitalisant.</li>
                <li><strong>Restaurants Gourmets:</strong> Savourez une cuisine exquise dans nos restaurants primés, où chaque plat est préparé avec soin par nos chefs talentueux.</li>
                <li><strong>Installations de Loisirs:</strong> Profitez de nos piscines scintillantes, de nos spas apaisants et de nos installations de fitness modernes pour une escapade parfaite.</li>
            </ul>
            <h3>Engagements Durables</h3>
            <p>
                Chez E-Hotel, nous sommes engagés envers la durabilité environnementale. Nous adoptons des pratiques respectueuses de l'environnement pour réduire notre empreinte carbone et protéger les ressources naturelles.
            </p>
            <h3>Contactez-Nous</h3>
            <ul>
                <li><strong>Téléphone:</strong> +1 123 456 7890</li>
                <li><strong>Email:</strong> info@ehotel.com</li>
                <li><strong>Adresse:</strong> 123 Rue E-Hotel, Ville, Pays</li>
            </ul>
            <p>
                Pour toute question ou réservation, n'hésitez pas à nous contacter. Notre équipe est là pour vous aider à planifier votre prochaine escapade dans l'un de nos magnifiques établissements.
            </p>
        </div>
        <img src="Pictures/hotelsign.jpg" alt="Hotel Sign" class="about-image">
    </section>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

</body>
</html>
