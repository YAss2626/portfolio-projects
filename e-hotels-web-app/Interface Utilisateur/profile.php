<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Profils</title>
    <style>
        body {
            font-family: Montserrat,sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            text-align: center;
            font-size: 20px;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .container {
            flex: 1; 
            max-width: 800px;
            padding: 20px;
            text-align: left;
            position: relative;
            z-index: 1;
            padding-left: 80px;
        }
        .card-content {
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            margin-bottom: 20px;
            width: 93%;
        }
        .card-content p {
            margin: 0;
            padding: 5px 0;
        }
        .card-title {
            font-weight: bold;
            font-size: 34px;
            margin-bottom: 10px;
            color: #333;
        }
        footer {
            background-color: rgba(220, 220, 220, 0.7); 
            text-align: center;
            padding: 20px;
            width: 100%;
            left: 0;
            bottom: 0;
            font-size : 15px;
            border-top: 1px solid #ccc;
        }
        .logo img {
            width: 100px;
            height: auto;
        }
        header {
            background-color: rgba(220, 220, 220, 0.7); 
            padding: 20px;
            text-align: center;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size : 20px;
            border-bottom: 1px solid #ccc;
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
    <img src="Pictures/profile.png" alt="Profile Icon" class="profile-icon">
    <?php
    // Database connection
    $host = 'localhost';
    $port = '5432';
    $dbname = 'Hotel';
    $user = 'postgres';
    $password = 'abc123';

    $dsn = "pgsql:host=$host;port=$port;dbname=$dbname;user=$user;password=$password";

    try {
        $dbh = new PDO($dsn);


        if(isset($_GET['client_id'])) {
            $client_id = $_GET['client_id'];


            $stmt = $dbh->prepare("SELECT * FROM client WHERE id = ?");
            $stmt->execute([$client_id]);
            $client = $stmt->fetch();

            if($client) {

                echo "<div class='card-content'>";
                echo "<p class='card-title'>Informations du Client</p>";
                echo "<p><strong>Prénom:</strong> " . $client['first_name'] . "</p>";
                echo "<p><strong>Nom:</strong> " . $client['last_name'] . "</p>";
                echo "<p><strong>Adresse:</strong> " . $client['address'] . "</p>";
                echo "</div>";

                $stmt = $dbh->prepare("SELECT reservations.arriving_date, reservations.departing_date, 
                                            hotel_chain.hotelchain_name, hotel.nom_hotel, chambre.room_type
                                       FROM reservations
                                       JOIN chambre ON reservations.room_id = chambre.id
                                       JOIN hotel ON chambre.hotel_id = hotel.id
                                       JOIN hotel_chain ON hotel.hotel_chain_id = hotel_chain.id
                                       WHERE reservations.client_id = ?");
                $stmt->execute([$client_id]);
                $reservations = $stmt->fetchAll();

                if($reservations) {
                    echo "<div class='card-content'>";
                    echo "<p class='card-title'>Réservations du Client</p>";
                    foreach($reservations as $reservation) {
                        echo "<div class='card-content'>";
                        echo "<p><strong>Chaîne Hôtelière:</strong> " . $reservation['hotelchain_name'] . "</p>";
                        echo "<p><strong>Nom de l'Hôtel:</strong> " . $reservation['nom_hotel'] . "</p>";
                        echo "<p><strong>Type de Chambre:</strong> " . $reservation['room_type'] . "</p>";
                        echo "<p><strong>Date d'arrivée:</strong> " . $reservation['arriving_date'] . "</p>";
                        echo "<p><strong>Date de départ:</strong> " . $reservation['departing_date'] . "</p>";  
                        echo "</div>";
                    }
                    echo "</div>";
                } else {
                    echo "<div class='card-content'><p>Aucune réservation trouvée pour ce client.</p></div>";
                }
            } else {
                echo "<div class='card-content'><p>Client non trouvé.</p></div>";
            }
        } else {
            echo "<div class='card-content'><p>Aucun identifiant de client spécifié.</p></div>";
        }


        $dbh = null;
    } catch (PDOException $e) {
        echo "<div class='card-content'><p>Erreur de connexion à la base de données: " . $e->getMessage() . "</p></div>";
    }
    ?>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

</body>
</html>
