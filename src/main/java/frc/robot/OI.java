
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
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.autonomous.RocketAutonomous;
import frc.robot.autonomous.RocketAutonomousArc;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Utility;

import edu.wpi.first.networktables.*;

public class OI {

  public static NetworkTable table;

  public static double x;
  public static double y;
  public static double area;

  private int inc = 1;

  public static double distance;

  public static Joystick driverController = new Joystick(RobotMap.Controller.DRIVER_CONTROLLER.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());
  
  Button B_BUTTON = new JoystickButton(driverController, 2);
  Button A_BUTTON = new JoystickButton(driverController, 1);
  Button Y_BUTTON = new JoystickButton(driverController, 4);
  Button X_BUTTON = new JoystickButton(driverController, 3);

  @SuppressWarnings("resource")
  public OI() {
    
    //DPAD CONTROLS
    int DPAD_ANGLE = driverController.getPOV();
    if (DPAD_ANGLE == -1) {} //Default Position - No DPAD Buttons Pressed
    if (DPAD_ANGLE == 0) { DriveCommand.driveType = "tank"; } //Up Position
    if (DPAD_ANGLE == 45) {} //Up-Right Position
    if (DPAD_ANGLE == 90) { DriveCommand.driveType = "fps"; } //Right Position
    if (DPAD_ANGLE == 135) {} //Down-Right Position
    if (DPAD_ANGLE == 180) { DriveCommand.driveType = null; } //Down Position
    if (DPAD_ANGLE == 225) {} //Down-Left Position
    if (DPAD_ANGLE == 270) { DriveCommand.driveType = "arcade"; } //Left Position
    if (DPAD_ANGLE == 315) {} //Up-Left Position

    A_BUTTON.toggleWhenPressed(new RocketAutonomousArc());
    X_BUTTON.toggleWhenPressed(new RotationalLockMode());
    
    //Y_BUTTON.whileHeld(new LineTrackCommand());
    //B_BUTTON.whileHeld(new CargoTrackCommand());
    B_BUTTON.toggleWhenPressed(new PIDDriveRotateCustom(90));
    Y_BUTTON.toggleWhenPressed(new PIDDriveInches(10, true));


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

    if (Utility.getUserButton()) {
      inc++;
    }

    if (inc % 3 == 0) {
      DriveCommand.driveType = "tank";
    } else if ((inc+1) % 3 == 0) {
      DriveCommand.driveType = "fps";
    } else if ((inc+2) % 3 == 0) {
      DriveCommand.driveType = "arcade";
    }
  }
}
