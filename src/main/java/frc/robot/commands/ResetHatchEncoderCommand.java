package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ResetHatchEncoderCommand extends Command {
  
  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  
  public ResetHatchEncoderCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.HATCH_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    Robot.HATCH_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
