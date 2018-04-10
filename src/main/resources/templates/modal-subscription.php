<?php
if ($_POST) {
  $to_email = "youremail@gmail.com"; //Recipient email, Replace with own email here

  //check if its an ajax request, exit if not
  if (!isset($_SERVER['HTTP_X_REQUESTED_WITH']) AND strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) != 'xmlhttprequest') {
    $output = json_encode(array( //create JSON data
      'type'=>'error',
      'text' => 'Sorry Request must be Ajax POST.'
    ));
    die($output); //exit script outputting json data
  }

  //Sanitize input data using PHP filter_var().
  $user_modal_trial_name    = filter_var($_POST["user_modal_trial_name"], FILTER_SANITIZE_STRING);
  $user_modal_trial_email   = filter_var($_POST["user_modal_trial_email"], FILTER_SANITIZE_EMAIL);
  $user_modal_trial_phone   = filter_var($_POST["user_modal_trial_phone"], FILTER_SANITIZE_NUMBER_INT);

  //additional php validation
  if (strlen($user_modal_trial_name) < 3) { // If length is less than 3 it will output JSON error.
    $output = json_encode(array('type'=>'error', 'text' => 'Name is too short or empty.'));
    die($output);
  }
  if (!filter_var($user_modal_trial_email, FILTER_VALIDATE_EMAIL)) { //email validation
    $output = json_encode(array('type'=>'error', 'text' => 'Please enter a valid email.'));
    die($output);
  }
  if (!filter_var($user_modal_trial_phone, FILTER_SANITIZE_NUMBER_FLOAT)) { //check for valid numbers in phone number field
    $output = json_encode(array('type'=>'error', 'text' => 'Enter only digits in phone number.'));
    die($output);
  }

  //email subject
  $subject = "Subscription to Trial";

  //email body
  $message_body = "Name  :  ".$user_modal_trial_name."\r\nEmail   : ".$user_modal_trial_email."\r\nPhone :  ".$user_modal_trial_phone;

  //proceed with PHP email.
  $headers = 'From: '.$user_modal_trial_email.''."\r\n".
  'Reply-To: '.$user_modal_trial_email.''."\r\n".
  'X-Mailer: PHP/'.phpversion();

  $send_mail = mail($to_email, $subject, $message_body, $headers);

  if (!$send_mail)
  {
    //If mail couldn't be sent output error. Check your PHP email configuration (if it ever happens)
    $output = json_encode(array('type'=>'error', 'text' => 'Could not send mail! Please check your PHP mail configuration.'));
    die($output);
  } else {
    $output = json_encode(array('type'=>'message', 'text' => 'Hi '.$user_modal_trial_name.', thank you for subscribing. Soon send you the link to download.'));
    die($output);
  }
}
?>
