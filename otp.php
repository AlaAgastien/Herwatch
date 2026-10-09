<?php 
include("connection.php");

$otp=$_POST["otp"];
$query="SELECT * FROM `user_reg` WHERE otp='$otp'";
$result=mysqli_query($con,$query);
$row=mysqli_fetch_row($result);
if(mysqli_num_rows($result)>0)
{
    $response["status"]="1";
    $response["message"]="Login Successful";

}
else
{
    $response["status"]="0";
    $response["message"]="Login failed";

}
echo json_encode($response);
?>
