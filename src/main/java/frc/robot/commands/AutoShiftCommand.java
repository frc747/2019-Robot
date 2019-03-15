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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoShiftCommand extends Command {

  int shiftCount = 0;

  public AutoShiftCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double left = -OI.leftStick.getRawAxis(1);
    double right = -OI.rightStick.getRawAxis(1);

    if((left > .9 && right > .9) || (left < -.9 && right < -.9) && Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() > 1600 || Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorVelocity() < -1600) {
      shiftCount++;
    } else {
      shiftCount = 0;
    }

    // if shift count has been adding for half a second
    if(shiftCount > 25) {
      OI.shiftHigh = true;
      SmartDashboard.putString("High or Low: ", "High");
    } else {
      OI.shiftHigh = false;
      SmartDashboard.putString("High or Low: ", "Low");
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
