/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PIDDriveRotateCustom extends Command {

  double p = 3, i = 0, dAcute = 2.7, dObtuse = 2.7, output;

  double goal, threshold = 5;//2.5;

  double onTargetCount = 0;

  double lastError = 0, error, totalError = 0;

  double errorSlope;

  public PIDDriveRotateCustom(double angle) {
    requires(Robot.DRIVE_SUBSYSTEM);
    goal = angle;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.resetNavXAngle();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    lastError = error;

    error = goal - Robot.getNavXAngle();

    if (Math.abs(lastError-error) > 180) {
      error = 0;
    }
    if (goal < 90 && goal > -90) {
      errorSlope = ((lastError-error)/20)*-dAcute;
    } else {
      errorSlope = ((lastError-error)/20)*-dObtuse;
    }
    
    totalError = totalError + error;

    output = (Math.tanh(error/90)*p)+errorSlope+(totalError*i);

    Robot.DRIVE_SUBSYSTEM.set(output, -output);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(error < threshold && error > -threshold) {
      onTargetCount++;
    } else {
      onTargetCount = 0;
    }

    return onTargetCount > 5;
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