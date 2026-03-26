<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrer Location</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            text-align: center;
            font-size: 20px;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        h2 {
            margin-top: 20px;
        }
        .bubble {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            border: 1px solid #ccc;
            max-width: 500px;
            margin: auto;
        }
        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        label {
            font-size: 18px;
            margin-bottom: 10px;
            display: block;
        }
        input[type="number"] {
            width: 300px;
            padding: 10px;
            margin-bottom: 20px;
            font-size: 18px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 12px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            font-size: 18px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
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
        <a href="about.html">À propos</a>
    </div>
</header>

<div class="bubble">
    <h2>Enregistrer Location</h2>
    <form method="post">
        <label for="payment">Paiement (numero de carte) :</label>
        <input type="number" id="payment" name="payment" required><br><br>

        <input type="submit" value="Enregistrer Location">

        <?php
        // Connexion à la base de données
        $host = 'localhost';
        $port = '5432';
        $dbname = 'Hotel';
        $user = 'postgres';
        $password = 'abc123';

        $dsn = "pgsql:host=$host;port=$port;dbname=$dbname;user=$user;password=$password";
        $dbh = new PDO($dsn);


        $reservation_id = $_POST['reservation_id'];

        $employee_id = $_POST['employee_id'];

        if (isset($reservation_id) && isset($employee_id)) {

            $stmt = $dbh->prepare("SELECT r.arriving_date, r.departing_date, r.client_id, r.room_id 
                           FROM reservations r 
                           WHERE r.id = ?");
            $stmt->execute([$reservation_id]);
            $reservation_data = $stmt->fetch();

            if ($reservation_data) {
                $arriving_date = $reservation_data['arriving_date'];
                $departing_date = $reservation_data['departing_date'];
                $client_id = $reservation_data['client_id'];
                $room_id = $reservation_data['room_id'];


                if ($_SERVER["REQUEST_METHOD"] == "POST") {

                    if (isset($_POST['payment'])) {

                        $payment = $_POST['payment'];


                        $stmt = $dbh->prepare("INSERT INTO location (date_arrive, date_depart, paiement, chambre_num, employee_id, client_id) 
                                               VALUES (?, ?, ?, ?, ?, ?)");

                        $stmt->execute([$arriving_date, $departing_date, $payment, $room_id, $employee_id, $client_id]);

                        echo "<p>La location a été enregistrée avec succès.</p>";
                    } else {
                        echo "<p>Veuillez remplir le champ de paiement.</p>";
                    }
                }
            } else {
                echo "<p>La réservation avec l'identifiant donné n'a pas été trouvée.</p>";
            }
        } else {
            echo "<p>Les identifiants de réservation et d'employé ne sont pas spécifiés.</p>";
        }
        ?>
        <input type="hidden" name="reservation_id" value="<?= $reservation_id ?>">
        <input type="hidden" name="employee_id" value="<?= $employee_id ?>">
    </form>
</div>

</body>
</html>
