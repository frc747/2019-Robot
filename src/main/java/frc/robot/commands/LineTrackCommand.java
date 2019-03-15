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
double last;

  public LineTrackCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    speed = 0.5;
    double area = OI.area;
    rate = .0065;
    System.out.println("line");

    OI.table.getEntry("pipeline").setDouble(0.0);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double yOffset = -1 * (OI.y + 3 + 6);
    double drivingAdjustY = 0.05 * yOffset; //-0.1 being the proportional value

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

    if(rampDown > 0.6) {
      rampDown -= rate;
    } else {
      rampDown = .4;
    }
     leftValue = ((speed) + ((.75 * (Math.tanh(OI.x / 3))) / 2.75)) *rampDown;//+ drivingAdjustY;
     rightValue = (-((speed) - ((.75 * (Math.tanh(OI.x / 3))) / 2.75))) *rampDown; //- drivingAdjustY);
 
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
