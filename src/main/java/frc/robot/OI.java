
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
import frc.robot.autonomous.LeftLevelTwoCargoAuto;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;

public class OI {

  public static NetworkTable table;

  public static double x;
  public static double y;
  public static double area;

  public static boolean shiftHigh = false;

  public static double distance;

  public static Joystick leftStick = new Joystick(RobotMap.Controller.LEFT_STICK.getValue());
  public static Joystick rightStick = new Joystick(RobotMap.Controller.RIGHT_STICK.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());
  //commented out testController joystick
  // public static Joystick testController = new Joystick(3);

  //commented out testController joystickbuttons
  // Button Y_BUTTON_TEST = new JoystickButton(testController, 4);
  // Button B_BUTTON_TEST = new JoystickButton(testController, 2);
  // Button A_BUTTON_TEST = new JoystickButton(testController, 1);
  // Button LEFT_BUMPER_TEST = new JoystickButton(testController, 5);

  Button B_BUTTON = new JoystickButton(operatorController, 2);
  Button A_BUTTON = new JoystickButton(operatorController, 1);
  Button Y_BUTTON = new JoystickButton(operatorController, 4);
  Button X_BUTTON = new JoystickButton(operatorController, 3);
  Button SELECT_BUTTON = new JoystickButton(operatorController, 7);
  Button START_BUTTON = new JoystickButton(operatorController, 8);

  @SuppressWarnings("resource")
  public OI() {
    //commented out testController button commands so that there will be no errors when the third controller is not plugged in
    // LEFT_BUMPER_TEST.whileHeld(new DartDriveCommand());
    // B_BUTTON_TEST.toggleWhenPressed(new PIDDartMechanism(-221740));
    // A_BUTTON_TEST.toggleWhenPressed(new PIDDartMechanism(0));
    //A_BUTTON.toggleWhenPressed(new ResetDartEncoder());
    
    SELECT_BUTTON.whileHeld(new LineTrackCommand());
    START_BUTTON.whileHeld(new ClimbCommandSafe());
    //X_BUTTON.toggleWhenPressed(new LeftLevelTwoCargoAuto());
    //A_BUTTON.toggleWhenPressed(new PIDDriveInchesArc(70, false));
    Y_BUTTON.whileHeld(new PIDDartMechanism(-221740));
    // A_BUTTON.toggleWhenPressed(new PIDDartMechanism(0));
    B_BUTTON.whileHeld(new PIDHatchMechanism(850, false));
    // Y_BUTTON.toggleWhenPressed(new ResetHatchEncoderCommand());
    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
  }

  // Anything to be updated should be done in here
  public void updateOI() {
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
    SmartDashboard.putNumber("Gear Shifter: ", Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition());
    SmartDashboard.putNumber("Dart Encoder: ", Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition());
    SmartDashboard.putNumber("robot heading", Robot.getNavXAngle());
    SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));
    SmartDashboard.putNumber("Latch Encoder: ", Robot.climb.latch.getSelectedSensorPosition());
    //SmartDashboard.putNumber("pot reading:", Robot.pot.get());s
  }
}
