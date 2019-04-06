package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import frc.robot.OI;

public class LineTrackCommand extends Command {

private int timeoutMs = 10;

private double speed = 0.50;
private double rampDown = 1;
private double rate;
private double leftValue = 0;
private double rightValue = 0;
private double x;
private double X_TOLERANCE = 1;
private static final double MAX_PERCENT_VOLTAGE = 1.0;
private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public LineTrackCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    rampDown = 1;
    x = 30;
    if(Robot.y == 0) {
      speed = .25;
      rate = 0;
    } else {
      speed = (1/Robot.y)*5;
      rate = .009;
    }

    if(speed > 1) {
      speed = 1.0;
    }
    
    System.out.println("line");

    Robot.table.getEntry("pipeline").setDouble(0);

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

      // If the absolute value of the X offset is less than the old value of X plus a tolerance and it is not equal to 0 then it will update the value
      // otherwise it will keep the old value and assume that you picked up a red herring
      if((Math.abs(Robot.x) < Math.abs(x) + X_TOLERANCE) && x != 0) {
        x = Robot.x;
      } 

      


      //  was leftValue = ((speed) + ((.75*(Math.tanh(Robot.x/5)))/adjustMagnitiude))*rampDown;
     leftValue = ((speed) + ((.75*(Math.tanh(x/10)))/adjustMagnitiude))*rampDown;
     rightValue = (-((speed) - ((.75*(Math.tanh(x/10)))/adjustMagnitiude))*rampDown);

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
    Robot.table.getEntry("pipeline").setDouble(0.0);
    Robot.DRIVE_SUBSYSTEM.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.table.getEntry("pipeline").setDouble(0.0);
  }
}
