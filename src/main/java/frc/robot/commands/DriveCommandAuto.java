// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.OI;
// import frc.robot.Robot;

// import com.ctre.phoenix.motorcontrol.ControlMode;

// public class DriveCommandAuto extends Command {

//   int timeoutMs = 10;

//   double left;
//   double right;
//   public static String driveType = "fps";

//   public double maxThreshold;

//   public DriveCommandAuto(double seconds) {
//     requires(Robot.DRIVE_SUBSYSTEM);
//     setTimeout(seconds);
//   }

//   protected double limit(double value) {
//     if (value > 1.0) {
//       return 1.0;
//     }
//     if (value < -1.0) {
//       return -1.0;
//     }
//     return value;
//   }

//   protected double applyDeadband(double value, double deadband) {
//     if (Math.abs(value) > deadband) {
//       if (value > 0.0) {
//         return (value - deadband) / (1.0 - deadband);
//       } else {
//         return (value + deadband) / (1.0 - deadband);
//       }
//     } else {
//       return 0.0;
//     }
//   }

//   private void arcadeDrive(double xSpeed, double zRotation) {

//     // Square the inputs (while preserving the sign) to increase fine control
//     // while permitting full power.
//     xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
//     zRotation = Math.copySign(zRotation * zRotation, zRotation);

//     double leftMotorOutput;
//     double rightMotorOutput;

//     double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

//     if (xSpeed >= 0.0) {
//       // First quadrant, else second quadrant
//       if (zRotation >= 0.0) {
//         leftMotorOutput = maxInput;
//         rightMotorOutput = xSpeed - zRotation;
//       } else {
//         leftMotorOutput = xSpeed + zRotation;
//         rightMotorOutput = maxInput;
//       }
//     } else {
//       // Third quadrant, else fourth quadrant
//       if (zRotation >= 0.0) {
//         leftMotorOutput = xSpeed + zRotation;
//         rightMotorOutput = maxInput;
//       } else {
//         leftMotorOutput = maxInput;
//         rightMotorOutput = xSpeed - zRotation;
//       }
//     }

//     Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.set(ControlMode.PercentOutput, leftMotorOutput);
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.set(ControlMode.PercentOutput, -rightMotorOutput);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//     System.out.println("Initialized DRIVE_SUBSYSTEM.");
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {

//     if (driveType == "tank") {
//       left = OI.driverController.getRawAxis(1);
//       right = -OI.driverController.getRawAxis(5);
  
//       if(Math.abs(left) < .1) {
//         left = 0;
//       } 
      
//       if(Math.abs(right) < .1) {
//         right = 0;
//       }
  
//       Robot.DRIVE_SUBSYSTEM.set(-left, right);

//     } else if (driveType == "arcade") {
//       this.arcadeDrive(OI.driverController.getRawAxis(4), -OI.driverController.getRawAxis(5));

//     } else if (driveType == "fps") {
//       double rotate = OI.driverController.getRawAxis(4), drive = -OI.driverController.getRawAxis(1);

//       if (rotate >= maxThreshold) {
//         rotate = maxThreshold;
//       }
//       if (drive >= maxThreshold) {
//         drive = maxThreshold;
//       }

//       this.arcadeDrive(OI.driverController.getRawAxis(4), -OI.driverController.getRawAxis(1));

//     }
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {
//     return false;
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//   }
// }
