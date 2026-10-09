<?php
include("connection.php");


//creating a query

$stmt = $con->prepare( "SELECT id,name,email,phone,work,specialization,experience,password FROM  psychologist_reg ");

//Executting the query

$stmt->execute();

//binding result to the query

$stmt->bind_result($id,$name,$email,$phone,$work,$specialization,$expeerience,$password);

$p=array();

while($stmt->fetch()){
    $temp=array();
    $temp['id']=$id;
    $temp['name']=$name;
    $temp['email']=$email;
    $temp['phone']=$phone;
    $temp['work']=$work;
    $temp['specialization']=$specialization;
    $temp['experience']=$expeerience;
    $temp['password']=$password;
  
    array_push($p,$temp);

}
echo json_encode($p);