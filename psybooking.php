<?php 
include("connection.php");

$docname =$_POST["docname"];
$docContact =$_POST["doc_contact"];
$patientname =$_POST["patientname"];
$patientnum =$_POST["patientnum"];
$patientage =$_POST["patientage"];
$dateselect =$_POST["dateselect"];
$timeselect =$_POST["timeselect"];



$q ="INSERT INTO booking_doc  (docname,doc_contact,patientname,patientnum,patientage,dateselect,timeselect) VALUES 
('$docname','$docContact','$patientname','$patientnum','$patientage','$dateselect','$timeselect')";

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