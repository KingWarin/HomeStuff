<?php
    session_start();
    require("dbconnect.php");

    mysqli_query($con, 'SET CHARACTER SET utf8');

    $text = "\nAccessed api";
//    file_put_contents('access.log', $text, FILE_APPEND);

    if(isset($_GET['type']) && $_GET['type'] == 'heater') {
        $text = "\nAccessed get heaters";
//        file_put_contents('access.log', $text, FILE_APPEND);
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
    elseif(isset($_POST['type']) && $_POST['type'] == 'new_heat_date') {
        $post = implode(' ', $_POST);
        $text = "\nAccessed new_heat_date with get: ".$_POST['type']." and post: " .$post;
//        file_put_contents('access.log', $text, FILE_APPEND);
        $heat_data = $_POST['heat_data'];
        $heat_data = json_decode(stripslashes($heat_data), true);
        $bath = $heat_data['bath'];
        $kitchen = $heat_data['kitchen'];
        $bed = $heat_data['bed'];
        $living = $heat_data['living'];
        $on = $heat_data['on'];
        $query = "INSERT INTO heater (id, date, bath, kitchen, bed, living, is_on) VALUES";
        $query = $query." (NULL,CURDATE(),$bath,$kitchen,$bed,$living,$on)";
        $result = mysqli_query($con, $query) or die('Could not insert new heat data');
//      if (mysqli_query($con, $query)) {
//          file_put_contents('access.log', 'Record created successfull.', FILE_APPEND);
//      } else {
//          $text =  "Error: " . $query . "\n" . mysqli_error($con);
//          file_put_contents('access.log', $text, FILE_APPEND);
//      }
        @mysqli_close($con);
    }
    else {
        $text = "\nAccessed fallback";
        file_put_contents('access.log', $text, FILE_APPEND);
        if(isset($_POST['json_data'])){
            $post = $_POST['json_data'];
            $text = '\nAccessed with post: ' .$post;
            file_put_contents('access.log', $text, FILE_APPEND);
        }
        else {
            file_put_contents('access.log', '\nNo post data', FILE_APPEND);
        }
        @mysqli_close($con);
    }
?>

