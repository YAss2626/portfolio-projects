<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails de la Chambre</title>
    <style>
        body {
            font-family: Montserrat, Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
            text-align: left;
            position: relative;
            z-index: 1;
        }
        .card-content {
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            margin-bottom: 20px;
        }
        .card-content p {
            margin: 0;
            padding: 5px 0;
        }
        .card-title {
            font-weight: bold;
            font-size: 34px;
            margin-bottom: 10px;
        }
        .reservation-form {
            margin-top: 20px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
            box-shadow: 0 2px 4px 0 rgba(0,0,0,0.1);
        }
        .reservation-form h2 {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .reservation-form label {
            font-weight: bold;
        }
        .reservation-form input[type="datetime-local"] {
            margin-bottom: 10px;
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
 
        .room-image {
            width: 100%;
            height: auto;
            border-radius: 5px;
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
    <?php
    
    session_start();

    
    if(isset($_SESSION['client_id'])) {
        
        $client_id = $_SESSION['client_id'];
    } else {
        
        $client_id = "N/A";
    }

    $viewImages = [
        'City View' => 'Pictures/CityView.jpg',
        'Sea View' => 'Pictures/Seaview.jpg',
        'Garden View' => 'Pictures/GardenView.jpeg'
    ];


    $host = 'localhost';
    $port = '5432';
    $dbname = 'Hotel';
    $user = 'postgres';
    $password = 'abc123';

    $dsn = "pgsql:host=$host;port=$port;dbname=$dbname;user=$user;password=$password";

    try {
        $dbh = new PDO($dsn);


        if(isset($_GET['room_num'])) {
            $room_num = $_GET['room_num'];
            $stmt = $dbh->prepare("SELECT chambre.*, hotel_chain.hotelchain_name, hotel.nom_hotel 
                                  FROM chambre 
                                  JOIN hotel ON chambre.hotel_id = hotel.id 
                                  JOIN hotel_chain ON hotel.hotel_chain_id = hotel_chain.id 
                                  WHERE chambre.room_num = ?");
            $stmt->execute([$room_num]);
            $room = $stmt->fetch();

            if($room) {

                echo "<div class='card-content'>";
                echo "<p class='card-title'>Détails de la Chambre</p>"; 
                echo "<p><strong>Chaîne Hôtelière:</strong> " . $room['hotelchain_name'] . "</p>";
                echo "<p><strong>Nom de l'Hôtel:</strong> " . $room['nom_hotel'] . "</p>";
                echo "<p><strong>Numéro de Chambre:</strong> " . $room['room_num'] . "</p>";
                echo "<p><strong>Capacité:</strong> " . $room['capacity'] . "</p>";
                echo "<p><strong>Type de Chambre:</strong> " . $room['room_type'] . "</p>";
                echo "<p><strong>Vue:</strong> " . $room['view'] . "</p>";
                echo "<p><strong>Commodité:</strong> " . $room['commodity'] . "</p>";
                echo "<p><strong>Problèmes:</strong> " . $room['problems'] . "</p>";
                echo "<p><strong>Prix:</strong> " . $room['price'] . "</p>";

                echo "<img src='" . $viewImages[$room['view']] . "' alt='" . ucfirst($room['view']) . " View' class='room-image'>";
                echo "</div>";
            } else {
                echo "<p>Aucune information disponible pour cette chambre.</p>";
            }
        } elseif ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['room_num'])) {

            $room_num = $_POST['room_num'];
        } else {
            echo "<p>Aucun numéro de chambre spécifié.</p>";
        }


        $dbh = null;
    } catch (PDOException $e) {
        echo "<p>Erreur de connexion à la base de données: " . $e->getMessage() . "</p>";
    }
    ?>


    <div class="reservation-form">
        <h2>Réserver cette chambre</h2>
        <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="POST">
            <input type="hidden" name="room_num" value="<?php echo $room['room_num']; ?>">
            <input type="hidden" name="client_id" value="<?php echo $client_id; ?>">
            <label for="arriving_date">Date d'arrivée :</label>
            <input type="datetime-local" id="arriving_date" name="arriving_date" required><br><br>
            <label for="departing_date">Date de départ :</label>
            <input type="datetime-local" id="departing_date" name="departing_date" required><br><br>
            <button type="submit">Réserver</button>
        </form>
        <?php
 
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            $arriving_date = $_POST['arriving_date'];
            $departing_date = $_POST['departing_date'];

            try {

                $dbh = new PDO($dsn);


                $room_num = $_POST['room_num'];
                $stmt = $dbh->prepare("SELECT * FROM chambre WHERE room_num = ?");
                $stmt->execute([$room_num]);
                $room = $stmt->fetch();


                $stmt = $dbh->prepare("SELECT * FROM reservations WHERE room_id = ? AND (? < departing_date AND ? > arriving_date)");
                $stmt->execute([$room['id'], $arriving_date, $departing_date]);
                $reserved = $stmt->fetch();

                if ($reserved) {
                    echo "<script>alert('La chambre est déjà réservée pour les dates sélectionnées.'); window.history.back();</script>";
                } else {

                    $stmt = $dbh->prepare("INSERT INTO reservations (room_id, client_id, arriving_date, departing_date) VALUES (?, ?, ?, ?)");
                    $stmt->execute([$room['id'], $client_id, $arriving_date, $departing_date]);
                    

                    header("Location: profile.php?client_id=" . urlencode($client_id));
                    exit();
                }
            } catch (PDOException $e) {
                echo "<p>Erreur lors de la vérification de la disponibilité de la chambre : " . $e->getMessage() . "</p>";
            }
        }
        ?>
    </div>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

</body>
</html>
