<?php
include("connection.php");

header('Content-Type: application/json');

// Enable error reporting for debugging
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Create emergency_alerts table if it doesn't exist
$create_table = "CREATE TABLE IF NOT EXISTS emergency_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(15) NOT NULL,
    message TEXT NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)";

$result = mysqli_query($con, $create_table) or die(mysqli_error($con));

// Validate required POST parameters
if (!isset($_POST['phone']) || empty($_POST['phone'])) {
    error_log("Emergency alert: Missing phone number");
    echo json_encode([
        "status" => "0",
        "message" => "Phone number is required",
        "error_type" => "missing_phone"
    ]);
    exit();
}

if (!isset($_POST['message']) || empty($_POST['message'])) {
    error_log("Emergency alert: Missing message");
    echo json_encode([
        "status" => "0",
        "message" => "Message is required",
        "error_type" => "missing_message"
    ]);
    exit();
}

$phone = mysqli_real_escape_string($con, $_POST['phone']);
$message = mysqli_real_escape_string($con, $_POST['message']);

// Validate and process uploaded image
if (!isset($_FILES['image'])) {
    error_log("Emergency alert: No image file received");
    echo json_encode([
        "status" => "0",
        "message" => "Image file is required",
        "error_type" => "no_file"
    ]);
    exit();
}

$image_file = $_FILES['image'];

// Check for upload errors
if ($image_file['error'] !== UPLOAD_ERR_OK) {
    $error_message = "";
    switch ($image_file['error']) {
        case UPLOAD_ERR_INI_SIZE:
            $error_message = "The uploaded file exceeds the upload_max_filesize directive";
            break;
        case UPLOAD_ERR_FORM_SIZE:
            $error_message = "The uploaded file exceeds the MAX_FILE_SIZE directive";
            break;
        case UPLOAD_ERR_PARTIAL:
            $error_message = "The uploaded file was only partially uploaded";
            break;
        case UPLOAD_ERR_NO_FILE:
            $error_message = "No file was uploaded";
            break;
        case UPLOAD_ERR_NO_TMP_DIR:
            $error_message = "Missing a temporary folder";
            break;
        case UPLOAD_ERR_CANT_WRITE:
            $error_message = "Failed to write file to disk";
            break;
        default:
            $error_message = "Unknown upload error";
    }
    error_log("Emergency alert: Image upload error - " . $error_message);
    echo json_encode([
        "status" => "0",
        "message" => $error_message,
        "error_type" => "upload_error",
        "error_code" => $image_file['error']
    ]);
    exit();
}

// Validate file size (max 10MB)
if ($image_file['size'] > 10 * 1024 * 1024) {
    error_log("Emergency alert: File too large - " . $image_file['size']);
    echo json_encode([
        "status" => "0",
        "message" => "File is too large. Maximum size is 10MB",
        "error_type" => "file_too_large",
        "file_size" => $image_file['size']
    ]);
    exit();
}

// Validate image type
$allowed_types = ['image/jpeg', 'image/png', 'image/gif'];
if (!in_array($image_file['type'], $allowed_types)) {
    error_log("Emergency alert: Invalid image type - " . $image_file['type']);
    echo json_encode([
        "status" => "0",
        "message" => "Invalid image type. Only JPEG, PNG and GIF are allowed",
        "error_type" => "invalid_type",
        "file_type" => $image_file['type']
    ]);
    exit();
}

// Generate unique filename and set up file path
$image_extension = pathinfo($image_file['name'], PATHINFO_EXTENSION);
$filename = 'emergency_' . time() . '_' . uniqid() . '.' . $image_extension;
$upload_dir = 'emergency_images';
$filepath = $upload_dir . '/' . $filename;

// Ensure upload directory exists with proper permissions
if (!file_exists($upload_dir)) {
    if (!mkdir($upload_dir, 0755, true)) {
        error_log("Emergency alert: Failed to create directory - $upload_dir");
        echo json_encode([
            "status" => "0",
            "message" => "Server configuration error",
            "error_type" => "directory_creation_failed"
        ]);
        exit();
    }
}

// Ensure directory is writable
if (!is_writable($upload_dir)) {
    if (!chmod($upload_dir, 0755)) {
        error_log("Emergency alert: Directory not writable - $upload_dir");
        echo json_encode([
            "status" => "0",
            "message" => "Server configuration error",
            "error_type" => "directory_permission_error"
        ]);
        exit();
    }
}

// Move uploaded file to destination
if (!move_uploaded_file($image_file['tmp_name'], $filepath)) {
    $upload_error = error_get_last();
    error_log("Emergency alert: Failed to move uploaded file - " . ($upload_error ? $upload_error['message'] : 'Unknown error'));
    echo json_encode([
        "status" => "0",
        "message" => "Failed to save emergency image",
        "error_type" => "move_failed",
        "error_details" => $upload_error ? $upload_error['message'] : 'Unknown error'
    ]);
    exit();
}

// Verify file was actually created
if (!file_exists($filepath)) {
    error_log("Emergency alert: File not created after move");
    echo json_encode([
        "status" => "0",
        "message" => "Failed to save emergency image",
        "error_type" => "file_not_created"
    ]);
    exit();
}

// Insert the emergency alert into the database
try {
    $query = "INSERT INTO emergency_alerts (phone, message, image_path) VALUES (?, ?, ?)";
    $stmt = $con->prepare($query);
    $stmt->bind_param("sss", $phone, $message, $filepath);

    if ($stmt->execute()) {
        echo json_encode([
            "status" => "1",
            "message" => "Emergency alert sent successfully",
            "image_path" => $filename,
            "full_path" => $filepath
        ]);
    } else {
        throw new Exception("Database error: " . $stmt->error);
    }
    $stmt->close();
} catch (Exception $e) {
    error_log("Emergency alert database error: " . $e->getMessage());
    // Clean up the file if database insert fails
    if (file_exists($filepath)) {
        unlink($filepath);
    }
    echo json_encode([
        "status" => "0",
        "message" => "Failed to save emergency alert",
        "error_type" => "database_error",
        "error_details" => $e->getMessage()
    ]);
}

?>