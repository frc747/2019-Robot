// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.OI;
// import frc.robot.Robot;
// import com.ctre.phoenix.motorcontrol.ControlMode;;

// public class ArcadeDriveCommand extends Command {

//   int timeoutMs = 10;

//   private double driveTicks = 830;

//   double shiftCount = 0;

//   double maxThreshold = 1
//   ;

//   private static final double MAX_PERCENT_VOLTAGE = 1.0;
//   private static final double MIN_PERCENT_VOLTAGE = 0.0;

//   public ArcadeDriveCommand() {
//     requires(Robot.DRIVE_SUBSYSTEM);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//     Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {
//     double xSpeed = -OI.driverController.getRawAxis(1);
//     double zRotation = OI.driverController.getRawAxis(4);

//     if (xSpeed >= maxThreshold) {
//       xSpeed = maxThreshold;
//     } else if (xSpeed <= -maxThreshold) {
//       xSpeed = -maxThreshold;
//     }
//     if (zRotation >= maxThreshold) {
//       zRotation = maxThreshold;
//     } else if (zRotation <= -maxThreshold) {
//       zRotation = -maxThreshold;
//     }


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
//         //xSpeed-=(Math.tanh(Robot.getNavXAngle()/120));
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
//     Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.set(ControlMode.PercentOutput, rightMotorOutput);
    
    

//             // check if the robot should be considered moving towards high gear or stay in low gear
//         if((xSpeed > .9) || (xSpeed < .9) && (Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() > 1600 || Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() < -1600)) {
//             shiftCount++;
//         } else {
//             shiftCount = 0;
//         }
      
//         if (OI.operatorController.getRawAxis(2) > 0.25) {
//             Robot.DRIVE_SUBSYSTEM.tracking = true;
//         } else {
//             Robot.DRIVE_SUBSYSTEM.tracking = false;
//         }

//         // if shift count has been adding for half a second
//         if(shiftCount > 25) {
//             OI.shiftHigh = true;
//         } else {
//             OI.shiftHigh = false;
//         }
       
//         if (OI.shiftHigh && !(OI.operatorController.getRawAxis(3) > .25)) {
//             // Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, shifterValue);
//             if (Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() > driveTicks - 10 && Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() < driveTicks + 10) {
//                 Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
//             } else {
//                 Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionCruiseVelocity(7500, 10); //1500
//                 Robot.DRIVE_SUBSYSTEM.gearShifter.configMotionAcceleration(20000, 10); //2000
//                 Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.MotionMagic, driveTicks);
//             }

//         } else {
//             // Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
//             if (Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() > -10 && Robot.DRIVE_SUBSYSTEM.gearShifter.getSelectedSensorPosition() < 10) {
//                 Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.PercentOutput, 0);
//             } else {
//                 Robot.DRIVE_SUBSYSTEM.gearShifter.set(ControlMode.MotionMagic, 0);
//             }
//         }
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
