<?php
include ("connection.php");
$otp = $_POST['otp'];
$password = $_POST['password'];

$q = "update user_reg set password = '$password' where otp ='$otp'";
$result = mysqli_query($con,$q) or die(mysqli_errror($con));
if($result)
{
    $response['status'] ="1";
    $response['message'] = "Password changed successfully";
}
else
{
    $response['status'] ="0";
    $response['message'] = "Failed";
}
echo json_encode($response);
?>