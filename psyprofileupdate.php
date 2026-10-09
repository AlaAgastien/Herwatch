<?php
include("connection.php");
$id=$_POST["id"];
$name =$_POST["name"];
$email =$_POST["email"];
$phone =$_POST["phone"];
$work =$_POST["work"];
$specialization =$_POST["specialization"];
$experience=$_POST["experience"];
$password=$_POST["password"];


$query="update  psychologist_reg set name='$name',email='$email',phone='$phone',work='$work',specialization='$specialization',experience='$experience',	password='$password' where id='$id'";
$result=mysqli_query($con,$query);
if($result)
{
$response["status"]="1";
$response["message"]="updation successfull";
}
else
{
    $response["status"]="0";
    $response["message"]="updation faild";
}
echo json_encode($response);
?>