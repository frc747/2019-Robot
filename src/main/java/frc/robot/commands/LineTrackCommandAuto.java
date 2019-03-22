/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.OI;

public class LineTrackCommandAuto extends Command {

int timeoutMs = 10;
double threshold = 0;
double speed = 0.50;
double rampDown = 1;
double rate;
double leftValue = 0;
double rightValue = 0;
double last;
double seconds;
private static final double MAX_PERCENT_VOLTAGE = 1.0;
private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public LineTrackCommandAuto(double seconds) {
    requires(Robot.DRIVE_SUBSYSTEM);
    this.seconds = seconds;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(seconds);
    //speed = .25;
    rampDown = 1;
    // // Rocket
    // if(OI.area < .75) {
    //   rate = .009;
    // } else if (OI.area < 1.5) {
    //   rate = 0.0115;
    // } else if(OI.area > 1.5) {
    //   speed = 0.6;
    //   rate = .018;//was 0.0049
    // } else if(OI.area > 3.9) {
    //   speed = 0.35;
    //   rate = 0.03;
    // }



    // if(OI.area  < .75) {
    //   speed = 1.0;
    // }
    if(OI.y == 0) {
      speed = .25;
      rate = 0;
    } else {
      speed = (1/OI.y)*5;
      rate = .009;
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
      adjustMagnitiude = 4.5;
      speed = .45;
      rate = 0;
    } else {
      adjustMagnitiude = 3.25;
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
      //SmartDashboard.putNumber("rampdown", rampDown);

       if(rampDown > .4) {
         rampDown -= rate;
       }
 
      //turns the y-range into a positive set to eliminate accidental reversing of the robot.
      //double convertedY = OI.y+20.5;
      

      //Divides the number by 20.5 to say that if the target is centered vertically, make the rampdown equal to 1.
      //rampDown = Math.abs(1/convertedY)*10;
    

 
      // if(OI.y == 0 || rampDown < .2) {
      //   rampDown = .2;
      // }
      // last = rampDown;
      SmartDashboard.putNumber("rampdown", rampDown);

     leftValue = ((speed) + ((.75*(Math.tanh(OI.x/5)))/adjustMagnitiude))*rampDown;
     rightValue = (-((speed) - ((.75*(Math.tanh(OI.x/5)))/adjustMagnitiude))*rampDown);

    // leftValue = (speed)*rampDown + ((.6*(Math.tanh(OI.x/5)))/3.9) * 1 * rampDown;
    // rightValue = (-speed*rampDown) + ((.6*(Math.tanh(OI.x/5)))/3.9) * 1 * rampDown;

 // 3.25
     Robot.DRIVE_SUBSYSTEM.set(leftValue, -rightValue);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() < threshold && Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorVelocity() < threshold) || (Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() > -threshold && Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorVelocity() > -threshold);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
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
