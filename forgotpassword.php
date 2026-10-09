<?php
include("connection.php");

$phone = $_POST['phone'];
$q = "SELECT * FROM user_reg WHERE phone = '$phone'";
$result = mysqli_query($con, $q) or die(mysqli_error($con));

if (mysqli_num_rows($result) > 0) {
    $otp = $_POST['otp']; // Generate 4-digit OTP
    $update_query = "UPDATE user_reg SET otp = '$otp' WHERE phone = '$phone'";
    $update_result = mysqli_query($con, $update_query) or die(mysqli_error($con));

    if ($update_result) {
        $response['status'] = "1";
        $response['message'] = "OTP generated and data fetched";
    } else {
        $response['status'] = "0";
        $response['message'] = "Failed to update OTP";
    }
} else {
    $response['status'] = "0";
    $response['message'] = "Entered phone number is not registered";
}

echo json_encode($response);
?>
