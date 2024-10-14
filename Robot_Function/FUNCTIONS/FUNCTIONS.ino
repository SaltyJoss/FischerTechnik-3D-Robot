
// defining constant integer variables for each relay input
const int relay1_TT = 2; // int assigned to analog pin 2
const int relay2_TT = 3; // int assigned to analog pin 3

const int relay1_Z = 4; // int assigned to digital pin 4
const int relay2_Z = 5; // int assigned to digital pin 5

const int relay1_Arm = 6; // int assigned to digital pin 7
const int relay2_Arm = 7; // int assigned to digital pin 8

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

// defining the threshold
const int threshold = 100; // sensitiy basically

void setup() {
  Serial.begin(9600);

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

  // SETTING ALL RELAYS TO OFF
  digitalWrite(relay1_TT, LOW);
  digitalWrite(relay2_TT, LOW);
  digitalWrite(relay1_Z, LOW);
  digitalWrite(relay2_Z, LOW);
  digitalWrite(relay1_Arm, LOW);
  digitalWrite(relay2_Arm, LOW);
  digitalWrite(relay1_Grip, LOW);
  digitalWrite(relay2_Grip, LOW);
}

void loop(){
  if(Serial.available() > 0){
    char command = Serial.read();
    int speed = Serial.read();
    if (command == '1'){
      digitalWrite(relay1_TT, HIGH);
      digitalWrite(relay2_TT, LOW);
    } else if(command == '2'){
      digitalWrite(relay1_TT, LOW);
      digitalWrite(relay2_TT, HIGH);
    } else if (command == '3'){
      digitalWrite(relay1_Z, HIGH);
      digitalWrite(relay2_Z, LOW);
    } else if(command == '4'){
      digitalWrite(relay1_Z, LOW);
      digitalWrite(relay2_Z, HIGH);
    } else if (command == '5'){
      digitalWrite(relay1_Arm, HIGH);
      digitalWrite(relay2_Arm, LOW);
    } else if(command == '6'){
      digitalWrite(relay1_Arm, LOW);
      digitalWrite(relay2_Arm, HIGH);
    } else if (command == '7'){
      digitalWrite(relay1_Grip, HIGH);
      digitalWrite(relay2_Grip, LOW);
    } else if(command == '8'){
      digitalWrite(relay1_Grip, LOW);
      digitalWrite(relay2_Grip, HIGH);
    } else if (command == '0') {
      digitalWrite(relay1_TT, LOW);
      digitalWrite(relay2_TT, LOW);
      digitalWrite(relay1_Z, LOW);
      digitalWrite(relay2_Z, LOW);
      digitalWrite(relay1_Arm, LOW);
      digitalWrite(relay2_Arm, LOW);
      digitalWrite(relay1_Grip, LOW);
      digitalWrite(relay2_Grip, LOW);
    }
  }
}