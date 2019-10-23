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

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import java.io.IOException;
public class FollowPath extends Command {


  private static final String path_name = "hatch";
    
  private double wheelDiameter = .15621; // meters 
  private double maxVelocity = 2.156; //2.156 2.487
  private int ticksPerRev = 4096; //ADD THE TIMES 6\

  private double kP = .1
  ; // .09 with 2.487
  private double kI = 0;
  private double kD = 0.01;

  private int leftEncoderValue = Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition();
  private int rightEncoderValue = Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorPosition();

  private double gyroHeading = -Robot.getNavXAngle();

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;

  private boolean finished = false;

  public FollowPath() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVE_SUBSYSTEM.resetBothEncoders();
    Robot.resetNavXAngle();
    try {
      Trajectory left_trajectory = PathfinderFRC.getTrajectory(path_name + ".right"); //swapped
      Trajectory right_trajectory = PathfinderFRC.getTrajectory(path_name + ".left");
      m_left_follower = new EncoderFollower(left_trajectory);
      m_right_follower = new EncoderFollower(right_trajectory);
    } catch(IOException ex) {
      System.out.println(ex.toString());
      System.out.println("IT DIDNT FIND IT");
    }
    

    m_left_follower.configureEncoder(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition(), ticksPerRev, wheelDiameter);
    // You must tune the PID values on the following line!
    m_left_follower.configurePIDVA(kP, kI, kD, 1 / maxVelocity, 0);

    m_right_follower.configureEncoder(Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorPosition(), ticksPerRev, wheelDiameter);
    // You must tune the PID values on the following line!
    m_right_follower.configurePIDVA(kP, kI, kD, 1 / maxVelocity, 0);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    leftEncoderValue = Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition(); //swapped
    rightEncoderValue = Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorPosition();

    gyroHeading = -Robot.getNavXAngle();

    followPath();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    SmartDashboard.putBoolean("FINISHED", finished);
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
    Robot.DRIVE_SUBSYSTEM.stop();
    finished = true;
  }

  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      Robot.DRIVE_SUBSYSTEM.stop();
      finished = true;
    } else {
      double left_speed = m_left_follower.calculate(leftEncoderValue);
      double right_speed = m_right_follower.calculate(rightEncoderValue);
      double heading = gyroHeading;
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      Robot.DRIVE_SUBSYSTEM.set(left_speed + turn, right_speed - turn); 
    }
  }
}
