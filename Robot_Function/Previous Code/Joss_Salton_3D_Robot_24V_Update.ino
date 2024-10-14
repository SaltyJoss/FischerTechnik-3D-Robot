// defining constant integer variables for each relay input
const int relay_TT = 13; // int assigned to analog pin 5
const int relay_Z = 2; // int assigned to digital pin 3
const int relay_Arm = 3; // int assigned to digital pin 4
const int relay_Grip = 4; // int assigned to digital pin 5

// PWM speed values, research indicated 0 -> 255 is best
int PWM_TT = 100; // speed value of TT
int PWM_Z = 100; // speed value of Z

// defining constant integer variables for each limit switcher input, correlating with board
const int limitSwtch_TT = A0; // int assigned to analog pin 0
const int limitSwtch_Z = A1; // int assigned to analog pin 1
const int limitSwtch_Arm = A2; // int assigned to analog pin 2
const int limitSwtch_Grip = A3; // int assigned to analog pin 3

// defining constant integer variables for each pulse input
const int pulseCount_TTA = 7; // int assigned to digital pin 6
const int pulseCount_TTB = 8; // int assigned to digital pin 7
const int pulseCount_ZA = 9; // int assigned to digital pin 8
const int pulseCount_ZB = 10; // int assigned to digital pin 9
const int pulseCount_Arm = 11; // int assigned to digital pin 10
const int pulseCount_Grip = 12; // int assigned to digital pin 11

// defining volatile integer variable for counting pulses
volatile int count_TTA = 0; // volatile referes to a variable that may / may-not change randomly
volatile int count_TTB = 0; // all values preset to 0, add up and only up
volatile int count_ZA = 0;
volatile int count_ZB = 0;
volatile int count_Arm = 0;
volatile int count_Grip = 0;

// Defining variables for joystick
const int joystick1_X = A4; // Joystick 1 X-axis
const int joystick1_Y = A5; // Joystick 1 Y-axis
const int joystick1_SW = 6;  // Joystick 1 switch
const int joystick2_X = A6; // Joystick 2 X-axis
const int joystick2_Y = A7; // Joystick 2 Y-axis
const int joystick2_SW = 7;  // Joystick 2 switch

void setup() {
  // setup Serial for debugging, serial port at 9600 bps
  Serial.begin(9600);
  
  // relay pins to output
  pinMode(relay_TT, OUTPUT);
  pinMode(relay_Z, OUTPUT);
  pinMode(relay_Arm, OUTPUT);
  pinMode(relay_Grip, OUTPUT);
  // limitSwitch set to input, recieve any data
  pinMode(limitSwtch_TT, INPUT_PULLUP); // INPUT_PULLUP -> CONFIGUES PIN AS INPUT WITH INTERNAL 'PULL-UP' RESISTOR
  pinMode(limitSwtch_Z, INPUT_PULLUP);
  pinMode(limitSwtch_Arm, INPUT_PULLUP);
  pinMode(limitSwtch_Grip, INPUT_PULLUP);
  // pulseCount set to input, recieve any data
  pinMode(pulseCount_TTA, INPUT);
  pinMode(pulseCount_TTB, INPUT);
  pinMode(pulseCount_ZA, INPUT);
  pinMode(pulseCount_ZB, INPUT);
  pinMode(pulseCount_Arm, INPUT);
  pinMode(pulseCount_Grip, INPUT);

  // Joystick pins set to input
  pinMode(joystick1_X, INPUT);
  pinMode(joystick1_Y, INPUT);
  pinMode(joystick1_SW, INPUT_PULLUP);
  pinMode(joystick2_X, INPUT);
  pinMode(joystick2_Y, INPUT);
  pinMode(joystick2_SW, INPUT_PULLUP);

  // attach interrupts to the pulseCount inputs, counts pulses at every interval
  attachInterrupt(digitalPinToInterrupt(pulseCount_TTA), countPulse_TTA, RISING); // saying which pin to interrupt, function called when its interrupted, mode that triggers it
  attachInterrupt(digitalPinToInterrupt(pulseCount_TTB), countPulse_TTB, RISING); // RISING -> LOW to HIGH
  attachInterrupt(digitalPinToInterrupt(pulseCount_ZA), countPulse_ZA, RISING);
  attachInterrupt(digitalPinToInterrupt(pulseCount_ZB), countPulse_ZB, RISING);
  attachInterrupt(digitalPinToInterrupt(pulseCount_Arm), countPulse_Arm, RISING);
  attachInterrupt(digitalPinToInterrupt(pulseCount_Grip), countPulse_Grip, RISING);
}


void loop() {
  // Read joystick values for motors TT and Z
  int xValue1 = analogRead(joystick1_X);
  int yValue1 = analogRead(joystick1_Y);
  bool button1 = digitalRead(joystick1_SW) == LOW; // Button pressed when LOW

  // Control TT motor
  if (yValue1 < 400) { // Forward
    motorForward(relay_TT, relay_Z, PWM_TT);
  } else if (yValue1 > 600) { // Backward
    motorBackward(relay_TT, relay_Z, PWM_TT);
  } else { // Stop
    stopMotor(relay_TT);
  }

  // Control Z motor
  if (xValue1 < 400) { // Left
    motorForward(relay_Z, relay_TT, PWM_Z);
  } else if (xValue1 > 600) { // Right
    motorBackward(relay_Z, relay_TT, PWM_Z);
  } else { // Stop
    stopMotor(relay_Z);
  }

  // Check joystick 1 button for special function
  if (button1) {
    // Perform an action when the button is pressed
    Serial.println("Joystick 1 Button Pressed");
    // Add action here
  }

  // Read joystick values for motors Arm and Grip
  int xValue2 = analogRead(joystick2_X);
  int yValue2 = analogRead(joystick2_Y);
  bool button2 = digitalRead(joystick2_SW) == LOW; // Button pressed when LOW

  // Control Arm motor
  if (yValue2 < 400) { // Up
    motorForward(relay_Arm, relay_Grip, PWM_TT);
  } else if (yValue2 > 600) { // Down
    motorBackward(relay_Arm, relay_Grip, PWM_TT);
  } else { // Stop
    stopMotor(relay_Arm);
  }

  // Control Grip motor
  if (xValue2 < 400) { // Open
    motorForward(relay_Grip, relay_Arm, PWM_Z);
  } else if (xValue2 > 600) { // Close
    motorBackward(relay_Grip, relay_Arm, PWM_Z);
  } else { // Stop
    stopMotor(relay_Grip);
  }

  // Check joystick 2 button for special function
  if (button2) {
    // Perform an action when the button is pressed
    Serial.println("Joystick 2 Button Pressed");
    // Add action here
  }

  // Add a short delay to stabilize reading
  delay(50);
}

void motorForward(int relayPin, int pwmPin, int pwmSpeed){
  digitalWrite(relayPin, HIGH);
  analogWrite(pwmPin, pwmSpeed);
}

void motorBackward(int relayPin, int pwmPin, int pwmSpeed){
  digitalWrite(relayPin, LOW);
  analogWrite(pwmPin, pwmSpeed);
}

void stopMotor(int relayPin){
  digitalWrite(relayPin, LOW);
  analogWrite(relayPin, 0);
}


void countPulse_TTA(){
  count_TTA++;
}
void countPulse_TTB(){
  count_TTB++;
}
void countPulse_ZA(){
  count_ZA++;
}
void countPulse_ZB(){
  count_ZB++;
}
void countPulse_Arm(){
  count_Arm++;
}
void countPulse_Grip(){
  count_Grip++;
}

/*
void loop() {
    // Check the limit switch for TT and control the relay accordingly
  if (digitalRead(limitSwtch_TT) == HIGH) {
    digitalWrite(relay_TT, HIGH); // Turn relay on
    delay(1000);                  // 1 second delay (1000 = m/s)
    digitalWrite(relay_TT, LOW); // Turn relay off
    Serial.println(count_TTA, count_TTB);
  }
  // Check the limit switch for Z and controls the relay
  if (digitalRead(limitSwtch_Z) == HIGH) {
    digitalWrite(relay_Z, HIGH); // Turn relay on
    delay(1000);                 // 1 second delay (1000 = m/s)
    digitalWrite(relay_Z, LOW); // Turn relay off
    Serial.println(count_ZA, count_ZB);
  }
  // Check the limit switch for Z and controls the relay
  if (digitalRead(limitSwtch_Arm) == HIGH) {
    digitalWrite(relay_Arm, HIGH); // Turn relay on
    delay(1000);                 // 1 second delay (1000 = m/s)
    digitalWrite(relay_Arm, LOW); // Turn relay off
    Serial.println(count_Arm);
  }
    // Check the limit switch for Z and controls the relay
  if (digitalRead(limitSwtch_Grip) == HIGH) {
    digitalWrite(relay_Grip, HIGH); // Turn relay on
    delay(1000);                 // 1 second delay (1000 = m/s)
    digitalWrite(relay_Grip, LOW);  // Turn relay off
    Serial.println(count_Grip);
  }

}
*/
