<?php
    session_start();
    require("dbconnect.php");

    mysqli_query($con, 'SET CHARACTER SET utf8');

    if(isset($_GET['type']) && $_GET['type'] == 'heater') {
        $query = "SELECT * FROM heater";
        $result = mysqli_query($con, $query) or die('Cannot load data from db');
        $heater_data = array();
        if(mysqli_num_rows($result)) {
            while ($heat_day = mysqli_fetch_assoc($result)) {
                $heater_data[] = $heat_day;
            }
        }

        header('Content-type: application/json');
        echo json_encode($heater_data);

        @mysqli_close($con);
    }
    elseif(isset($_POST['type']) && $_POST['type'] == 'new_heat_date' && isset($_POST['heat_data'])) {
        //do stuff with the heat data... which means decode the json and push it
        // to the db
    }

?>

