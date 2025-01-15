<?php
// Start session before any output is sent
session_start();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    try {
        // Database connection parameters
        $host = 'localhost';
        $port = '5432';
        $dbname = 'Hotel';
        $user = 'postgres';
        $password = 'abc123';

        // Establish database connection
        $dsn = "pgsql:host=$host;port=$port;dbname=$dbname;user=$user;password=$password";
        $conn = new PDO($dsn);


        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


        $table_name = '';


        if ($_POST['role'] === 'client') {
            $table_name = 'client';
        } elseif ($_POST['role'] === 'employee') {
            $table_name = 'employees';
        }


        $stmt = $conn->prepare("SELECT * FROM $table_name WHERE first_name = :first_name AND last_name = :last_name AND nas = :nas");


        $stmt->bindParam(':first_name', $_POST['first_name']);
        $stmt->bindParam(':last_name', $_POST['last_name']);
        $stmt->bindParam(':nas', $_POST['nas']);


        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($result) {
            if ($_POST['role'] === 'client') {
                $_SESSION['client_id'] = $result['id'];
                header('Location: introduction.php');
                exit();
            } else {
                $_SESSION['employee_id'] = $result['id'];
                header('Location: profile_emp.php?employee_id=' . $result['id']);
                exit();
            }
        } else {
            echo "<p>Login failed. Please check your credentials.</p>";
        }
    } catch (PDOException $e) {
        echo "<p>Error: " . $e->getMessage() . "</p>";
    } finally {

        $conn = null;
    }
}
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
    <link href='https://fonts.googleapis.com/css?family=Merienda' rel='stylesheet'>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: Montserrat;
            font-size: 20px;
            color: #000;
            background-image: url('Pictures/Water.png');
            background-size: cover;
            background-position: center;
            background-attachment: fixed; 
        }
        .container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 100%;
            padding: 0 100px;
        }
        .welcome-text {
            width: 54%; 
            padding: 60px;
        }
        .welcome-text h1 {
            font-size: 80px;
            color: #000;
            background-color: rgba(255, 255, 252, 0.7);
            border-radius: 9px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            padding: 40px;
        }
        .login-form {
            background-color: rgba(255, 255, 255, 0.8);
            padding: 50px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            border: 1px solid #ccc;
            width: 350px; 
        }
        .login-form label {
            font-size: 18px;
            margin-bottom: 10px;
            display: block;
        }
        .login-form input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            font-size: 18px;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        .login-form input[type="submit"] {
            background-color: #ff5a5f;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 12px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            font-size: 18px;
        }
        .login-form input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .login-form .role-selection {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .signup-link {
            text-align: center;
            margin-top: 20px;
        }
        footer {
            background-color: rgba(255, 255, 255, 0.5);
            text-align: center;
            padding: 20px;
            width: 100%;
            left: 0;
            bottom: 0;
            position: fixed;
            font-size: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="welcome-text">
        <h1>Bienvenue à E-Hotel</h1>
    </div>

    <div class="login-form">
        <form method="post">
            <label for="first_name">Prénom:</label>
            <input type="text" id="first_name" name="first_name" required><br>

            <label for="last_name">Nom:</label>
            <input type="text" id="last_name" name="last_name" required><br>

            <label for="nas">NAS (Numéro d'identification national):</label>
            <input type="text" id="nas" name="nas" required><br>

            <div class="role-selection">
                <label for="role">Sélectionner:</label>
                <select id="role" name="role">
                    <option value="client">Client</option>
                    <option value="employee">Employé</option>
                </select>
            </div>

            <input type="submit" value="Connexion" name="submit">
        </form>


        <div class="signup-link">
            <p>Vous n'avez pas de compte ? <a href="Sign up.php"> Inscrivez-vous!</a></p>
        </div>
    </div>
</div>

<footer>
    <p>&copy; 2024 E-Hotel. Tous droits réservés.</p>
</footer>

</body>
</html>
