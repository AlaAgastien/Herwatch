<?php
include("connection.php");


//creating a query

$stmt = $con->prepare( "SELECT id,docname,doc_contact,patientname,patientnum,patientage,dateselect,timeselect FROM  booking_doc ");

//Executting the query

$stmt->execute();

//binding result to the query

$stmt->bind_result($id,$docname,$doc_contact,$patientname,$patientnum,$patientage,$dateselect,$timeselect);

$p=array();

while($stmt->fetch()){
    $temp=array();
    $temp['id']=$id;
    $temp['docname']=$docname;
    $temp['doc_contact']=$doc_contact;
    $temp['patientname']=$patientname;
    $temp['patientnum']=$patientnum;
    $temp['patientage']=$patientage;
    $temp['dateselect']=$dateselect;
    $temp['timeselect']=$timeselect;
  
    array_push($p,$temp);

}
echo json_encode($p);