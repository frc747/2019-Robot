
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
import frc.robot.autonomous.RocketAutonomous;
import frc.robot.autonomous.RocketAutonomousArc;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;

public class OI {

  public static NetworkTable table;

  public static double x;
  public static double y;
  public static double area;

  public static double distance;

  public static Joystick driverController = new Joystick(RobotMap.Controller.DRIVER_CONTROLLER.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());
  
  Button B_BUTTON = new JoystickButton(driverController, 2);
  Button A_BUTTON = new JoystickButton(driverController, 1);
  Button Y_BUTTON = new JoystickButton(driverController, 4);
  Button X_BUTTON = new JoystickButton(driverController, 3);

  @SuppressWarnings("resource")
  public OI() {
    
    
    
    //A_BUTTON.toggleWhenPressed(new RocketAutonomousArc());
    //X_BUTTON.toggleWhenPressed(new RotationalLockMode());
    
    //Y_BUTTON.whileHeld(new LineTrackCommand());
    X_BUTTON.whileHeld(new CargoTrackCommand());
    B_BUTTON.whileHeld(new LineTrackCommand());
    Y_BUTTON.toggleWhenPressed(new PIDDriveInches(20.125, true));


    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
  }

  // Anything to be updated should be done in here
  public void updateOI() {

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

    SmartDashboard.putNumber("Left encoder", Robot.DRIVE_SUBSYSTEM.getLeftPosition());
    SmartDashboard.putNumber("Right encoder", Robot.DRIVE_SUBSYSTEM.getRightPosition());


  }
}
