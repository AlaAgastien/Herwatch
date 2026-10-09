<?php 
include("connection.php");

$name =$_POST["name"];
$email =$_POST["email"];
$phone =$_POST["phone"];
$work =$_POST["work"];
$specialization =$_POST["specialization"];
$experience =$_POST["experience"];
$password =$_POST["password"];


$q ="INSERT INTO psychologist_reg  (name,email,phone,work,specialization,experience,password) VALUES 
('$name','$email','$phone','$work','$specialization','$experience','$password')";

$result=mysqli_query($con,$q);
if($result){
    $response["status"]="1";
    $response["message"]=" Registration successful";
}
else{
    $response["status"]="0";
    $response["message"]="Registration failed";
}
echo json_encode($response);
?>