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

public class LineTrackCommand extends Command {

int timeoutMs = 10;

double speed = 0.50;
double rampDown = 1;
double rate;
double leftValue = 0;
double rightValue = 0;

  public LineTrackCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    speed = 1.0;
    rampDown = 1;
    // Rocket
    if(OI.area > 6) {
      speed = .5;
      rate = .00875;
    } else {
      // Loading Station
      speed = 1.0;
      rate = .0080;//was 0.0049
    }
    System.out.println("line");

    OI.table.getEntry("pipeline").setDouble(5.0);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

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
      SmartDashboard.putNumber("rampdown", rampDown);

      if(rampDown > .4) {
        rampDown -= rate;
      }
 
     
 
     leftValue = ((speed) + ((.75*(Math.tanh(OI.x/10)))/2.75))*rampDown;
     rightValue = (-((speed) - ((.75*(Math.tanh(OI.x/10)))/2.75)))*rampDown;
 
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
