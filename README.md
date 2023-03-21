# MyBuddy

Documentation for MyBuddy Android App

Overview
MyBuddy is an Android application that collects student data and sends it to a remote Google Apps Script web app to store the information. The app generates a random string representing a unique identifier for each entry. The app stores the unique identifier, date, and time in SharedPreferences and displays the unique identifier on the screen.

MainActivity Class
The MainActivity class is the main activity for the application. It extends AppCompatActivity and contains the core functionality for collecting and sending students' data to the remote server.

Variables
EditText name, phone, roll, purpose: EditText fields for collecting student information (name, phone, roll number, and purpose).
Button button: Button for submitting the student's data.
TextView textview: TextView for displaying the generated unique identifier (random string).
SharedPreferences sharedPreferences: SharedPreferences object for storing the unique identifier, date, and time.
ProgressDialog pd: ProgressDialog object to show a loading spinner while the data is being sent.

Constants
SHARED_PREF_NAME: The name of the SharedPreferences file.
KEY_TEXT: Key for storing the unique identifier in SharedPreferences.
KEY_TIME: Key for storing the current time in SharedPreferences.
KEY_DATE: Key for storing the current date in SharedPreferences.

Methods
onCreate(Bundle savedInstanceState)
This method is called when the activity is created. It initializes the views, sets up the ProgressDialog, and sets the click listener for the submit button.

addStudentData()
This method collects the student's data, generates the unique identifier, and sends the data to the remote server using a StringRequest. If the request is successful, the app navigates to the UpdateData activity.

onPause()
This method is called when the activity is paused. It stores the unique identifier, date, and time in SharedPreferences.

getRandomString(int i)
This is a helper method that generates a random string of specified length. The string consists of lowercase letters and digits.

UpdateData Class
The UpdateData class is not provided in the code snippet. It is assumed to be another activity for displaying or updating the submitted student data.
