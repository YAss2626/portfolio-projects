<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription employee</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        body {
            font-family: Montserrat;
            font-size: 20px;
            background-image: url('Pictures/employees.png');
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
            padding-bottom: 40px;
        }
        .signup-section {
            padding: 90px;
            padding-top: 0px;
            padding-bottom: 20px;
            text-align: left;
            width: 600px;
            height:602;
        }
        .signup-form {
            background-color: rgba(255, 255, 255, 0.7);
            padding: 20px;
            border-radius: 10px;
            margin-left: 20px; 
            border: 1px solid #ccc;
        }
        .signup-form label {
            font-size: 15px;
            margin-bottom: 10px;
        }
        .signup-form .signup-label {
            font-size: 30px;
            color: #333;
            font-weight: bold;
        }
        .signup-form input[type="text"] {
            font-family: Montserrat;
            width: 90%;
            padding: 5px;
            margin-bottom: 20px;
            font-size: 18px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .signup-form input[type="submit"] {
            background-color: #ff5a5f;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            text-decoration: none;
            font-size: 18px;
        }
        .signup-form input[type="submit"]:hover {
            background-color: #e70007;
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
            border: 1px solid #ccc;
        }
        footer {
            background-color: rgba(220, 220, 220, 0.7); 
            text-align: center;
            padding: 20px;
            width: 100%;
            left: 0;
            bottom: 0;
            position: fixed;
            font-size : 15px;
            border-top: 1px solid #ccc;
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
    <section class="signup-section">
        <div class="signup-container">
            <form class="signup-form" method="post">
                <label class="signup-label" for="first_name">Employee Sign Up</label><br>
                <label for="first_name">First Name:</label><br>
                <input type="text" id="first_name" name="first_name" required><br>

                <label for="last_name">Last Name:</label><br>
                <input type="text" id="last_name" name="last_name" required><br>

                <label for="email">Email:</label><br>
                <input type="text" id="email" name="email" required><br>

                <label for="tel_num">Telephone Number:</label><br>
                <input type="text" id="tel_num" name="tel_num" required><br>

                <label for="role">Role:</label><br>
                <input type="text" id="role" name="role" required><br>

                <label for="nas">NAS:</label><br>
                <input type="text" id="nas" name="nas" required><br>

                <label for="hotel">Hotel:</label><br>
                <select id="hotel" name="hotel">
                    <?php
                    try {
                        // Database connection
                        $host = 'localhost';
                        $port = '5432';
                        $dbname = 'Hotel';
                        $user = 'postgres';
                        $password = 'abc123';

                        // Establish database connection
                        $dsn = "pgsql:host=$host;port=$port;dbname=$dbname;user=$user;password=$password";
                        $conn = new PDO($dsn);

                        // Set error mode to exception
                        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                        // Fetch hotel data
                        $stmt = $conn->prepare("SELECT id, nom_hotel FROM hotel");
                        $stmt->execute();
                        $hotels = $stmt->fetchAll();

                        // Populate dropdown with hotel data
                        foreach ($hotels as $hotel) {
                            echo "<option value=\"" . $hotel['id'] . "\">" . $hotel['nom_hotel'] . "</option>";
                        }
                    } catch (PDOException $e) {
                        echo "<p>Error: " . $e->getMessage() . "</p>";
                    } finally {
                        // Close connection
                        $conn = null;
                    }
                    ?>
                </select><br>

                <input type="submit" value="Submit" name="submit">
            </form>
            <p style="font-size: 20px; margin-top: 20px; color: white; ">Etes vous un client? <a href="Sign up.php"> Inscrivez-vous ici</a></p>
        </div>
    </section>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    try {

        $host = 'localhost';
        $port = '5432';
        $dbname = 'Hotel';
        $user = 'postgres';
        $password = 'abc123';


        $dsn = "pgsql:host=$host;port=$port;dbname=$dbname;user=$user;password=$password";
        $conn = new PDO($dsn);


        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Prepare SQL statement
        $stmt = $conn->prepare("INSERT INTO employees (first_name, last_name, email, num_tel, role, nas, hotel_id) VALUES (:first_name, :last_name, :email, :tel_num, :role, :nas, :hotel_id)");

        // Bind parameters
        $stmt->bindParam(':first_name', $_POST['first_name']);
        $stmt->bindParam(':last_name', $_POST['last_name']);
        $stmt->bindParam(':email', $_POST['email']);
        $stmt->bindParam(':tel_num', $_POST['tel_num']);
        $stmt->bindParam(':role', $_POST['role']);
        $stmt->bindParam(':nas', $_POST['nas']);
        $stmt->bindParam(':hotel_id', $_POST['hotel']);


        $stmt->execute();


        header('Location: login.php');
        exit();
    } catch (PDOException $e) {
        echo "<p>Error: " . $e->getMessage() . "</p>";
    } finally {

        $conn = null;
    }
}
?>

</body>
</html>
