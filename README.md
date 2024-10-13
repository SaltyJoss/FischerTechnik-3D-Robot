# FischerTechnik-3D-Robot

## Robot_Function

### MOTOR_FUNCTION
- Contains the robots functions, each motor (TT, Z, ARM, GRIP)
- 

## RobotController

### ControllerUI
- JFrame UI class
- Extends and Imports JFrame for graphical window
- Custom buttons to controll the robot
- throws controllerExceptions
- Parent to the 'motorname' subclasses

#### motorTT
- subclass of ControllerUI
- deals with the TT motor (base rotation)

#### motorZ
- subclass of ControllerUI
- deals with the Z motor (vertical movement)

#### motorARM
- subclass of ControllerUI
- deals with the ARM motor (horizontal movement)

#### motorGRIP
- subclass of ControllerUI
- deals with the GRIP motor (pincer movemement

### portArduino
- Driver class
- ports to arduino
- runs obj UI

### controllerExceptions
- Parent class to the exceptions for UI-Specific, and Port-specific
- extends Exceptions
