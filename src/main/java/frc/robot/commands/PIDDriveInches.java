/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.command.*;
import frc.robot.Robot;

public class PIDDriveInches extends Command {

  boolean finished = false;

  double p;

  double i;
  
  double d;
  
  int inch_goal;

  double leftGoal, rightGoal;

  public PIDDriveInches(int inches) {

    inch_goal = inches;

    leftGoal = Robot.DRIVE_SUBSYSTEM.getLeftTicks() + Robot.DRIVE_SUBSYSTEM.inchesToTicks(inches);
    rightGoal = Robot.DRIVE_SUBSYSTEM.getRightTicks() + Robot.DRIVE_SUBSYSTEM.inchesToTicks(inches);

    CANPIDController leftPID = new CANPIDController(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary);
    CANPIDController rightPID = new CANPIDController(Robot.DRIVE_SUBSYSTEM.rightDrivePrimary);

    leftPID.setP(0.0);
    leftPID.setI(0.0);
    leftPID.setD(0.0);

    rightPID.setP(0.0);
    rightPID.setI(0.0);
    rightPID.setD(0.0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {




    if(Robot.DRIVE_SUBSYSTEM.getLeftTicks() > leftGoal && Robot.DRIVE_SUBSYSTEM.getRightTicks() > rightGoal) {
      finished = true;
    } else {
      finished = false;
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
