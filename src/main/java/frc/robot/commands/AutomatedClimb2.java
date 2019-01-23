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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutomatedClimb2 extends Command {

  private static final double p = 0.5; // .5
  private static final double i = 0.0; // .0
  private static final double d = 1.0; // .0

  double rev_goal;
  double goal;

  double nominalMin = -1;
  double nominalMax = 1;

  double stop_threshold_revs = 10;
  double count_threshold = 5;

  // double distanceLeft;
  // double distanceRight;

  int timeoutMs = 10;

  double onTargetCount;
  public AutomatedClimb2(double revs) {
    requires(Robot.DRIVE_SUBSYSTEM);
    this.rev_goal = revs;

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    
    Robot.DRIVE_SUBSYSTEM.isDone1 = false;
    Robot.DRIVE_SUBSYSTEM.isClimbed = true;

    Robot.DRIVE_SUBSYSTEM.climb.setCANTimeout(timeoutMs);
    Robot.DRIVE_SUBSYSTEM.climb.setMotorType(MotorType.kBrushless);

    Robot.DRIVE_SUBSYSTEM.climbPID.setOutputRange(nominalMin, nominalMax);    
    goal = rev_goal;
    
    Robot.DRIVE_SUBSYSTEM.climbPID.setP(p);
    Robot.DRIVE_SUBSYSTEM.climbPID.setI(i);
    Robot.DRIVE_SUBSYSTEM.climbPID.setD(d);


  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.DRIVE_SUBSYSTEM.setClimbPID(goal);


    //distanceLeft = Robot.DRIVE_SUBSYSTEM.ticksToRevs(Robot.DRIVE_SUBSYSTEM.getLeftTicks()-left_start_ticks);
    //distanceRight = Robot.DRIVE_SUBSYSTEM.ticksToRevs(Robot.DRIVE_SUBSYSTEM.getRightTicks()-right_start_ticks);

    //leftGoal = Robot.DRIVE_SUBSYSTEM.getLeftTicks() + Robot.DRIVE_SUBSYSTEM.inchesToTicks(inch_goal);
    //rightGoal = Robot.DRIVE_SUBSYSTEM.getRightTicks() + Robot.DRIVE_SUBSYSTEM.inchesToTicks(inch_goal);




    SmartDashboard.putNumber("CLIMB MOTOR GOAL", goal);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(goal >= 0) {
      if(Robot.DRIVE_SUBSYSTEM.getClimbRevs() > goal - stop_threshold_revs && Robot.DRIVE_SUBSYSTEM.getClimbRevs() < goal + stop_threshold_revs) {
        onTargetCount++;
      } else {
        onTargetCount = 0;
      }
  
      return onTargetCount > count_threshold;
    } else {
      if(Robot.DRIVE_SUBSYSTEM.getClimbRevs() < goal + stop_threshold_revs && Robot.DRIVE_SUBSYSTEM.getClimbRevs() > goal - stop_threshold_revs) {
        onTargetCount++;
      } else {
        onTargetCount = 0;
      }
      return onTargetCount > count_threshold;
    }


  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
    Robot.DRIVE_SUBSYSTEM.isDone1 = true;
    Robot.DRIVE_SUBSYSTEM.isClimbed = false;

    Robot.DRIVE_SUBSYSTEM.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
