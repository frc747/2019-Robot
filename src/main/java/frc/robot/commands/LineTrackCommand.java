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

    rampDown = 1;
    if(OI.area > 6) {
      rate = .008;
    } else {
      rate = .0065;
    }
    System.out.println("line");

    OI.table.getEntry("pipeline").setDouble(5.0);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    SmartDashboard.putNumber("rampdown", rampDown);

     if(rampDown > .1) {
       rampDown -= rate;
     }

    

    leftValue = ((speed) + ((.75*(Math.tanh(OI.x/10)))/3))*rampDown;
    rightValue = (-((speed) - ((.75*(Math.tanh(OI.x/10)))/3)))*rampDown;

    Robot.DRIVE_SUBSYSTEM.set(leftValue, -rightValue);
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
