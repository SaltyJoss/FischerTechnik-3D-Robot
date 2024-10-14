# FischerTechnik-3D-Robot

  ## Robot_Function - C++
  
  ### MOTOR_FUNCTION
    - Contains the robots functions, each motor (TT, Z, ARM, GRIP)
    - allocates them a variable to the corresponding pin
    - uses the whole relay to take advantage of going opposite ways
    - utilising back skills from C++ / C to code this part
    - basic functions for now, looking into more complex ideas
  
  ## RobotController - JAVA
  
  ### CLASS: UI
    - main class
    - deals with JFrame creation and menu page
    - deals with porting to arduino - Using the library: com.fazecast.jSerialComm
    - deals with outputting to arduino
    - contains important methods used elsewhere
    
  ### CLASS: controllerUI
    - subClass
    - extends JPanel
    - deals with data of the motors movements (0,0,0,0) was the easiest way for me to read it
    - helps get correct data to output to arduino
    - uses cardLayout from UI class to create an easy button system on the UI
    - deals with UI of the whole control Panel page
    
  ### CLASS: portArduino
    - Driver class
    - ports to arduino
    - has obj UI
    - runs the method portIdentify()
    
  ### CLASS: controllerExceptions
    - Parent class to the exceptions for UI-Specific, and Port-specific
    - extends Exceptions
    - precauctionary
