// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.*;
// import frc.robot.Robot;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class PIDDriveInchesCustom extends Command {

//   private static double p = 0.03; // .5
//   private static double i; // .0
//   private static double d = 1;

//   double inch_goal;
//   double leftGoal, rightGoal;

//   double nominalMin = -1;
//   double nominalMax = 1;

//   double stop_threshold_inches = 3;
//   double stop_threshold_revs = Robot.DRIVE_SUBSYSTEM.inchesToRevs(stop_threshold_inches);
//   double count_threshold = 10;

//   double errorLeft, errorRight, outputLeft, outputRight, lastErrorLeft, lastErrorRight, totalErrorLeft, totalErrorRight;

//   // double distanceLeft;
//   // double distanceRight;

//   int timeoutMs = 10;

//   double onTargetCount;
//   public PIDDriveInchesCustom(double inches) {
//     requires(Robot.DRIVE_SUBSYSTEM);
//     inch_goal = inches;

//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {

//     Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.setCANTimeout(timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.setCANTimeout(timeoutMs);

//     Robot.DRIVE_SUBSYSTEM.leftDriveFront.follow(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary);
//     Robot.DRIVE_SUBSYSTEM.rightDriveFront.follow(Robot.DRIVE_SUBSYSTEM.rightDrivePrimary);
    
//     leftGoal = -Robot.DRIVE_SUBSYSTEM.inchesToRevs(inch_goal);
//     rightGoal = Robot.DRIVE_SUBSYSTEM.inchesToRevs(inch_goal);

//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {
//     System.out.println(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.get());
    
//     lastErrorLeft = errorLeft;
//     lastErrorRight = errorRight;

//     errorLeft = leftGoal - Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getEncoder().getPosition();
//     errorRight = rightGoal - Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getEncoder().getPosition();

//     outputLeft = p*errorLeft+-d*((lastErrorLeft-errorLeft)/20);
//     outputRight = p*errorRight+-d*((lastErrorRight-errorRight)/20);

//     Robot.DRIVE_SUBSYSTEM.set(outputLeft, outputRight);

//     SmartDashboard.putNumber("goal to travel to", leftGoal);
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {

// // Robot.DRIVE_SUBSYSTEM.getLeftTicks() >  left_end_ticks - stop_threshold_ticks && Robot.DRIVE_SUBSYSTEM.getLeftTicks() < left_end_ticks + stop_threshold_ticks && Robot.DRIVE_SUBSYSTEM.getRightTicks() > right_end_ticks - stop_threshold_ticks && Robot.DRIVE_SUBSYSTEM.getRightTicks() < right_end_ticks + stop_threshold_ticks
//     // GOOD SHIT if(Robot.DRIVE_SUBSYSTEM.getLeftRevs() > leftGoal && Robot.DRIVE_SUBSYSTEM.getRightRevs() > rightGoal) { //&& Robot.DRIVE_SUBSYSTEM.getRightTicks() > 100) {
//     //   System.out.println("REACHED GOAL");
//     //   return true;
//     // } else {
//     //   System.out.println("FALSE");
//     //   return false;
//     // }

//     if((Robot.DRIVE_SUBSYSTEM.getLeftRevs() < leftGoal + stop_threshold_revs) && (Robot.DRIVE_SUBSYSTEM.getLeftRevs() > leftGoal - stop_threshold_revs) && (Robot.DRIVE_SUBSYSTEM.getRightRevs() > rightGoal - stop_threshold_revs) && (Robot.DRIVE_SUBSYSTEM.getRightRevs() < rightGoal + stop_threshold_revs)) { //&& Robot.DRIVE_SUBSYSTEM.getRightTicks() > 100) {
//       System.out.println("ON COUNT ++");
//       onTargetCount++;
//     } else {
//       onTargetCount = 0;
//     }

//     return onTargetCount > count_threshold;

//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//     System.out.println("IS FINSIHED TRUE");
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//   }
// }
