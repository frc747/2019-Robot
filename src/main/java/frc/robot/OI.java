
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import edu.wpi.first.networktables.*;

public class OI {

  public static NetworkTable table;

  public static double x;
  public static double y;
  public static double area;

  // private int inc = 1;

  public static double distance;

  public static Joystick driverController = new Joystick(RobotMap.Controller.DRIVER_CONTROLLER.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());

  //DRIVER CONTROLLER
  Button A_BUTTON_D = new JoystickButton(driverController, 1);
  Button B_BUTTON_D = new JoystickButton(driverController, 2);
  Button X_BUTTON_D = new JoystickButton(driverController, 3);
  Button Y_BUTTON_D = new JoystickButton(driverController, 4);
  Button LEFT_BUMPER_D = new JoystickButton(driverController, 5);
  Button RIGHT_BUMPER_D = new JoystickButton(driverController, 6);
  Button BACK_BUTTON_D = new JoystickButton(driverController, 7);
  Button START_BUTTON_D = new JoystickButton(driverController, 8);
  Button LEFT_STICK_D = new JoystickButton(driverController, 9);
  Button RIGHT_STICK_D = new JoystickButton(driverController, 10);

  //OPERATOR CONTROLLER
  Button A_BUTTON_O = new JoystickButton(driverController, 1);
  Button B_BUTTON_O = new JoystickButton(driverController, 2);
  Button X_BUTTON_O = new JoystickButton(driverController, 3);
  Button Y_BUTTON_O = new JoystickButton(driverController, 4);
  Button LEFT_BUMPER_O = new JoystickButton(driverController, 5);
  Button RIGHT_BUMPER_O = new JoystickButton(driverController, 6);
  Button BACK_BUTTON_O = new JoystickButton(driverController, 7);
  Button START_BUTTON_O = new JoystickButton(driverController, 8);
  Button LEFT_STICK_O = new JoystickButton(driverController, 9);
  Button RIGHT_STICK_O = new JoystickButton(driverController, 10);

  @SuppressWarnings("resource")
  public OI() {

    //A_BUTTON.toggleWhenPressed(new RocketAutonomousArc());
    //X_BUTTON.toggleWhenPressed(new RotationalLockMode());
    //Y_BUTTON.whileHeld(new LineTrackCommand());
   // X_BUTTON.whileHeld(new CargoTrackCommand());
    //B_BUTTON.whileHeld(new LineTrackCommand());
    //Y_BUTTON.toggleWhenPressed(new PIDDriveInches(20.125, true));


    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
  }

  // Anything to be updated should be done in here
  public void updateOI() {

    //Button IDs and Axis IDs are placeholders, NOT REAL
    if(operatorController.getRawButton(0)) {
      
    } else if (operatorController.getRawAxis(7) > .2) {

    } else if (operatorController.getRawAxis(9) > .2) {

    } else if (operatorController.getRawButton(0)) {

    } else {
      A_BUTTON_D.whileHeld(new DoNothing());
      B_BUTTON_D.whileHeld(new DoNothing());
      X_BUTTON_D.whileHeld(new DoNothing());
      Y_BUTTON_D.whileHeld(new DoNothing());
      LEFT_BUMPER_D.whileHeld(new DoNothing()); 
      RIGHT_BUMPER_D.whileHeld(new DoNothing());
      BACK_BUTTON_D.whileHeld(new DoNothing()); 
      START_BUTTON_D.whileHeld(new DoNothing());
      LEFT_STICK_D.whileHeld(new DoNothing());
      RIGHT_STICK_D.whileHeld(new DoNothing()); 

      A_BUTTON_O.whileHeld(new DoNothing());
      B_BUTTON_O.whileHeld(new DoNothing()); 
      X_BUTTON_O.whileHeld(new DoNothing()); 
      Y_BUTTON_O.whileHeld(new DoNothing()); 
      LEFT_BUMPER_O.whileHeld(new DoNothing());  
      RIGHT_BUMPER_O.whileHeld(new DoNothing()); 
      BACK_BUTTON_O.whileHeld(new DoNothing());  
      START_BUTTON_O.whileHeld(new DoNothing()); 
      LEFT_STICK_O.whileHeld(new DoNothing()); 
      RIGHT_STICK_O.whileHeld(new DoNothing());  
    }






    

    SmartDashboard.putNumber("Joystick DPAD Value: ", operatorController.getPOV());

    //table = NetworkTableInstance.getDefault().getTable("limelight");
    table = NetworkTableInstance.getDefault().getTable("limelight");
    x = table.getEntry("tx").getDouble(0);
    y = table.getEntry("ty").getDouble(0);
    area = table.getEntry("ta").getDouble(0);
    
    SmartDashboard.putNumber("x value: ", x);
    SmartDashboard.putNumber("y value: ", y);
    SmartDashboard.putNumber("area value: ", area);
    SmartDashboard.putNumber("distance", distance);
    
    SmartDashboard.putString("Drive Type", DriveCommand.driveType);

    SmartDashboard.putNumber("robot heading", Robot.getNavXAngle());
    SmartDashboard.putNumber("Joystick Left", driverController.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", driverController.getRawAxis(5));
    SmartDashboard.putNumber("pot reading:", Robot.pot.get());

    // if (Utility.getUserButton()) {
    //   inc++;
    // }

    // if (inc % 3 == 0) {
    //   DriveCommand.driveType = "tank";
    // } else if ((inc+1) % 3 == 0) {
    //   DriveCommand.driveType = "fps";
    // } else if ((inc+2) % 3 == 0) {
    //   DriveCommand.driveType = "arcade";
    // }
  }
}
