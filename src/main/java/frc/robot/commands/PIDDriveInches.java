/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.*;
import frc.robot.Robot;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class PIDDriveInches extends Command {

  private static final double p = 0.0;
  private static final double i = 0.0;
  private static final double d = 0.0;

  double inch_goal;
  double leftGoal, rightGoal;

  double stop_threshold = 3;
  double stop_threshold_ticks = Robot.DRIVE_SUBSYSTEM.inchesToTicks(stop_threshold);
  double count_threshold = 5;

  int timeoutMs = 10;

  double onTargetCount;
  public PIDDriveInches(double inches) {

    inch_goal = inches;

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.setCANTimeout(timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.setCANTimeout(timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.setMotorType(MotorType.kBrushless);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.setMotorType(MotorType.kBrushless);

    leftGoal = Robot.DRIVE_SUBSYSTEM.getLeftTicks() + Robot.DRIVE_SUBSYSTEM.inchesToTicks(inch_goal);
    rightGoal = Robot.DRIVE_SUBSYSTEM.getRightTicks() + Robot.DRIVE_SUBSYSTEM.inchesToTicks(inch_goal);

   

    Robot.DRIVE_SUBSYSTEM.leftPID.setP(p);
    Robot.DRIVE_SUBSYSTEM.leftPID.setI(i);
    Robot.DRIVE_SUBSYSTEM.leftPID.setD(d);

    Robot.DRIVE_SUBSYSTEM.rightPID.setP(p);
    Robot.DRIVE_SUBSYSTEM.rightPID.setI(i);
    Robot.DRIVE_SUBSYSTEM.rightPID.setD(d);
    
    Robot.DRIVE_SUBSYSTEM.setPID(leftGoal, rightGoal);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    if(Robot.DRIVE_SUBSYSTEM.getLeftTicks() > leftGoal - stop_threshold_ticks && Robot.DRIVE_SUBSYSTEM.getLeftTicks() < leftGoal + stop_threshold_ticks && Robot.DRIVE_SUBSYSTEM.getRightTicks() > rightGoal - stop_threshold_ticks && Robot.DRIVE_SUBSYSTEM.getRightTicks() < rightGoal + stop_threshold_ticks) {
      onTargetCount++;
    } else {
      onTargetCount = 0;
    }

    return onTargetCount > count_threshold;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVE_SUBSYSTEM.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
