/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class MotionProfilingDrivePathCommand extends Command {

  private static boolean motionProfilingDone = false;

  private static final String PATH_NAME = "Arc_Drive_Level_Two_First_Bay"; //_Drive_Level_Two_First_Bay

  private static final int TICKS_PER_REV = 4096;
  private static final double WHEELBASE_WIDTH = 24.5; //24.5 inches, 0.6223 meters
  private static final double WHEEL_DIAMETER = 6.25; //6.25 inches, 0.15875 meters
  private static final double MAX_VELOCITY = 106.2; //8.849557522 ft/sec, 106.1946903 in/sec, 2.69734513362 m/sec
                                                      //8.85 ft/sec, 106.2 in/sec, 2.69748 m/sec
  private static final double MAX_ACCELERATION = 53.1; //4.425 ft/sec^2, 53.1 in/sec^2, 1.34874 m/sec
  private static final double MAX_JERK = 2362.2; //2362.2 inches, 60 meters

  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  private static final int slotIdx = 0;

  private double CONSTANT_P = 0.5;
  private double CONSTANT_I = 0;
  private double CONSTANT_D = 0;

  private Trajectory left_trajectory;
  private Trajectory right_trajectory;

  private EncoderFollower left_follower;
  private EncoderFollower right_follower;

  private Notifier follower_notifier;

  
  //from Jaci private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
  //from Jaci TankModifier modifier = new TankModifier(left_trajectory).modify(WHEELBASE_WIDTH);


  
  public MotionProfilingDrivePathCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.resetNavXAngle();
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.setSelectedSensorPosition(0);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.setSelectedSensorPosition(0);
    try {
      left_trajectory = PathfinderFRC.getTrajectory(PATH_NAME + ".right");
      right_trajectory = PathfinderFRC.getTrajectory(PATH_NAME + ".left");
    } catch (IOException e) {
      System.out.println("Exception caught: creating left and right trajectories");
    }
    left_follower = new EncoderFollower(left_trajectory);
    right_follower = new EncoderFollower(right_trajectory);

    left_follower.configureEncoder(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition(), TICKS_PER_REV, WHEEL_DIAMETER);
    right_follower.configureEncoder(Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorPosition(), TICKS_PER_REV, WHEEL_DIAMETER);

    left_follower.configurePIDVA(CONSTANT_P, CONSTANT_I, CONSTANT_D, 1 / MAX_VELOCITY, 0);
    right_follower.configurePIDVA(CONSTANT_P, CONSTANT_I, CONSTANT_D, 1 / MAX_VELOCITY, 0);

    follower_notifier = new Notifier(this::followPath);
    follower_notifier.startPeriodic(left_trajectory.get(0).dt);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    System.out.println(Robot.getNavXAngle());
    followPath();

    // SmartDashboard.putNumber("Left Encoder:", Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition());
    // SmartDashboard.putNumber("Right Encoder:", Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorPosition());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return motionProfilingDone;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.setSelectedSensorPosition(0);
    // Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.setSelectedSensorPosition(0);
    left_follower.reset();
    right_follower.reset();
    System.out.println(left_follower.isFinished());
    Robot.DRIVE_SUBSYSTEM.set(0, 0);
    follower_notifier.close();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  private void followPath() {
    System.out.println("We're in followPath");
    if (this.left_follower.isFinished() || this.right_follower.isFinished()) {
      follower_notifier.stop();
      System.out.println("Motion Profiling Done!");
      motionProfilingDone = true;
      
    } else {
      double left_speed = left_follower.calculate(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition());
      double right_speed = right_follower.calculate(Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorPosition());
      //pathfinder works with a gyro that reads counterclockwise as positive and clockwise as negative
      //might need to invert the navx angle
      double heading = Robot.getNavXAngle();
      // attempt to run the code without the turn variable being added and subtracted in DRIVE_SUBSYSTEM.set
      double desired_heading = -Pathfinder.r2d(left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      // left_motor.set(left_speed + turn);
      // right_motor.set(right_speed - turn);
      Robot.DRIVE_SUBSYSTEM.set(left_speed + turn, right_speed - turn);
    }
  }
}
