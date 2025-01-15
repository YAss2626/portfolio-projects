<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recherche de Chambres</title>
    <style>
        body {
            font-family: Montserrat;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        header {
            background-color: rgba(255, 255, 255, 0.5); 
            color: #000;
            padding: 20px;
            text-align: center;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header-links a {
            font-size : 20px;
            color: #333;
            text-decoration: none;
            margin-right: 20px;
            font-weight: bold;
        }
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
            text-align: left;
        }
        .input-group {
            margin-bottom: 20px;
        }
        .input-group label {
            display: inline-block;
            width: 150px;
            font-weight: bold;
        }
        .input-group input[type="text"],
        .input-group input[type="number"],
        .input-group select {
            width: calc(50% - 8px);
            padding: 8px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-right: 16px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }
        .input-group input[type="submit"] {
            width: 100px;
            background-color: #ff5a5f;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            box-sizing: border-box;
        }
        .input-group input[type="submit"]:hover {
            background-color: grey;
        }
        .card-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
           
        }
        .card {
            width: calc(33.33% - 10px);
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            transition: box-shadow 0.3s;
            margin-bottom: 20px;
            overflow: hidden;
            text-decoration: none; 
            color: inherit; 
        }
        .card:hover {
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
        }
        .card-content {
            padding: 20px;
        }
        .card-content p {
            margin: 0;
            padding: 5px 0;
        }
        .card img {
            width: 100%;
            height: auto;
            border-radius: 5px 5px 0 0;
        }
        footer {
            background-color: rgba(255, 255, 255, 0.5); 
            text-align: center;
            padding: 20px;
            width: 100%;
            left: 0;
            bottom: 0;
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
    <form action="#" method="GET">
        <div class="input-group">
            <label for="hotel_chain">Chaîne hôtelière:</label>
            <input type="text" id="hotel_chain" name="hotel_chain">
        </div>
        <div class="input-group">
            <label for="hotel_name">Nom de l'hôtel:</label>
            <input type="text" id="hotel_name" name="hotel_name">
        </div>
        <div class="input-group">
            <label for="capacity">Capacité:</label>
            <input type="number" id="capacity" name="capacity" min="1">
        </div>
        <div class="input-group">
            <label for="price">Prix (max):</label>
            <input type="number" id="price" name="price" min="0">
        </div>
        <div class="input-group">
            <input type="submit" value="Rechercher">
        </div>
    </form>

    
    <?php
    
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

       
        $query = "SELECT chambre.room_num, chambre.price, chambre.capacity, chambre.view, hotel.nom_hotel, hotel_chain.hotelchain_name 
                  FROM chambre 
                  JOIN hotel ON chambre.hotel_id = hotel.ID 
                  JOIN hotel_chain ON hotel.hotel_chain_id = hotel_chain.id 
                  WHERE 1=1";

        if(isset($_GET['capacity']) && !empty($_GET['capacity'])) {
            $capacity = $_GET['capacity'];
            $query .= " AND chambre.capacity >= $capacity";
        }

        if(isset($_GET['hotel_chain']) && !empty($_GET['hotel_chain'])) {
            $hotel_chain = $_GET['hotel_chain'];
            $query .= " AND hotel_chain.hotelchain_name ILIKE '%$hotel_chain%'";
        }

        if(isset($_GET['hotel_name']) && !empty($_GET['hotel_name'])) {
            $hotel_name = $_GET['hotel_name'];
            $query .= " AND hotel.nom_hotel ILIKE '%$hotel_name%'";
        }

        if(isset($_GET['price']) && !empty($_GET['price'])) {
            $price = $_GET['price'];
            $query .= " AND chambre.price <= $price";
        }

        $stmt = $dbh->query($query);
        $rooms = $stmt->fetchAll();

        if($stmt->rowCount() > 0) {
            echo "<div class='card-container'>";
            foreach ($rooms as $room) {
                echo "<div class='card'>";
                echo "<a href='room_details.php?room_num={$room['room_num']}' style='text-decoration: none; color: inherit;'>"; // Link to room details page
                echo "<img src='" . $viewImages[$room['view']] . "' alt='" . ucfirst($room['view']) . " View'>";
                echo "<div class='card-content'>";
                echo "<p><strong>Chaîne Hôtelière:</strong> " . $room['hotelchain_name'] . "</p>";
                echo "<p><strong>Nom de l'Hôtel:</strong> " . $room['nom_hotel'] . "</p>";
                echo "<p><strong>Numéro de Chambre:</strong> " . $room['room_num'] . "</p>";
                echo "<p><strong>Capacité:</strong> " . $room['capacity'] . "</p>";
                echo "<p><strong>Vue:</strong> " . $room['view'] . "</p>";
                echo "<p><strong>Prix:</strong> " . $room['price'] . "</p>";
                echo "</div>";
                echo "</a>";
                echo "</div>";
            }
            echo "</div>";
        } else {
            echo "<p>Aucune chambre disponible pour les critères spécifiés.</p>";
        }


        $dbh = null;
    } catch (PDOException $e) {
        echo "<p>Erreur de connexion à la base de données: " . $e->getMessage() . "</p>";
    }
    ?>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

</body>
</html>
