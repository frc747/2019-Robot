/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class GetInRangeCommand extends Command {

  int timeoutMs = 10;

  private static final double MAX_PERCENT_VOLTAGE = 0.50;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public GetInRangeCommand() {
    requires(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // this code is being run when the Select button on the operator controller is pressed
    
    // this block of code utilizes pipeline 4 from the limelight titled: Line_Getting_In_Range_CLOSE
    // it will drive at a specific speed after reaching the edge of the line of tape
    // so that it can press up against the face of the rocket or cargo ship
    if (OI.y < 2.5) {
      Robot.DRIVE_SUBSYSTEM.set(0.35, 0.35);
    } else {
      double drivingAdjustX = 0.1 * OI.x * OI.y * 0.03;
      double drivingAdjustY = 0.03 * OI.y; //-0.1 being the proportional value
      Robot.DRIVE_SUBSYSTEM.set(0.2 + drivingAdjustX + drivingAdjustY, 0.2 -drivingAdjustX + drivingAdjustY);
    }

    // this block of commented code utilizes pipeline 3 from the limelight titled: Line_Getting_In_Range_CLOSE
    // this code will reach drive until it reaches a desired position on the line
    // that was specified to me by Jeff
    // double drivingAdjustX = 0.1 * OI.x * OI.y * 0.03;
    // double drivingAdjustY = 0.03 * OI.y; //-0.1 being the proportional value
    // Robot.DRIVE_SUBSYSTEM.set(drivingAdjustX + drivingAdjustY, -drivingAdjustX + drivingAdjustY);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.DRIVE_SUBSYSTEM.set(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
