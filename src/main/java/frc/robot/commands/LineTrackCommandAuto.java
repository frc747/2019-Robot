// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;
// import frc.robot.OI;

// public class LineTrackCommandAuto extends Command {

// private int timeoutMs = 10;
// private double threshold = 0;
// private double speed = 0.50;
// private double rampDown = 1;
// private double rate;
// private double leftValue = 0;
// private double rightValue = 0;
// private double seconds;

// private static final double MAX_PERCENT_VOLTAGE = 1.0;
// private static final double MIN_PERCENT_VOLTAGE = 0.0;

//   public LineTrackCommandAuto(double seconds) {
//     requires(Robot.DRIVE_SUBSYSTEM);
//     this.seconds = seconds;
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//     setTimeout(seconds);
//     rampDown = 1;

//     if(Robot.y == 0) {
//       speed = .25;
//       rate = 0;
//     } else {
//       speed = (1/Robot.y)*5;
//       rate = .009;
//     }

//     if(speed > 1) {
//       speed = 1.0;
//     }
    
//     System.out.println("line");

//     Robot.table.getEntry("pipeline").setDouble(0);

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
//     double adjustMagnitiude;
//     if(speed < .45) {
//       adjustMagnitiude = 5;
//       speed = .45;
//       rate = 0;
//     } else {
//       adjustMagnitiude = 3.75;
//     }
//     if(OI.leftStick.getRawButton(10)) {
//       leftValue = -OI.leftStick.getRawAxis(1);
//       rightValue = -OI.rightStick.getRawAxis(1);
      
//       if (Math.abs(leftValue) < 0.1) {
//         leftValue = 0;
//     }
//     if (Math.abs(rightValue) < 0.1) {
//         rightValue = 0;
//     }

//       Robot.DRIVE_SUBSYSTEM.set(leftValue, rightValue);
//     } else {
//        if(rampDown > .4) {
//          rampDown -= rate;
//        }
 
//      leftValue = ((speed) + ((.75*(Math.tanh(Robot.x/5)))/adjustMagnitiude))*rampDown;
//      rightValue = (-((speed) - ((.75*(Math.tanh(Robot.x/5)))/adjustMagnitiude))*rampDown);

//      Robot.DRIVE_SUBSYSTEM.set(leftValue, -rightValue);
//     }
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {
//     return (Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() < threshold && Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorVelocity() < threshold) || (Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() > -threshold && Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.getSelectedSensorVelocity() > -threshold);
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//     Robot.table.getEntry("pipeline").setDouble(0.0);
//     Robot.DRIVE_SUBSYSTEM.stop();
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//     Robot.table.getEntry("pipeline").setDouble(0.0);
//   }
// }
