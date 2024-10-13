#include <Wire.h>                // Include Wire library for I2C
#include <Adafruit_GFX.h>       // Include Adafruit graphics library
#include <Adafruit_SSD1306.h>   // Include Adafruit SSD1306 library

#define SSD1306_I2C_ADDRESS 0x3C
#define SCREEN_WIDTH 128         // Define width of the display
#define SCREEN_HEIGHT 64         // Define height of the display

// Create an instance of the OLED display
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);

// defining constant integer variables for each relay input
const int relay1_TT = 2; // int assigned to analog pin 2
const int relay2_TT = 3; // int assigned to analog pin 3

const int relay1_Z = 4; // int assigned to digital pin 4
const int relay2_Z = 5; // int assigned to digital pin 5

const int relay1_Arm = 7; // int assigned to digital pin 7
const int relay2_Arm = 8; // int assigned to digital pin 8

const int relay1_Grip = 11; // int assigned to digital pin 11
const int relay2_Grip = 12; // int assigned to digital pin 12

// PWM speed values, research indicated 0 -> 255 is best
int PWM_TT = 50; // speed value of TT
int PWM_Z = 50; // speed value of Z

// defining constant integer variables for each limit switcher input, correlating with board
const int limitSwtch_TT = A0; // int assigned to analog pin 0
const int limitSwtch_Z = A1; // int assigned to analog pin 1
const int limitSwtch_Arm = A2; // int assigned to analog pin 2
const int limitSwtch_Grip = A3; // int assigned to analog pin 3

// defining joystick val to pins
const int joystick_X = A4; // Joystick 1 X-axis
const int joystick_Y = A5; // Joystick 1 Y-axis
const int joystick_SW = 6;  // Joystick 1 switch

// defining the threshold
const int threshold = 100; // sensitiy basically

void setup() {
  display.begin(SSD1306_I2C_ADDRESS, 0x3c); // Use the correct I2C address (0x3C or 0x3D)

  display.clearDisplay(); // Clear the display buffer
  display.setTextSize(1); // Set text size to 1
  display.setTextColor(SSD1306_WHITE); // Set text color to white
  display.setCursor(0, 0); // Set cursor to the top-left corner
  display.print("Hello, World!"); // Print text
  display.display(); // Display the text

  // relay pins to output
  pinMode(relay1_TT, OUTPUT);
  pinMode(relay2_TT, OUTPUT);

  pinMode(relay1_Z, OUTPUT);
  pinMode(relay2_Z, OUTPUT);
  
  pinMode(relay1_Arm, OUTPUT);
  pinMode(relay2_Arm, OUTPUT);

  pinMode(relay1_Grip, OUTPUT);
  pinMode(relay2_Grip, OUTPUT);

  // limitSwitch set to input, recieve any data
  pinMode(limitSwtch_TT, INPUT_PULLUP); // INPUT_PULLUP -> CONFIGUES PIN AS INPUT WITH INTERNAL 'PULL-UP' RESISTOR
  pinMode(limitSwtch_Z, INPUT_PULLUP);
  pinMode(limitSwtch_Arm, INPUT_PULLUP);
  pinMode(limitSwtch_Grip, INPUT_PULLUP);

  // Joystick pins set to input
  pinMode(joystick_X, INPUT);
  pinMode(joystick_Y, INPUT);
  pinMode(joystick_SW, INPUT_PULLUP);

  // SETTING ALL RELAYS TO OFF
  digitalWrite(relay1_TT, LOW);
  digitalWrite(relay2_TT, LOW);
  digitalWrite(relay1_Z, LOW);
  digitalWrite(relay2_Z, LOW);
}

void loop(){
  int xVal = analogRead(joystick_X); // reads the x joystick
  int yVal = analogRead(joystick_Y); // reads the y joystick
  
  verticalMotor(xVal, relay1_TT, relay2_TT, threshold);
  verticalMotor(yVal, relay1_Z, relay2_Z, threshold);
}

void baseMotor(int val, int relay1, int relay2, int sense){
  // Control base motor (TT)
  if (val > (512 + sense)) {
    // Move TT clockwise
    digitalWrite(relay1, HIGH);
    digitalWrite(relay2, LOW);
  } 
  else if (val < (512 - sense)) {
    // Move TT counter-clockwise
    digitalWrite(relay2, LOW);
    digitalWrite(relay2, HIGH);
  } else {
    // Stop TT motor
    digitalWrite(relay1, LOW);
    digitalWrite(relay2, LOW);
  }
}

void verticalMotor(int val, int relay1, int relay2, int sense){
  // Control vertical motor (Z)
  if (val > (512 + sense)) {
    // Move UP
    digitalWrite(relay1, HIGH);
    digitalWrite(relay2, LOW);
  } 
  else if (val < (512 - sense)) {
    // Move DOWN
    digitalWrite(relay2, LOW);
    digitalWrite(relay2, HIGH);
  } else {
    // Stop Z motor
    digitalWrite(relay1, LOW);
    digitalWrite(relay2, LOW);
  }
}