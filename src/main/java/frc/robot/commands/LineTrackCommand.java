package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import frc.robot.OI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LineTrackCommand extends Command {

private int timeoutMs = 10;

private double speed = 0.50;
private double rampDown = 1;
private double rate;
private double leftValue = 0;
private double rightValue = 0;

private boolean hasTarget = false;

private int count = 0;

private static final double MAX_PERCENT_VOLTAGE = 1.0;
private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public LineTrackCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putBoolean("Currently Vision Tracking", true);

    rampDown = 1;

    if(OI.y == 0) {
      speed = .25;
      rate = 0;

      hasTarget = false;
    } else {
      speed = (1/OI.y)*5;
      rate = .009;
      hasTarget = true;
    }

    if(speed > 1) {
      speed = 1.0;
    }
    
    System.out.println("line");

    OI.table.getEntry("pipeline").setDouble(0);

    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double adjustMagnitiude;
    if(speed < .45) {
      adjustMagnitiude = 6; //changed to 5 for the final matches of Chestnut Hill
      speed = .45;
      rate = 0;
    } else {
      adjustMagnitiude = 4.5; //changed to 3.75 for the final matches of Chestnut Hill
    }

    if(OI.leftStick.getRawButton(10)) {
      leftValue = -OI.leftStick.getRawAxis(1);
      rightValue = -OI.rightStick.getRawAxis(1);
      
      if (Math.abs(leftValue) < 0.1) {
        leftValue = 0;
      }
      if (Math.abs(rightValue) < 0.1) {
        rightValue = 0;
      }

      Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);
    } else {
       if(rampDown > .4) {
         rampDown -= rate;
       }
      //  was leftValue = ((speed) + ((.75*(Math.tanh(Robot.x/5)))/adjustMagnitiude))*rampDown;
      leftValue = ((speed) + ((.75*(Math.tanh(OI.x/10)))/adjustMagnitiude))*rampDown;
      rightValue = (-((speed) - ((.75*(Math.tanh(OI.x/10)))/adjustMagnitiude))*rampDown);

      // if (count % 25 == 0) {
      //   // added printouts for vision tracking logging purposes
      //   System.out.println("Speed: " + speed + "\t Rampdown: " + rampDown + "\t Rate: " + rate + "\t Has target: " + hasTarget + "Adjust Magnitude : " + adjustMagnitiude);
      //   System.out.println("Left Value: " + leftValue + "Right Value: " + -rightValue);
      // }
      // count++;


      Robot.DRIVE_SUBSYSTEM.set(leftValue, -rightValue);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    SmartDashboard.putBoolean("Currently Vision Tracking", false);
    OI.table.getEntry("pipeline").setDouble(0.0);
    Robot.DRIVE_SUBSYSTEM.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    OI.table.getEntry("pipeline").setDouble(0.0);
  }
}
