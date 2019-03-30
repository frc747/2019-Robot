package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.autonomous.BackoffRotateReloadAdaptive;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;

public class OI {

  public static boolean shiftHigh = false;

  public static boolean tongueIsOut = false;

  public static boolean latchInPosition = false;

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

  Button LEFT_STICK_BUTTON_SEVEN = new JoystickButton(leftStick, 7);

  @SuppressWarnings("resource")
  public OI() {
    
    SELECT_BUTTON.whileHeld(new LineTrackCommand());
    START_BUTTON.whileHeld(new ClimbCommandSafe());
    Y_BUTTON.whileHeld(new PIDDartMechanism(-221740));
    B_BUTTON.whileHeld(new PIDHatchMechanism(935, false)); //1020 //850
    //X_BUTTON.toggleWhenPressed(new ResetDartEncoder());
    //B_BUTTON.toggleWhenPressed(new ResetHatchEncoderCommand());
    LEFT_STICK_BUTTON_SEVEN.whenPressed(new BackoffRotateReloadAdaptive());
    
    SmartDashboard.putString("During Auto:", "Green - Auto is running; Red - Auto is finished");
    SmartDashboard.putString("After Auto:", "Green - Tongue is out; Red - Tongue is in");

    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
  }

  // Anything to be updated should be done in here
  public void updateOI() {
    
    SmartDashboard.putBoolean("Tongue is out: ", tongueIsOut);
    if (latchInPosition) {
      SmartDashboard.putString("Latch: ", "CLIMB");
    } else {
      SmartDashboard.putString("Latch: ", "DON'T CLIMB");
    }

    if (shiftHigh) {
      SmartDashboard.putString("GEAR: ", "HIGH");
    } else {
      SmartDashboard.putString("GEAR: ", "LOW");
    }

    // Limelight Value SmartDashboard display
    // SmartDashboard.putNumber("x value: ", x);
    // SmartDashboard.putNumber("y value: ", y);
    // SmartDashboard.putNumber("area value: ", area);
    
    // Sensor Values and Information
    // SmartDashboard.putNumber("Average Inches Driven", distance);

    // SmartDashboard.putNumber("Gear Shifter Encoder: ", Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Tongue Encoder: ", Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Dart Encoder: ", Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Latch Encoder: ", Robot.climb.latch.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Robot Heading", Robot.getNavXAngle());
    
    // SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    // SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));

  }
}
