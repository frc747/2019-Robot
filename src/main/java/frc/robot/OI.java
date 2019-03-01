
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

  public static Joystick leftStick = new Joystick(RobotMap.Controller.LEFT_STICK.getValue());
  public static Joystick rightStick = new Joystick(RobotMap.Controller.RIGHT_STICK.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());
  



  Button B_BUTTON = new JoystickButton(operatorController, 2);
  Button A_BUTTON = new JoystickButton(operatorController, 1);
  Button Y_BUTTON = new JoystickButton(operatorController, 4);
  Button X_BUTTON = new JoystickButton(operatorController, 3);
  Button SELECT_BUTTON = new JoystickButton(operatorController, 7);
  @SuppressWarnings("resource")
  public OI() {
    SELECT_BUTTON.whileHeld(new GetInRangeCommand());
    //A_BUTTON.toggleWhenPressed(new RocketAutonomousArc());
    //X_BUTTON.toggleWhenPressed(new RotationalLockMode());
    //Y_BUTTON.whileHeld(new LineTrackCommand());
   // X_BUTTON.whileHeld(new CargoTrackCommand());
    //B_BUTTON.whileHeld(new LineTrackCommand());
    //Y_BUTTON.toggleWhenPressed(new PIDDriveInches(20.125, true));
    B_BUTTON.whileHeld(new PIDHatchMechanism(1024, false));
    A_BUTTON.toggleWhenPressed(new PIDHatchMechanism(0, true));
    Y_BUTTON.toggleWhenPressed(new ResetHatchEncoderCommand());
    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
  }

  // Anything to be updated should be done in here
  public void updateOI() {

    int left;
    int right;

    //table = NetworkTableInstance.getDefault().getTable("limelight");
    table = NetworkTableInstance.getDefault().getTable("limelight");
    x = table.getEntry("tx").getDouble(0);
    y = table.getEntry("ty").getDouble(0);
    area = table.getEntry("ta").getDouble(0);
    
    SmartDashboard.putNumber("x value: ", x);
    SmartDashboard.putNumber("y value: ", y);
    SmartDashboard.putNumber("area value: ", area);
    SmartDashboard.putNumber("distance", distance);
    
    SmartDashboard.putNumber("Hatch Talon Position: ", Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition());

    //SmartDashboard.putString("Drive Type", DriveCommand.driveType);

    SmartDashboard.putNumber("robot heading", Robot.getNavXAngle());
    SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));
    SmartDashboard.putNumber("pot reading:", Robot.pot.get());

    if (Utility.getUserButton()) {
      inc++;
    }

    /*if (inc % 3 == 0) {
      DriveCommand.driveType = "tank";
    } else if ((inc+1) % 3 == 0) {
      DriveCommand.driveType = "fps";
    } else if ((inc+2) % 3 == 0) {
      DriveCommand.driveType = "arcade";
    }*/
  }
}
