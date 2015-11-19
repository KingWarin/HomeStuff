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
        $heat_data = $_POST['heat_data'];
        $heat_data = json_decode($heat_data, true);
        $bath = $heat_data['bath'];
        $kitchen = $heat_data['kitchen'];
        $bed = $heat_data['bed'];
        $living = $heat_data['living'];
        $on = $heat_data['on'];
        $query = "INSERT INTO heater (id, date, bath, kitchen, bed, living, on) VALUES";
        $query = $query."(NULL,NULL,'$bath','$kitchen','$bed','$living','$on')";
        @mysqli_close($con);
    }

?>

