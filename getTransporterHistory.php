<?php
include_once './db_functions.php';
//Create Object for DB_Functions clas
$db = new DB_Functions(); 
//Get JSON posted by Android Application

$json = $_POST["getTransporterHistory"];
//Remove Slashes

if (get_magic_quotes_gpc()){
	$json = stripslashes($json);
}
//Decode JSON into an Array
$data = json_decode($json);


//Util arrays to create response JSON
$a=array();
$b=array();
//Loop through an Array and insert data read from JSON into MySQL DB

for($i=0; $i<count($data) ; $i++)
{
	//Store User into MySQL DB   
	$res = $db->getTransactionTransporter($data[$i]->phonenumber);
	
	while ($row = mysql_fetch_assoc($res)){
		array_push($a, $row);
		$res2 = $db->getTransporter($row['phonenumbertransporter']);
                $row2 = mysql_fetch_assoc($res2);
                $row['firstname'] = $row2['firstname'];
                $row['lastname'] = $row2['lastname'];
	}

}
//Post JSON response back to Android Application
echo json_encode($a);
?>
