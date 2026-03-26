<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription client</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            padding: 0;
            
        }
        body {
            font-family: Montserrat;
            font-size : 10px;
            background-image: url('Pictures/reception.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed; 
            
        }
        .container {
            max-width: 1000px;
            padding: 5px;
            
            
        }
        .welcome-section {
            padding: 100px;
            text-align: left;
            width: 600px;
            height: 600px;
        
            
            
        }
        .signup-form {
            background-color: rgba(255, 255, 255, 0.7);
            padding: 40px;
            border-radius: 10px;
            margin-left: 20px; 
            border: 1px solid #ccc;
        }
        .signup-form label {
            font-size: 24px;
            margin-bottom: 10px;
        }
        .signup-form .signup-label {
            font-size: 28px; 
            color: #333; 
            font-weight: bold;
        }
        .signup-form input[type="text"] {
            width: 80%;
            padding: 10px;
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
            font-size : 20px;
            border-bottom: 1px solid #ccc;
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
    <section class="welcome-section">
        <div class="signup-container">
            <form class="signup-form" method="post">
                <label class="signup-label" for="first_name">Inscription</label><br>
                <label for="first_name">Prénom:</label><br>
                <input type="text" id="first_name" name="first_name" required><br>
                
                <label for="last_name">Nom:</label><br>
                <input type="text" id="last_name" name="last_name" required><br>
                
                <label for="address">Addresse:</label><br>
                <input type="text" id="address" name="address"><br>
                
                <label for="nas">Numéro d'identification national (NAS):</label><br>
                <input type="text" id="nas" name="nas" required><br>
                
                <input type="submit" value="Soumettre" name="submit">
            </form>
            <p style="font-size: 20px; margin-top: 20px; color:white;">Êtes-vous un employé? <a href="employee_sign.php"> Inscrivez-vous ici</a></p>
        </div>
    </section>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
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


        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


        $stmt = $conn->prepare("INSERT INTO client (first_name, last_name, address, nas) VALUES (:first_name, :last_name, :address, :nas)");


        $stmt->bindParam(':first_name', $_POST['first_name']);
        $stmt->bindParam(':last_name', $_POST['last_name']);
        $stmt->bindParam(':address', $_POST['address']);
        $stmt->bindParam(':nas', $_POST['nas']);

   
        $stmt->execute();


        header('Location: login.php');
        exit();

        echo "<script>alert('Compte créé avec succès !!');</script>";
    } catch (PDOException $e) {
        echo "<p>Error: " . $e->getMessage() . "</p>";
    } finally {

        $conn = null;
    }
}
?>

</body>
</html>
