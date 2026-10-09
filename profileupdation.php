<?php
include("connection.php");
$id=$_POST["id"];
$name =$_POST["name"];
$email =$_POST["email"];
$phone =$_POST["phone"];
$guardiannumber =$_POST["guardiannumber"];
$password =$_POST["password"];


$query="update  user_reg set name='$name',email='$email',phone='$phone',guardiannumber='$guardiannumber',password='$password' where id='$id'";
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