
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
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;



public class OI {

  public static NetworkTable table;

  public static double x;
  public static double y;
  public static double area;

  public static Joystick driverController = new Joystick(RobotMap.Controller.DRIVER_CONTROLLER.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());
  
  Button B_BUTTON = new JoystickButton(driverController, 2);
  Button A_BUTTON = new JoystickButton(driverController, 1);
  Button Y_BUTTON = new JoystickButton(driverController, 4);
  Button X_BUTTON = new JoystickButton(driverController, 3);

  @SuppressWarnings("resource")
  public OI() {

    
    B_BUTTON.toggleWhenPressed(new PIDDriveRotateSpark(90));
    A_BUTTON.toggleWhenPressed(new PIDDriveInches(50));
    X_BUTTON.toggleWhenPressed(new PIDDriveInchesSpark(50));
    
    //Y_BUTTON.whileHeld(new LineTrackCommand());
    
    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);

   // Button B_BUTTON = new JoystickButton;
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

    SmartDashboard.putNumber("robot heading", Robot.getNavXAngle());
    SmartDashboard.putNumber("left encoder pos", Robot.DRIVE_SUBSYSTEM.getLeftRevs());
    SmartDashboard.putNumber("right encoder pos", Robot.DRIVE_SUBSYSTEM.getRightRevs());
    SmartDashboard.putNumber("Climb Encoder Pos", Robot.DRIVE_SUBSYSTEM.getClimbRevs());

  }
}
