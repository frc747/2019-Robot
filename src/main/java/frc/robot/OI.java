package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

public class OI {

  public static boolean shiftHigh = false;

  public static boolean tongueIsOut = false;

  public static boolean latchInPosition = false;

  public static double distance;

  public static NetworkTable table;
  public static double v;
  public static double x;
  public static double y;
  public static double area;

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

  // Button LEFT_STICK_BUTTON_SEVEN = new JoystickButton(leftStick, 7);

  @SuppressWarnings("resource")
  public OI() {
    
    SELECT_BUTTON.whileHeld(new VisionTrackCommand());
    START_BUTTON.whileHeld(new ClimbCommandSafe());
    Y_BUTTON.whileHeld(new PIDDartMechanism(-221740));
    B_BUTTON.whileHeld(new PIDHatchMechanism(935, false)); //1020 //850
    //X_BUTTON.toggleWhenPressed(new ResetDartEncoder());
    //B_BUTTON.toggleWhenPressed(new ResetHatchEncoderCommand());

    // LEFT_STICK_BUTTON_SEVEN.whenPressed(new BackoffRotateReloadAdaptive());
    
    SmartDashboard.putString("During Auto:", "Green - Auto is running; Red - Auto is finished");
    SmartDashboard.putString("After Auto:", "Green - Tongue is out; Red - Tongue is in");

    SmartDashboard.putBoolean("Currently Vision Tracking", false);
    // DriverStation.getInstance().getMatchTime();

    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
    // OI.table.getEntry("stream").setDouble(0);
  }

  // Anything to be updated should be done in here
  public void updateOI() {
    
    // SmartDashboard.putNumber("Countdown", DriverStation.getInstance().getMatchTime());

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
    table = NetworkTableInstance.getDefault().getTable("limelight");

    v = table.getEntry("tv").getDouble(0);

    x = table.getEntry("tx").getDouble(0);
    y = table.getEntry("ty").getDouble(0);
    area = table.getEntry("ta").getDouble(0);

    if (Robot.DRIVE_SUBSYSTEM.tracking) {
      OI.table.getEntry("camMode").setDouble(0);
      OI.table.getEntry("ledMode").setDouble(3);
    } else {
      OI.table.getEntry("camMode").setDouble(1);
      OI.table.getEntry("ledMode").setDouble(1);
    }

    // SmartDashboard.putNumber("x value: ", Robot.x);
    // SmartDashboard.putNumber("y value: ", Robot.y);
    // SmartDashboard.putNumber("area value: ", Robot.area);
    
    // Sensor Values and Information
    // SmartDashboard.putNumber("Average Inches Driven", distance);

    // SmartDashboard.putNumber("Gear Shifter Encoder: ", Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Tongue Encoder: ", Robot.HATCH_SUBSYSTEM.hatchTalon.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Dart Encoder: ", Robot.ACTUATOR_SUBSYSTEM.dartTalon.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Latch Encoder: ", Robot.climb.latch.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Robot Heading", Robot.getNavXAngle());
    
    SmartDashboard.putNumber("Left Encoder", Robot.DRIVE_SUBSYSTEM.getLeftEncoderPosition());
    SmartDashboard.putNumber("Right Encoder", Robot.DRIVE_SUBSYSTEM.getRightEncoderPosition());

    SmartDashboard.putNumber("Left Front Output Voltage", Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getMotorOutputVoltage());
    SmartDashboard.putNumber("Left Mid Output Voltage", Robot.DRIVE_SUBSYSTEM.leftDriveMid.getMotorOutputVoltage());
    SmartDashboard.putNumber("Left Back Output Voltage", Robot.DRIVE_SUBSYSTEM.leftDriveBack.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Front Output Voltage", Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Mid Output Voltage", Robot.DRIVE_SUBSYSTEM.rightDriveMid.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Back Output Voltage", Robot.DRIVE_SUBSYSTEM.rightDriveBack.getMotorOutputVoltage());

    SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));

  }
}
