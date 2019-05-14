// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;
// import java.lang.Thread;

// import frc.robot.OI;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class VisionTrackCommand extends Command {

// private int timeoutMs = 10;

// private static double DRIVE_MAX = Math.sqrt(0.7);//Math.sqrt(0.8); // Simple speed limit so we don't drive too fast
// private static final double DRIVE_P = 0.12;//0.15 // how hard to drive foward toward the target
// private static final double STEER_P = 0.03; // how hard to turn toward the target
// private static final double TARGET_Y_GOAL = 19.0; // Area of the target when the robot reaches the wall

// private double leftValue = 0;
// private double rightValue = 0;

// private double straight = 0;
// private double steer = 0;

// private boolean hasTarget = false;

// private static final double MAX_PERCENT_VOLTAGE = 1.0;
// private static final double MIN_PERCENT_VOLTAGE = 0.0;

//   public VisionTrackCommand() {
//     requires(Robot.DRIVE_SUBSYSTEM);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//     SmartDashboard.putBoolean("Currently Vision Tracking", true);

//     Robot.DRIVE_SUBSYSTEM.tracking = true;
    
//   try {
//       Thread.sleep(50);
//   } catch (InterruptedException e) {
//       // TODO Auto-generated catch block
//       e.printStackTrace();
//   }

//     // OI.table.getEntry("camMode").setDouble(0); // Limelight "Vision Processor" operation mode

//     // OI.table.getEntry("pipeline").setDouble(0);

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
//     // if the operator is holding the left trigger
//     if (OI.operatorController.getRawAxis(2) > 0.25) {
//       DRIVE_MAX = Math.sqrt(1);
//     } else {
//       DRIVE_MAX = Math.sqrt(0.7);
//     }

//     if(OI.leftStick.getRawButton(10)) {
//       leftValue = -OI.leftStick.getRawAxis(1);
//       rightValue = -OI.rightStick.getRawAxis(1);
      
//       if (Math.abs(leftValue) < 0.1) {
//         leftValue = 0;
//       }
//       if (Math.abs(rightValue) < 0.1) {
//         rightValue = 0;
//       }


//       Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);
//     } else if (OI.v == 1) {
//       hasTarget = true;
//       double steer_cmd = 0;

//       if (Math.abs(OI.x) > 3.5 && !(OI.operatorController.getRawAxis(2) > 0.25)) {
//         if (Math.abs(OI.x) > 6) {
//           steer_cmd = Math.copySign(6.5, OI.x) * STEER_P;
//         } else {
//           steer_cmd = OI.x * STEER_P;
//         }

//         steer = steer_cmd;
//         this.arcadeDrive(Math.sqrt(0.25), steer, true, 0);
//         Robot.DRIVE_SUBSYSTEM.set(limit(leftValue) * DRIVE_MAX, limit(rightValue) * DRIVE_MAX);
        
//       } else if (Math.abs(OI.x) < 1.5 && !(OI.operatorController.getRawAxis(2) > 0.25)){
//         steer_cmd = OI.x * STEER_P;
        
//         steer = steer_cmd;
//         double drive_cmd = 0;  
//           // try to drive forward until the target area reaches our desired area
//           if (OI.y > 15) {
//             drive_cmd = 0.5;
//           } else {
//             drive_cmd = (TARGET_Y_GOAL - OI.y) * 0.2;
//           }
  
  
//           // don't let the robot drive too fast into the goal
//           if (drive_cmd > DRIVE_MAX) {
//             drive_cmd = DRIVE_MAX;
//           }
//           straight = drive_cmd;
  
//         this.arcadeDrive(straight, steer, true, 0);
//         Robot.DRIVE_SUBSYSTEM.set(limit(leftValue) * DRIVE_MAX, limit(rightValue) * DRIVE_MAX);
//       } else if (!(OI.operatorController.getRawAxis(2) > 0.25)) {
//         steer_cmd = OI.x * STEER_P;
        
//         steer = steer_cmd;
//         double drive_cmd = 0;  
//           // try to drive forward until the target area reaches our desired area
//           if (OI.y > 15) {
//             drive_cmd = 0.5;
//           } else {
//             drive_cmd = (TARGET_Y_GOAL - OI.y) * DRIVE_P;
//           }
  
  
//           // don't let the robot drive too fast into the goal
//           if (drive_cmd > DRIVE_MAX) {
//             drive_cmd = DRIVE_MAX;
//           }
//           straight = drive_cmd;
  
//         this.arcadeDrive(straight, steer, true, 0);
//         Robot.DRIVE_SUBSYSTEM.set(limit(leftValue) * DRIVE_MAX, limit(rightValue) * DRIVE_MAX);
//       } else { // FOR LOADING STATION HOLD DOWN LEFT TRIGGER
//         if (Math.abs(OI.x) > 8) {
//           steer_cmd = Math.copySign(7, OI.x) * STEER_P;
//         } else {
//           steer_cmd = OI.x * STEER_P;
//         }
        
//         steer_cmd = OI.x * STEER_P;
        
//         steer = steer_cmd;
//         double drive_cmd = 0;  
//         // try to drive forward until the target area reaches our desired Y angle
//         if (OI.y > 15) {
//           drive_cmd = 0.5;
//         } else {
//           drive_cmd = (TARGET_Y_GOAL - OI.y) * DRIVE_P;
//         }
  
  
//         // don't let the robot drive too fast into the goal
//         if (drive_cmd > DRIVE_MAX) {
//           drive_cmd = DRIVE_MAX;
//         }
//         straight = drive_cmd;
  
//         this.arcadeDrive(straight, steer, true, 0);
//         Robot.DRIVE_SUBSYSTEM.set(limit(leftValue) * DRIVE_MAX, limit(rightValue) * DRIVE_MAX);
//       }
//     } else { // when there is no target in sight)
//       hasTarget = false;
//       leftValue = 0;
//       rightValue = 0;

//       straight = 0;
//       steer = 0;
//       this.arcadeDrive(0.30, 0, false, 0);
//       Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);
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
//     SmartDashboard.putBoolean("Currently Vision Tracking", false);
//     OI.table.getEntry("camMode").setDouble(1);

//     // OI.table.getEntry("pipeline").setDouble(0.0);
//     Robot.DRIVE_SUBSYSTEM.stop();
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//     // OI.table.getEntry("pipeline").setDouble(0.0);
//   }

//   private double limit(double value) {
//     if (value > 1.0) {
//       return 1.0;
//     }
//     if (value < -1.0) {
//       return -1.0;
//     }
//     return value;
//   }
  
//   private double applyDeadband(double value, double deadband) {
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

//   private void arcadeDrive(double straightValue, double steerValue, boolean squareInputs, double deadBandValue) {
//     this.straight = limit(straightValue);
//     this.straight = applyDeadband(straightValue, deadBandValue);

//     this.steer = limit(steerValue);
//     this.steer = applyDeadband(steerValue, deadBandValue);

//     // Square the inputs (while preserving the sign) to increase fine control
//     // while permitting full power.
//     if (squareInputs) {
//       this.straight = Math.copySign(this.straight * this.straight, this.straight);
//       this.steer = Math.copySign(this.steer, this.steer);
//       }

//     double maxInput = Math.copySign(Math.max(Math.abs(this.straight), Math.abs(this.steer)), this.straight);

//     if (this.straight >= 0.0) {
//       // First quadrant, else second quadrant
//       if (this.steer >= 0.0) {
//         leftValue = maxInput;
//         rightValue = this.straight - this.steer;
//       } else {
//         leftValue = this.straight + this.steer;
//         rightValue = maxInput;
//       }
//     } else {
//       // Third quadrant, else fourth quadrant
//       if (this.steer >= 0.0) {
//         leftValue = this.straight + this.steer;
//         rightValue = maxInput;
//       } else {
//         leftValue = maxInput;
//         rightValue = this.straight - this.steer;
//       }
//     }

//   }

// }
