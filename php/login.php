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



$Password = md5($Password);//encrypt the password before saving in the database

$user_check_query = "SELECT * FROM users WHERE username='$Username' AND password='$Password'";

$result = mysqli_query($connect, $user_check_query);
$user = mysqli_fetch_assoc($result);

if ($user) { 
    if ($user['username'] === $Username) {
        if($user['password'] ===$Password){
            print("0");
        }
    }else{
        print("1");
    }
}



mysqli_close($connect);
?>



