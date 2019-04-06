// package frc.robot.commands;

// import com.ctre.phoenix.motorcontrol.ControlMode;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.OI;
// import frc.robot.Robot;

// public class DartDriveCommand extends Command {
//   public DartDriveCommand() {
//     requires(Robot.ACTUATOR_SUBSYSTEM);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {
//     double power = OI.testController.getRawAxis(1);

//     if(!OI.testController.getRawButton(6)) {
//       power = 0;
//     }

//     if(power < .05 && power > -.05) {
//       power = 0;
//     }

//     //

//     Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, power);
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {
//     return false;
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//     Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//   }
// }
