<?php

$host = "127.0.0.1";
$username = "root";
$password = "";   
$dbname =  "usertest";

// Connect to server
$connect=mysqli_connect($host, $username, $password) or die ("Sorry, unable to connect database server");

$dbselect=mysqli_select_db($connect,$dbname) or die ("Sorry, unable to connect database");
					
$Username 		= $_POST["Username"];
$Password  	= $_POST["Password"];

$user_check_query = "SELECT * FROM users WHERE username='$username' LIMIT 1";
$result = mysqli_query($connect, $user_check_query);
$user = mysqli_fetch_assoc($result);




if ($user) { // if user exists
    if ($user['username'] === $Username) {
      array_push($errors, "Username already exists");
    }
}

if (count($errors) == 0) {
    $Password = md5($Password);//encrypt the password before saving in the database

    $query = "insert into users (id, username, password)" .
       			   " values (NULL, '$Username', '$Password')";


$result = mysqli_query($connect,$query);

    $_SESSION['username'] = $username;
    $_SESSION['success'] = "You are now logged in";
    header('location: index.php');
}

mysqli_close($connect);
?>



