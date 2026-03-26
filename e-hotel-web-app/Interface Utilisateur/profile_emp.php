<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Profil </title>
    <style>
        body {
            font-family: Montserrat, sans-serif;
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
            color: #333;
            text-align: center;
            padding: 20px;
            width: 100%;
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
            font-size: 20px;
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
        <a href="login.php">Connexion</a>
        <a href="about.php">À propos</a>
    </div>
</header>

<div class="container">
    <img src="Pictures/profile.png" alt="Icone Profil" class="profile-icon">
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

        session_start();
        if(isset($_SESSION['employee_id'])) {
            $employee_id = $_SESSION['employee_id'];


            $stmt = $dbh->prepare("SELECT * FROM employees WHERE id = ?");
            $stmt->execute([$employee_id]);
            $employee = $stmt->fetch();

            if($employee) {

                echo "<div class='card-content'>";
                echo "<p class='card-title'>Informations de l'Employé</p>";
                echo "<p><strong>Prénom:</strong> " . $employee['first_name'] . "</p>";
                echo "<p><strong>Nom:</strong> " . $employee['last_name'] . "</p>";
                echo "<p><strong>Email:</strong> " . $employee['email'] . "</p>";
                echo "<p><strong>Numéro de Téléphone:</strong> " . $employee['num_tel'] . "</p>";
                echo "<p><strong>Rôle:</strong> " . $employee['role'] . "</p>";
                

                $stmt = $dbh->prepare("SELECT hotel.nom_hotel FROM hotel WHERE id = ?");
                $stmt->execute([$employee['hotel_id']]);
                $hotel = $stmt->fetch();

                if($hotel) {
                    echo "<p><strong>Nom de l'Hôtel:</strong> " . $hotel['nom_hotel'] . "</p>";
                } else {
                    echo "<p><strong>Nom de l'Hôtel:</strong> Non défini</p>";
                }

                echo "</div>";


                $stmt = $dbh->prepare("SELECT c.first_name, c.last_name, r.id AS reservation_id, r.arriving_date, r.departing_date, r.reservation_date 
                                       FROM reservations r 
                                       JOIN client c ON r.client_id = c.id");
                $stmt->execute();
                $reservations = $stmt->fetchAll();

                if($reservations) {
                    echo "<div class='card-content'>";
                    echo "<p class='card-title'>Réservations des Clients</p>";
                    foreach($reservations as $reservation) {
                        echo "<div class='card-content'>";
                        echo "<p><strong>Prénom:</strong> " . $reservation['first_name'] . "</p>";
                        echo "<p><strong>Nom:</strong> " . $reservation['last_name'] . "</p>";
                        echo "<p><strong>ID de Réservation:</strong> " . $reservation['reservation_id'] . "</p>";
                        echo "<p><strong>Date d'arrivée:</strong> " . $reservation['arriving_date'] . "</p>";
                        echo "<p><strong>Date de départ:</strong> " . $reservation['departing_date'] . "</p>";  
                        echo "<p><strong>Date de réservation:</strong> " . $reservation['reservation_date'] . "</p>";

                        echo "<form action='enreg_location.php' method='post'>";
                        echo "<input type='hidden' name='reservation_id' value='" . $reservation['reservation_id'] . "'>";
                        echo "<input type='hidden' name='employee_id' value='" . $employee_id . "'>";
                        echo "<input type='submit' value='Enregistrer Location'>";
                        echo "</form>"; 
                        echo "</div>";
                    }
                    echo "</div>";
                } else {
                    echo "<div class='card-content'><p>Aucune réservation trouvée.</p></div>";
                }
            } else {
                echo "<div class='card-content'><p>Employé non trouvé.</p></div>";
            }
        } else {
            echo "<div class='card-content'><p>Aucun identifiant d'employé spécifié.</p></div>";
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
